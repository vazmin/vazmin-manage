package com.github.vazmin.manage.support.security.filter;

import com.github.vazmin.framework.web.support.model.ModuleTree;
import com.github.vazmin.manage.component.model.users.ManageUser;
import com.github.vazmin.manage.support.security.ManageUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 命令请求授权过滤器，适用于每次request请求，根据访问的URL路径从用户权限列表进行匹配，
 * 如果匹配失败，抛出 AccessDeniedException 异常，由 AccessDeniedHandler 处理。
 *
 */
public class CommandAccessDecisionFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(CommandAccessDecisionFilter.class);

    @Autowired
    private ModuleTree moduleTree;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException, AccessDeniedException {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            ManageUserDetails manageUserDetails = (ManageUserDetails)authentication.getPrincipal();
            String requestPath = getRequestPathNoSuffix(request);
            log.debug("check permission requestPath=" + requestPath);
            if (!manageUserDetails.hasPermission(moduleTree.getCommand(requestPath))) {
                throw new AccessDeniedException("Has no permission.");
            } else {
                ManageUser manageUser = manageUserDetails.getManageUser();
                manageUser.setManageRoleList(manageUserDetails.getManageRoleList());
                request.setAttribute("manageUser", manageUser);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 从请求对象中获取请求的路径
     * @param request HttpServletRequest
     * @return String 请求的地址
     */
    private String getRequestPathNoSuffix(HttpServletRequest request) {
        String url = request.getServletPath();
        if (request.getPathInfo() != null) {
            url += request.getPathInfo();
        }
        int lastDotIndex = url.lastIndexOf(".");
        if (lastDotIndex != -1) {
            url = url.substring(0, lastDotIndex);
        }
        return url;
    }
}