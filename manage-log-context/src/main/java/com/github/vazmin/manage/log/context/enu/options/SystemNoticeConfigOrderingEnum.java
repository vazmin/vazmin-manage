package com.github.vazmin.manage.log.context.enu.options;

import org.apache.commons.lang3.StringUtils;

/**
* 系统通知配置 列表排序枚举类
*
* Created by zhiming on 9/26/16.
*/
public enum SystemNoticeConfigOrderingEnum {
    ID_ASC(1, "a.id ASC", "systemNoticeConfig.id.asc", "记录id 升序"),
    ID_DESC(2, "a.id DESC", "systemNoticeConfig.id.desc", "记录id 降序"),
    USER_ID_ASC(3, "a.user_id ASC", "systemNoticeConfig.user_id.asc", "用户id 升序"),
    USER_ID_DESC(4, "a.user_id DESC", "systemNoticeConfig.user_id.desc", "用户id 降序"),
    NOTICE_TYPE_ASC(5, "a.notice_type ASC", "systemNoticeConfig.notice_type.asc", "通知类型 升序"),
    NOTICE_TYPE_DESC(6, "a.notice_type DESC", "systemNoticeConfig.notice_type.desc", "通知类型 降序"),
    RECEIVE_EMAIL_ASC(7, "a.receive_email ASC", "systemNoticeConfig.receive_email.asc", "是否邮箱接收 升序"),
    RECEIVE_EMAIL_DESC(8, "a.receive_email DESC", "systemNoticeConfig.receive_email.desc", "是否邮箱接收 降序"),
    RECEIVE_WECHAT_ASC(9, "a.receive_wechat ASC", "systemNoticeConfig.receive_wechat.asc", "是否微信接收 升序"),
    RECEIVE_WECHAT_DESC(10, "a.receive_wechat DESC", "systemNoticeConfig.receive_wechat.desc", "是否微信接收 降序");

    /** id */
    private Integer id;
    /** option选项值 */
    private String value;
    /** 本地化标识 */
    private String textCode;
    /** 描述 */
    private String description;

    SystemNoticeConfigOrderingEnum(
            Integer id, String value, String textCode, String description) {
        this.id = id;
        this.value = value;
        this.textCode = textCode;
        this.description = description;
    }

    /**
    * 根据id值获取对应的枚举值
    * @param id String id值
    * @return SystemNoticeConfigOrderingEnum 枚举值，如果不存在返回 null
    */
    public static SystemNoticeConfigOrderingEnum valueOf(Integer id) {
        if (id != null) {
            for (SystemNoticeConfigOrderingEnum orderingEnum: values()) {
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
    * @return SystemNoticeConfigOrderingEnum 排序枚举值，如果不存在，返回 null
    */
    public static SystemNoticeConfigOrderingEnum enumOfValue(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (SystemNoticeConfigOrderingEnum orderingEnum: values()) {
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
        SystemNoticeConfigOrderingEnum orderingEnum = valueOf(id);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }

    /**
     * 根据value 直接获取描述信息
     * @param value String 值字符串
     * @return String 对应枚举的描述，如果不是有效枚举返回null
     */
    public static String getDescriptionOfValue(String value) {
        SystemNoticeConfigOrderingEnum orderingEnum = enumOfValue(value);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }

    /**
     * 返回默认枚举值
     * @return SystemNoticeConfigOrderingEnum 默认枚举值
     */
    public static SystemNoticeConfigOrderingEnum getDefaultEnum() {
        return USER_ID_ASC;
    }
}