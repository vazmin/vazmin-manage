package com.github.vazmin.manage.component.model.users;


import com.github.vazmin.framework.core.enu.BoolEnum;
import com.github.vazmin.framework.core.enu.StatusEnum;

import java.io.Serializable;

/**
 * 平台角色信息Bean
 *
 */
public class ManageRole implements Serializable {
    private static final long serialVersionUID = 721326756473686798L;
    /** 角色id */
    private Long id;
    /** 角色名 */
    private String name;
    /** 备注 */
    private String memo;
    /** 0-停用，1-启用 */
    private Integer status;
    /** 谁否是管理层,0-否，1-是 */
    private Integer manager;
    /** 本角色用户数 */
    private Integer userCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return StatusEnum.getDescription(this.status);
    }

    public Integer getManager() {
        return manager;
    }

    public String getManagerDescription() {
        return BoolEnum.getDescription(manager);
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public boolean isAManager() {
        return BoolEnum.valueOf(manager) == BoolEnum.YES;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }
}
