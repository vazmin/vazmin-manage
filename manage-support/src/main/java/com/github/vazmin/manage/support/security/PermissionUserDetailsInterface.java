package com.github.vazmin.manage.support.security;

import com.github.vazmin.framework.web.support.model.CommandInfo;
import com.github.vazmin.framework.web.support.model.MenuInfo;
import com.github.vazmin.framework.web.support.model.ModuleInfo;

/**
 * Created by Chwing on 2018/12/17.
 */
public interface PermissionUserDetailsInterface {
    boolean hasPermission(CommandInfo commandInfo);
    boolean hasPermission(ModuleInfo moduleInfo);
    boolean hasPermission(MenuInfo menuInfo);
}
