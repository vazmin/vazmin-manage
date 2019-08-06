package com.github.vazmin.manage.support.security;

import com.github.vazmin.framework.core.enu.StatusEnum;
import com.github.vazmin.manage.component.model.users.ManageUser;
import com.github.vazmin.manage.component.service.users.ManageUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
            ManageUser manageUser = new ManageUser();
            manageUser.setId(0L);
            manageUser.setUsername(adminUsername);
            manageUser.setPassword(adminPassword);
            manageUser.setName("超级管理员");
            manageUser.setAdmin(true);
            manageUser.setStatus(StatusEnum.VALID.getValue());
            return new ManageUserDetails(manageUser);
        } else {
            ManageUser manageUser = manageUserService.getByUsername(username);
            if (manageUser != null) {
                return new ManageUserDetails(manageUser);
            } else {
                throw new UsernameNotFoundException("The user not exist.");
            }
        }
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
