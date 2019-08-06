package com.github.vazmin.manage.component.model.users;

import com.github.vazmin.framework.core.enu.StatusEnum;
import com.github.vazmin.framework.core.util.DateUtil;

/**
* 部门信息 Bean
*
 *
*/
public class DepartmentInfo {

    /** 部门id */
    private Long id;
    /** 部门名称 */
    private String departmentName;
    /** 部门备注 */
    private String departmentMemo;
    /** 0-停用，1-启用 */
    private Integer status;
    /** 创建时间 */
    private Long createTime;
    /** 业务组数 */
    private Integer groupCount;
    /** 部门成员数 */
    private Integer memberCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentMemo() {
        return departmentMemo;
    }

    public void setDepartmentMemo(String departmentMemo) {
        this.departmentMemo = departmentMemo;
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

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }
}