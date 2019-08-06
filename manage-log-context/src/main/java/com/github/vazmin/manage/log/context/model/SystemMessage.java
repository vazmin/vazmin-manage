package com.github.vazmin.manage.log.context.model;

import com.github.vazmin.framework.core.util.DateUtil;
import com.github.vazmin.manage.log.context.enu.MessageLevelEnum;
import org.springframework.web.util.HtmlUtils;

import java.io.Serializable;

/**
* 系统错误日志 Bean
*
 *
*/
public class SystemMessage implements Serializable {
    private static final long serialVersionUID = 273428691095768907L;
    /** 记录id */
    private Long id;
    /** 消息标题 */
    private String title;
    /** 消息内容 */
    private String message;
    /** 消息级别，0-调试，1-普通，2-警告，3-错误 */
    private Integer messageLevel;
    /** 消息所在模块（类） */
    private String messageModule;
    /** 消息所在位置 */
    private String messageLine;
    /** 消息时间 */
    private Long messageTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public String getEscapeMessage() {
        return HtmlUtils.htmlEscape(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMessageLevel() {
        return messageLevel;
    }

    public String getMessageLevelDescription() {
        return MessageLevelEnum.getDescription(messageLevel);
    }

    public void setMessageLevel(Integer messageLevel) {
        this.messageLevel = messageLevel;
    }

    public String getMessageModule() {
        return messageModule;
    }

    public void setMessageModule(String messageModule) {
        this.messageModule = messageModule;
    }

    public String getMessageLine() {
        return messageLine;
    }

    public void setMessageLine(String messageLine) {
        this.messageLine = messageLine;
    }

    public Long getMessageTime() {
        return messageTime;
    }

    public String getMessageTimeStr() {
        return DateUtil.fullFormat(messageTime);
    }

    public void setMessageTime(Long messageTime) {
        this.messageTime = messageTime;
    }
}