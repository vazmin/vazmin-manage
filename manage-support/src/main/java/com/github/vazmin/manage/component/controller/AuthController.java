package com.github.vazmin.manage.component.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.web.support.annotation.Command;
import com.github.vazmin.framework.web.support.annotation.Module;
import com.github.vazmin.manage.component.model.users.ManageUser;
import com.github.vazmin.manage.component.service.MailService;
import com.github.vazmin.manage.component.service.users.ManageUserService;
import com.github.vazmin.manage.component.controller.errors.EmailNotFoundException;
import com.github.vazmin.manage.component.controller.errors.InternalServerErrorException;
import com.github.vazmin.manage.component.service.AuthService;
import com.github.vazmin.manage.component.vm.KeyAndPasswordVM;
import com.github.vazmin.manage.component.vm.LoginVM;
import com.github.vazmin.manage.component.vm.PrincipalVM;
import com.github.vazmin.manage.support.security.ManageUserDetails;
import com.github.vazmin.manage.support.security.jwt.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Chwing on 2018/2/12.
 */
@Controller
@RequestMapping("/api")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public static final String MODULE_NAME = "用户";

    @Autowired
    private AuthService authService;

    @Autowired
    private MailService mailService;

    @Autowired
    private ManageUserService manageUserService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @ResponseBody
    @RequestMapping(value = "/authenticate", method = RequestMethod.GET)
    public PrincipalVM isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        ManageUserDetails partnerUserDetails =
                (ManageUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new PrincipalVM(
                authService.getMenuList(partnerUserDetails),
                partnerUserDetails.getManageUserNoPassword());
    }

    @ResponseBody
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ManageUser account(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        ManageUserDetails manageUserDetails =
                (ManageUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return manageUserDetails.getManageUserNoPassword();
    }

    @ResponseBody
    @RequestMapping("/noPermission")
    public ResponseEntity<String> noPermission() {
        log.debug("REST request to check if the current user is authenticated");
        return new ResponseEntity<>("没有权限，请联系管理员！", HttpStatus.FORBIDDEN);
    }

    @ResponseBody
    @RequestMapping("/logout/success")
    public ResponseEntity<String> logoutSuccess() {
        log.debug("logout success...");
        return new ResponseEntity<>("logout success", HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/session/out", method = RequestMethod.GET)
    public ResponseEntity<String> sessionOut() {
        return new ResponseEntity<>("session out...", HttpStatus.UNAUTHORIZED);
    }


    /**
     * 请求重置密码
     * @param mail 邮箱
     */
    @ResponseBody
    @RequestMapping(value = "/account/reset-password/init", method = RequestMethod.POST)
    public void resetPasswordInit(@RequestBody String mail) throws ServiceProcessException {
        ManageUser user = manageUserService.requestResetPassword(mail);
        if (user != null) {
//            mailService.sendPasswordResetMail(user);
        } else {
            throw new EmailNotFoundException();
        }
    }

    /**
     * 重置密码 完成
     * @param keyAndPassword
     * @throws ServiceProcessException
     */
    @ResponseBody
    @RequestMapping(value = "/account/reset-password/finish", method = RequestMethod.POST)
    public void resetPasswordFinish(@Valid @RequestBody KeyAndPasswordVM keyAndPassword) throws ServiceProcessException {
        ManageUser user = manageUserService.completePasswordReset(
                keyAndPassword.getKey(), passwordEncoder.encode(keyAndPassword.getNewPassword()));
        if (user == null) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
