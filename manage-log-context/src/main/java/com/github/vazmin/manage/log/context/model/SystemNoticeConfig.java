package com.github.vazmin.manage.log.context.model;

import java.io.Serializable;

/**
* 系统通知配置 Bean
*
* Created by zhiming on 9/26/16.
*/
public class SystemNoticeConfig implements Serializable {

    /** 记录id */
    private Long id;
    /** 用户id */
    private Long userId;
    /** 通知类型 */
    private Integer noticeType;
    /** 是否邮箱接收 */
    private Integer receiveEmail;
    /** 是否微信接收 */
    private Integer receiveWechat;
    /** 用户登录名 */
    private String username;
    /** 邮箱地址 */
    private String email;
    /** 电话 */
    private String phone;
    /** openid */
    private String openid;
    /** 通知类型描述 */
    private String noticeTypeDescription;

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

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeTypeDescription() {
        return noticeTypeDescription;
    }

    public void setNoticeTypeDescription(String noticeTypeDescription) {
        this.noticeTypeDescription = noticeTypeDescription;
    }

    public Integer getReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(Integer receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public Integer getReceiveWechat() {
        return receiveWechat;
    }

    public void setReceiveWechat(Integer receiveWechat) {
        this.receiveWechat = receiveWechat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}