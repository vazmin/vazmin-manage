package com.github.vazmin.manage.component.model.users;

import com.github.vazmin.manage.component.enu.system.ItemTypeEnum;

import java.io.Serializable;

/**
 * 平台角色权限关联信息Bean
 *
 */
public class RolePrivilege implements Serializable {
    private static final long serialVersionUID = -8817999149160753661L;
    /** 记录id */
    private Long id;
    /** 系统角色id */
    private Long roleId;
    /** 条目id */
    private Long itemId;
    /** 条目类型，0-菜单，1-模块，2-命令 */
    private Integer itemType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public String getItemTypeDescription() {
        return ItemTypeEnum.getDescription(itemType);
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getPrivilegeKey() {
        return String.format("%s-%s", itemType, itemId);
    }
}
