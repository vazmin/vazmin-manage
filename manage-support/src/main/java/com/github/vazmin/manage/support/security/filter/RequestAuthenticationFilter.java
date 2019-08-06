package com.github.vazmin.manage.support.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Request 请求参数验证过滤器，适用于每次request请求，从传递的参数中获取账号和密码，
 * 构造验证凭证。
 *
 */
public class RequestAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(RequestAuthenticationFilter.class);

    @Autowired
    private AuthenticationManager internalAuthenticationManager;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String username = request.getParameter("username");
        if (StringUtils.isBlank(username)) {
            username = request.getParameter("userName");
        }
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        SecurityContextHolder.getContext().setAuthentication(authRequest);
//        try {
//            Authentication authResult = authenticationManager.authenticate(authRequest);
//            SecurityContextHolder.getContext().setAuthentication(authResult);
//        } catch (BadCredentialsException authException) {
//            log.error("认证失败：" + authException.getMessage());
//            response.setContentType("application/json;charset=UTF-8");
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
//                    authException.getMessage());
//            return;
//        }
        filterChain.doFilter(request, response);
    }
}
