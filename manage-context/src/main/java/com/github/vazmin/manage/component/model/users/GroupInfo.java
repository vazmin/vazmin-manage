package com.github.vazmin.manage.component.model.users;

import com.github.vazmin.framework.core.enu.StatusEnum;
import com.github.vazmin.framework.core.util.DateUtil;

/**
* 业务组信息 Bean
*
 *
*/
public class GroupInfo {

    /** 业务组id */
    private Long id;
    /** 所属部门id */
    private Long departmentId;
    /** 业务组名 */
    private String groupName;
    /** 备注 */
    private String groupMemo;
    /** 0-停用，1-启用 */
    private Integer status;
    /** 创建时间 */
    private Long createTime;
    /** 业务组成员数 */
    private Integer memberCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupMemo() {
        return groupMemo;
    }

    public void setGroupMemo(String groupMemo) {
        this.groupMemo = groupMemo;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusDescription() {
        return StatusEnum.getDescription(this.status);
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public String getCreateTimeStr() {
        return DateUtil.fullFormat(createTime);
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }
}