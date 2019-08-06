package com.github.vazmin.manage.support.security;

import com.github.vazmin.framework.web.support.model.CommandInfo;
import com.github.vazmin.framework.web.support.model.MenuInfo;
import com.github.vazmin.framework.web.support.model.ModuleInfo;

import java.util.Set;

/**
 * 权限验证用户详情
 * Created by Chwing on 2018/12/17.
 */
public abstract class PermissionUserDetails implements PermissionUserDetailsInterface {

    /** 模块权限key集合 */
    protected Set<String> privilegeKeySet;


    protected abstract boolean isAdmin();

    /**
     * 判断用户是否有命令的使用权限,如果为超级管理员或模块信息为空可使用,
     * 否则需要根据用户的权限集合判断是否可用。
     * @param commandInfo CommandInfo 命令信息对象
     * @return 如果允许使用返回 true, 否则返回 false
     */
    @Override
    public boolean hasPermission(CommandInfo commandInfo) {
        return isAdmin() || (commandInfo == null || commandInfo.isAllowAccessAuthenticated()
                || privilegeKeySet.contains(commandInfo.getCommandKey()));
    }

    /**
     * 判断用户是否有模块的使用权限,如果为超级管理员可使用,
     * 否则需要根据用户的权限集合判断是否可用。
     * @param moduleInfo ModuleInfo 模块信息对象
     * @return 如果允许使用返回 true, 否则返回 false
     */
    @Override
    public boolean hasPermission(ModuleInfo moduleInfo) {
        return isAdmin() || (moduleInfo != null
                && (privilegeKeySet.contains(moduleInfo.getModuleKey())
                || moduleInfo.isAllowAccessAuthenticated()));
    }

    /**
     * 判断用户是否有菜单的使用权限,如果为超级管理员可使用,
     * 否则需要根据用户的权限集合判断是否可用。
     * @param menuInfo MenuInfo 菜单信息对象
     * @return 如果允许使用返回 true, 否则返回 false
     */
    @Override
    public boolean hasPermission(MenuInfo menuInfo) {
        return isAdmin() || (menuInfo != null
                && (privilegeKeySet.contains(menuInfo.getMenuKey())
                || menuInfo.isAllowAccessAuthenticated()));
    }
}
