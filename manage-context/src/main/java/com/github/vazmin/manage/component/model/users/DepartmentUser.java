package com.github.vazmin.manage.component.model.users;

import com.github.vazmin.framework.core.util.DateUtil;
import com.github.vazmin.manage.component.enu.users.UserPositionEnum;

/**
* 部门用户关系 Bean
*
 *
*/
public class DepartmentUser {

    /** 记录id */
    private Long id;
    /** 部门id */
    private Long departmentId;
    /** 用户id */
    private Long userId;
    /** 职位,0-普通,1-负责人 */
    private Integer userPosition;
    /** 指派时间 */
    private Long assignTime;
    /** 用户名称 */
    private String userName;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserPosition() {
        return userPosition;
    }

    public String getUserPositionDescription() {
        return UserPositionEnum.getDescription(userPosition);
    }

    public void setUserPosition(Integer userPosition) {
        this.userPosition = userPosition;
    }

    public Long getAssignTime() {
        return assignTime;
    }

    public String getAssignTimeStr() {
        return DateUtil.fullFormat(assignTime);
    }

    public void setAssignTime(Long assignTime) {
        this.assignTime = assignTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}