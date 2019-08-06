package com.github.vazmin.manage.log.context.model;

import com.github.vazmin.framework.core.util.DateUtil;
import com.github.vazmin.manage.log.context.enu.SystemNoticeModelEnum;
import com.github.vazmin.manage.log.context.enu.SystemNoticeSendModelEnum;

import java.io.Serializable;

/**
* 系统通知记录 Bean
*
* Created by zhiming on 9/27/16.
*/
public class SystemNoticeLog implements Serializable {

    /** 记录id */
    private Long id;
    /** 用户id */
    private Long userId;
    /** 通知类型 */
    private Integer noticeType;
    /** 发送方式 */
    private Integer sendMode;
    /** 发送标题 */
    private String title;
    /** 发送内容 */
    private String sendContent;
    /** 发送异常消息 */
    private String errorMsg;
    /** 通知时间 */
    private Long noticeTime;
    /** 用户登录名 */
    private String username;
    /** 发送结果 */
    private Integer result;

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

    public String getNoticeTypeDescription(){
        return SystemNoticeModelEnum.getDescription(noticeType);
    }

    public Integer getSendMode() {
        return sendMode;
    }

    public void setSendMode(Integer sendMode) {
        this.sendMode = sendMode;
    }

    public String getSendModeDescription(){
        return SystemNoticeSendModelEnum.getDescription(sendMode);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Long getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Long noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getNoticeTimeFormat(){
        return DateUtil.fullFormat(noticeTime);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}