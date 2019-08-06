package com.github.vazmin.manage.component.controller.advice;

import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.core.util.DateUtil;
import com.github.vazmin.framework.core.util.GsonUtils;
import com.github.vazmin.framework.web.support.model.CommandInfo;
import com.github.vazmin.framework.web.support.model.ModuleTree;
import com.github.vazmin.manage.component.model.users.ManageUser;
import com.github.vazmin.manage.log.context.model.CommandLog;
import com.github.vazmin.manage.log.context.model.CommandResultMessage;
import com.github.vazmin.manage.log.context.service.CommandLogService;
import com.github.vazmin.manage.component.model.NgxSupportConstant;
import com.github.vazmin.manage.support.security.ManageUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.net.URI;

/**
 * 日志记录
 * 认证用户才会记录
 * 只记录url上的参数，只记录header上的操作结果
 * Created by Chwing on 2018/10/16.
 */
@ControllerAdvice
public class CommandLogControllerAdvice implements ResponseBodyAdvice<Object> {
    private static final Logger log = LoggerFactory.getLogger(CommandLogControllerAdvice.class);

    @Autowired
    protected ModuleTree moduleTree;

    @Autowired
    private CommandLogService commandLogService;

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug(converterType.getName());
        return GsonHttpMessageConverter.class.isAssignableFrom(converterType)
                || MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return body;
        }
        ManageUserDetails manageUserDetails = (ManageUserDetails)authentication.getPrincipal();
        ManageUser manageUser = manageUserDetails.getManageUser();

        URI uri = request.getURI();
        CommandInfo commandInfo = moduleTree != null ? moduleTree.getCommand(uri.getPath()) : null;
        if (commandInfo == null) {
            log.debug("no matched commandInfo for: " + uri.getPath());
        }
        logHandle(request, response, manageUser, uri, commandInfo);
        return body;
    }


    private void logHandle(ServerHttpRequest request, ServerHttpResponse response, ManageUser manageUser, URI uri, CommandInfo commandInfo) {
        if (commandInfo != null && commandInfo.isTrace()) {
            HttpHeaders httpHeaders = response.getHeaders();
            CommandLog commandLog = new CommandLog();
            commandLog.setUserId(manageUser.getId());
            commandLog.setName(manageUser.getName());
            commandLog.setCommandId(commandInfo.getId());
            commandLog.setCommandName(commandInfo.getValue());
            commandLog.setModuleName(commandInfo.getModule().getValue());
            commandLog.setRequestPath(commandInfo.getPath());
            commandLog.setUserIp(request.getRemoteAddress().getAddress().toString());
            commandLog.setActionTime(DateUtil.getTimestamp());
            commandLog.setParameters(uri.getQuery());
            commandLog.setResultMessage(
                    GsonUtils.toJson(
                            new CommandResultMessage(
                                    httpHeaders.getFirst(NgxSupportConstant.X_APP_ALERT),
                                    httpHeaders.getFirst(NgxSupportConstant.X_APP_PARAMS),
                                    httpHeaders.getFirst(NgxSupportConstant.X_APP_ERROR)), false)
            );
            try {
                commandLogService.insert(commandLog);
            } catch (ServiceProcessException e) {
                log.error("记录日志失败，path:{}", uri.getPath(), e);
            }
        }
    }

}
