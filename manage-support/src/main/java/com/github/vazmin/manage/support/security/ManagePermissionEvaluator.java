package com.github.vazmin.manage.support.security;

import com.github.vazmin.framework.web.support.model.CommandInfo;
import com.github.vazmin.framework.web.support.model.MenuInfo;
import com.github.vazmin.framework.web.support.model.ModuleInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * 权限计算实现类,用于spring security 标签中的 hasPermission()
 *
 */
public class ManagePermissionEvaluator implements PermissionEvaluator {

    private static final Logger log = LoggerFactory.getLogger(ManagePermissionEvaluator.class);

    /**
     * 如果对象是菜单或命令,则直接判断是否有权限;
     * 根据 permission 从 targetObj 对应的模块信息中解析出目标命令,
     * 再用目标命令去匹配用户的权限集合;
     * 如果 permission 为空,则直接判断是否有模块权限,否则判断命令权限。
     */
    @Override
    public boolean hasPermission(
            Authentication authentication, Object targetObj, Object permission) {
        ManageUserDetails manageUserDetails = (ManageUserDetails) authentication.getPrincipal();
        if (targetObj instanceof MenuInfo) {
            return manageUserDetails.hasPermission((MenuInfo)targetObj);
        } else if (targetObj instanceof CommandInfo) {
            return manageUserDetails.hasPermission((CommandInfo)targetObj);
        }

        ModuleInfo moduleInfo = (ModuleInfo)targetObj;
        String commandStr = (String)permission;
        if (StringUtils.isBlank(commandStr)) {
            return manageUserDetails.hasPermission(moduleInfo);
        } else {
            CommandInfo targetCommand = null;
            for (CommandInfo command : moduleInfo.getCommandList()) {
                if (command.getPath().endsWith(commandStr)) {
                    targetCommand = command;
                    break;
                }
            }
            if (targetCommand == null) {
                log.warn("命令 (" + commandStr + ") 不在模块 "
                        + moduleInfo.getValue() + " 中, 请确认是否配置了正确的目标命令");
                return false;
            }
            return manageUserDetails.hasPermission(targetCommand);
        }
    }

    @Override
    public boolean hasPermission(
            Authentication authentication, Serializable serializable,
            String s, Object o) {
        log.debug("验证权限 s=" + s + ",o=" + o);
        return false;
    }
}
