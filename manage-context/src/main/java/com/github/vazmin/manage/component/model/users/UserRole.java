package com.github.vazmin.manage.component.model.users;

import java.io.Serializable;

/**
 * 平台用户角色关联信息Bean
 *
 */
public class UserRole implements Serializable {
    private static final long serialVersionUID = 6204540957253538212L;
    /** 记录id */
    private Long id;
    /** 系统用户id */
    private Long userId;
    /** 角色id */
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
