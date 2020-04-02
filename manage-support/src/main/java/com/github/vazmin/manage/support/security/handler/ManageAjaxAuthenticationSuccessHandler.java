package com.github.vazmin.manage.support.security.handler;

import com.github.vazmin.manage.component.model.users.ManageRole;
import com.github.vazmin.manage.component.service.users.ManageRoleService;
import com.github.vazmin.manage.component.service.users.ManageUserService;
import com.github.vazmin.manage.support.security.ManageUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录成功处理器
 *
 */
public class ManageAjaxAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {
    protected final Logger log = LoggerFactory.getLogger(ManageAjaxAuthenticationSuccessHandler.class);

//    private RequestCache requestCache = new HttpSessionRequestCache();
//
//    private static final String CLIENT_TYPE_HEADER = "x-client-type";

    @Autowired
    private ManageUserService manageUserService;

    @Autowired
    private ManageRoleService manageRoleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        log.debug("登录成功，提取用户权限集合保存到用户的凭证中");

        ManageUserDetails manageUserDetails = (ManageUserDetails)authentication.getPrincipal();
        if (!manageUserDetails.isAdmin()) {
            manageUserService.updateLastVisitDate(manageUserDetails.getManageUser().getId());
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}