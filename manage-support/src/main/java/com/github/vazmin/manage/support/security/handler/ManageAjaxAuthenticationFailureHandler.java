package com.github.vazmin.manage.support.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ajax 登录失败处理
 * Created by Chwing on 2018/2/26.
 */
public class ManageAjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public static final String UNAUTHORIZED_MESSAGE = "Authentication failed";

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_MESSAGE);
    }
}
