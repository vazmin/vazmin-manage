package com.github.vazmin.manage.support.security;


import com.github.vazmin.framework.core.enu.StatusEnum;
import com.github.vazmin.framework.web.support.model.MenuInfo;
import com.github.vazmin.framework.web.support.model.ModuleInfo;
import com.github.vazmin.manage.component.model.Constants;
import com.github.vazmin.manage.component.model.users.ManageRole;
import com.github.vazmin.manage.component.model.users.ManageUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户认证详细信息实现类
 *
 */
public class ManageUserDetails extends PermissionUserDetails implements UserDetails {
    private static final Logger log = LoggerFactory.getLogger(ManageUserDetails.class);
    private static final long serialVersionUID = -5800748790160486625L;

    private ManageUser manageUser;

    public ManageUserDetails(ManageUser manageUser) {
        this.manageUser = manageUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities =  new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE0));
        return grantedAuthorities;
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
        if (getManageRoleList() != null) {
            for (ManageRole manageRole: getManageRoleList()) {
                if (manageRole.isAManager()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 添加用户的权限集合, 用户成功登录时会触发调用该方法
     * @param keySet Set<String>
     */
    public void addPrivilegeKeys(Set<String> keySet) {
        getPrivilegeKeySet().addAll(keySet);
    }

    public List<ManageRole> getManageRoleList() {
        return manageUser.getManageRoleList();
    }

    @Override
    protected Set<String> getPrivilegeKeySet() {
        return getManageUser().getPrivilegeKeySet();
    }
}
