package com.github.vazmin.manage.component.model.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.vazmin.framework.core.enu.StatusEnum;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * URP用户信息Bean
 *
 */
@JsonIgnoreProperties({"resetKey"})
public class ManageUser implements Serializable {
    private static final long serialVersionUID = 721326756473686798L;
    /** 用户id */
    private Long id;
    /** 系统用户名（邮箱） */
    private String username;
    /** 登录密码 */
    // @JsonIgnore
    private String password;
    /** 确认登录密码 */
    private String confirm;
    /** 姓名 */
    private String name;
    /** 电话 */
    private String phone;
    /** 业务邮箱，默认与用户名邮箱相同 */
    private String email;
    /** 微信号 */
    private String wechatId;
    /** 微信公众号对应的openid，需关注公司内部微信公众号 */
    private String openid;
    /** 备注 */
    private String memo;
    /** 0-停用，1-启用 */
    private Integer status;
    /** 0-否，1-是 */
    private Integer sendEmail;
    /** 创建时间 */
    private Long createDate;
    /** 最后访问时间 */
    private Long lastVisitDate;

    private boolean admin;

    private String resetKey;

    private Long resetDate;

    /** 用户角色id集合 */
    private Set<Long> roleIdSet = new HashSet<>();
    /** 用户设置的角色列表 */
    private List<ManageRole> manageRoleList;

    /**
     * 判断用户是否赋予了指定角色
     * @param roleId Long 角色id
     * @return boolean 有返回true，没有返回false
     */
    public boolean contains(Long roleId) {
        return roleIdSet.contains(roleId);
    }

    public Set<Long> getRoleIdSet() {
        return roleIdSet;
    }

    public void setRoleIdSet(Set<Long> roleIdSet) {
        this.roleIdSet = roleIdSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailOrUsername() {
        return getEmail() == null ? getUsername() : getEmail();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public Long getCreateDate() {
        return createDate;
    }

//    public String getCreateDateStr() {
//        return createDate == null ? null : DateUtil.fullFormat(createDate);
//    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getLastVisitDate() {
        return lastVisitDate;
    }

//    public String getLastVisitDateStr() {
//        return DateUtil.fullFormat(lastVisitDate);
//    }

    public void setLastVisitDate(Long lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public Integer getSendEmail() {
        return sendEmail;
    }

//    public String getSendEmailDescription() {
//        return (sendEmail != null & sendEmail == 1) ? "是" : "否";
//    }

    public void setSendEmail(Integer sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public boolean isAdmin() {
        return admin;
    }

    public List<ManageRole> getManageRoleList() {
        return manageRoleList;
    }

    public void setManageRoleList(List<ManageRole> manageRoleList) {
        this.manageRoleList = manageRoleList;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Long getResetDate() {
        return resetDate;
    }

    public void setResetDate(Long resetDate) {
        this.resetDate = resetDate;
    }
}
