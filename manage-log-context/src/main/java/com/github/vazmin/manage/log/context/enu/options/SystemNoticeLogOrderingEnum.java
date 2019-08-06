package com.github.vazmin.manage.log.context.enu.options;

import org.apache.commons.lang3.StringUtils;

/**
* 系统通知记录 列表排序枚举类
*
* Created by zhiming on 9/27/16.
*/
public enum SystemNoticeLogOrderingEnum {
    ID_ASC(1, "a.id ASC", "systemNoticeLog.id.asc", "记录id 升序"),
    ID_DESC(2, "a.id DESC", "systemNoticeLog.id.desc", "记录id 降序"),
    USER_ID_ASC(3, "a.user_id ASC", "systemNoticeLog.user_id.asc", "用户id 升序"),
    USER_ID_DESC(4, "a.user_id DESC", "systemNoticeLog.user_id.desc", "用户id 降序"),
    NOTICE_TYPE_ASC(5, "a.notice_type ASC", "systemNoticeLog.notice_type.asc", "通知类型 升序"),
    NOTICE_TYPE_DESC(6, "a.notice_type DESC", "systemNoticeLog.notice_type.desc", "通知类型 降序"),
    SEND_MODE_ASC(7, "a.send_mode ASC", "systemNoticeLog.send_mode.asc", "发送方式 升序"),
    SEND_MODE_DESC(8, "a.send_mode DESC", "systemNoticeLog.send_mode.desc", "发送方式 降序"),
    SEND_CONTENT_ASC(9, "a.send_content ASC", "systemNoticeLog.send_content.asc", "发送内容 升序"),
    SEND_CONTENT_DESC(10, "a.send_content DESC", "systemNoticeLog.send_content.desc", "发送内容 降序"),
    NOTICE_TIME_ASC(11, "a.notice_time ASC", "systemNoticeLog.notice_time.asc", "通知时间 升序"),
    NOTICE_TIME_DESC(12, "a.notice_time DESC", "systemNoticeLog.notice_time.desc", "通知时间 降序");

    /** id */
    private Integer id;
    /** option选项值 */
    private String value;
    /** 本地化标识 */
    private String textCode;
    /** 描述 */
    private String description;

    SystemNoticeLogOrderingEnum(
            Integer id, String value, String textCode, String description) {
        this.id = id;
        this.value = value;
        this.textCode = textCode;
        this.description = description;
    }

    /**
    * 根据id值获取对应的枚举值
    * @param id String id值
    * @return SystemNoticeLogOrderingEnum 枚举值，如果不存在返回 null
    */
    public static SystemNoticeLogOrderingEnum valueOf(Integer id) {
        if (id != null) {
            for (SystemNoticeLogOrderingEnum orderingEnum: values()) {
                if (orderingEnum.getId().intValue() == id.intValue()) {
                    return orderingEnum;
                }
            }
        }
        return null;
    }

    /**
    * 根据value获取对应的枚举值
    * @param value String 值字符串
    * @return SystemNoticeLogOrderingEnum 排序枚举值，如果不存在，返回 null
    */
    public static SystemNoticeLogOrderingEnum enumOfValue(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (SystemNoticeLogOrderingEnum orderingEnum: values()) {
                if (orderingEnum.getValue().equals(value)) {
                    return orderingEnum;
                }
            }
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getTextCode() {
        return textCode;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据id 直接获取描述信息
     * @param id Integer 数字编号
     * @return String 对应枚举的描述，如果不是有效枚举返回null
     */
    public static String getDescription(Integer id) {
        SystemNoticeLogOrderingEnum orderingEnum = valueOf(id);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }

    /**
     * 根据value 直接获取描述信息
     * @param value String 值字符串
     * @return String 对应枚举的描述，如果不是有效枚举返回null
     */
    public static String getDescriptionOfValue(String value) {
        SystemNoticeLogOrderingEnum orderingEnum = enumOfValue(value);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }

    /**
     * 返回默认枚举值
     * @return SystemNoticeLogOrderingEnum 默认枚举值
     */
    public static SystemNoticeLogOrderingEnum getDefaultEnum() {
        return USER_ID_ASC;
    }
}