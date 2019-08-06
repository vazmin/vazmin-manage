package com.github.vazmin.manage.support.security;


import com.github.vazmin.framework.core.enu.StatusEnum;
import com.github.vazmin.framework.web.support.model.MenuInfo;
import com.github.vazmin.framework.web.support.model.ModuleInfo;
import com.github.vazmin.manage.component.model.users.ManageRole;
import com.github.vazmin.manage.component.model.users.ManageUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户认证详细信息实现类
 *
 */
public class ManageUserDetails extends PermissionUserDetails implements UserDetails {
    private static final Logger log = LoggerFactory.getLogger(ManageUserDetails.class);
    private static final long serialVersionUID = -5800748790160486625L;

    private ManageUser manageUser;

//    /** 模块权限key集合 */
//    private Set<String> privilegeKeySet;

    /** 用户设置的角色列表 */
    private List<ManageRole> manageRoleList;

    public ManageUserDetails(ManageUser manageUser) {
        privilegeKeySet = new HashSet<>();
        this.manageUser = manageUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return manageUser.getPassword();
    }

    @Override
    public String getUsername() {
        return manageUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonLocked();
    }

    @Override
    public boolean isAccountNonLocked() {
        return StatusEnum.valueOf(manageUser.getStatus()) == StatusEnum.VALID;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNonLocked();
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonLocked();
    }

    public ManageUser getManageUser() {
        return manageUser;
    }

    public ManageUser getManageUserNoPassword() {
        manageUser.setPassword(null);
        return manageUser;
    }

    /**
     * 判断是否是超级管理员
     * @return boolean 是返回true, 否返回false
     */
    @Override
    public boolean isAdmin() {
        return manageUser.isAdmin();
    }

    /**
     * 判断用户是否是管理层
     * @return boolean 是返回true, 否返回false
     */
    public boolean isManager() {
        if (manageRoleList != null) {
            for (ManageRole manageRole: manageRoleList) {
                if (manageRole.isAManager()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断用户是否有模块的使用权限,如果为超级管理员可使用,
     * 否则需要根据用户的权限集合判断是否可用。
     * @param moduleInfo ModuleInfo 模块信息对象
     * @return 如果允许使用返回 true, 否则返回 false
     */
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
    public boolean hasPermission(MenuInfo menuInfo) {
        return isAdmin() || (menuInfo != null
                && (privilegeKeySet.contains(menuInfo.getMenuKey())
                    || menuInfo.isAllowAccessAuthenticated()));
    }

    /**
     * 添加用户的权限集合, 用户成功登录时会触发调用该方法
     * @param keySet Set<String>
     */
    public void addPrivilegeKeys(Set<String> keySet) {
        privilegeKeySet.addAll(keySet);
    }

    public void setManageRoleList(List<ManageRole> manageRoleList) {
        this.manageRoleList = manageRoleList;
    }

    public List<ManageRole> getManageRoleList() {
        return manageRoleList;
    }
}
