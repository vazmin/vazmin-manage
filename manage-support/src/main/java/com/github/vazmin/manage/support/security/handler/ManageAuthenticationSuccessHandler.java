package com.github.vazmin.manage.support.security.handler;

import com.github.vazmin.manage.component.service.users.ManageRoleService;
import com.github.vazmin.manage.component.service.users.ManageUserService;
import com.github.vazmin.manage.support.security.ManageUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 登录成功处理器
 *
 */
public class ManageAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {
    protected final Logger log = LoggerFactory.getLogger(ManageAuthenticationSuccessHandler.class);

    private static final String CLIENT_TYPE_HEADER = "x-client-type";

    private RequestCache requestCache = new HttpSessionRequestCache();

    private String defaultJsonSuccessURL;

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
            Set<String> privilegeSet =
                    manageUserService.getPrivilegeSet(manageUserDetails.getManageUser().getId());
            log.debug("privilegeSet=" + privilegeSet);
            if (privilegeSet != null) {
                manageUserDetails.addPrivilegeKeys(privilegeSet);
            }
            manageUserDetails.setManageRoleList(
                    manageRoleService.getListByUserId(manageUserDetails.getManageUser().getId()));
            manageUserService.updateLastVisitDate(manageUserDetails.getManageUser().getId());
        }

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            log.debug("savedRequest is null");
            log.debug("getDefaultTargetUrl=" + getDefaultTargetUrl());
            //如果header中有x-client-type=app，则跳转到默认json回应地址
            if (isAppLogin(request) && !StringUtils.isEmpty(defaultJsonSuccessURL)) {
                getRedirectStrategy().sendRedirect(request, response, defaultJsonSuccessURL);
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
            return;
        }
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParameter != null && StringUtils.hasText(request
                .getParameter(targetUrlParameter)))) {
            log.debug("isAlwaysUseDefaultTargetUrl=" + isAlwaysUseDefaultTargetUrl());
            log.debug("targetUrlParameter=" + targetUrlParameter);
            log.debug("targetUrl=" + request.getParameter(targetUrlParameter));
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     * 判断请求登录的是否时App客户端
     * @param request HttpServletRequest
     * @return 如果是客户端，则返回true，否则返回false。
     */
    private boolean isAppLogin(HttpServletRequest request) {
        String clientType = request.getHeader(CLIENT_TYPE_HEADER);
        return !StringUtils.isEmpty(clientType) && clientType.equalsIgnoreCase("app");
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    public void setDefaultJsonSuccessURL(String defaultJsonSuccessURL) {
        this.defaultJsonSuccessURL = defaultJsonSuccessURL;
    }
}