package com.github.vazmin.manage.support.security;

import com.github.vazmin.framework.core.enu.StatusEnum;
import com.github.vazmin.manage.component.model.users.ManageRole;
import com.github.vazmin.manage.component.model.users.ManageUser;
import com.github.vazmin.manage.component.service.users.ManageRoleService;
import com.github.vazmin.manage.component.service.users.ManageUserService;
import com.github.vazmin.manage.component.service.users.UserCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Spring Security 认证接口实现类
 *
 */
public class ManageUserDetailsService implements UserDetailsService {

    private static final Logger log =
            LoggerFactory.getLogger(ManageUserDetailsService.class);

    /** 管理员用户名 */
    private String adminUsername;
    /** 管理员密码 */
    private String adminPassword;

    @Autowired
    private ManageUserService manageUserService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        if (adminUsername.equals(username)) {
            ManageUser manageUser = manageUserService.buildAdmin(adminUsername, adminPassword);
            return new ManageUserDetails(manageUser);
        } else {
            ManageUser manageUser = manageUserService.getByUsernameTakePrincipal(username);
            if (manageUser == null) {
                throw new UsernameNotFoundException("The user not exist.");
            }
            return new ManageUserDetails(manageUser);
        }
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
