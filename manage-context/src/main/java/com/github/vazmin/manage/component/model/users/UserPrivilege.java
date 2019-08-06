package com.github.vazmin.manage.component.model.users;

import com.github.vazmin.manage.component.enu.system.ItemTypeEnum;

import java.io.Serializable;

/**
 * 平台用户权限关联信息Bean
 *
 */
public class UserPrivilege implements Serializable {
    private static final long serialVersionUID = -1440358832976332290L;
    /** 记录id */
    private Long id;
    /** 系统用户id */
    private Long userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getPrivilegeKey() {
        return String.format("%s-%s", itemType, itemId);
    }
}
