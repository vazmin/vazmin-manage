package com.github.vazmin.manage.log.context.enu.options;

import org.apache.commons.lang3.StringUtils;

/**
* 系统错误日志 列表排序枚举类
*
 *
*/
public enum SystemMessageOrderingEnum {
    ID_ASC(1, "a.id ASC", "systemMessage.id.asc", "记录id 升序"),
    ID_DESC(2, "a.id DESC", "systemMessage.id.desc", "记录id 降序"),
    TITLE_ASC(3, "a.title ASC", "systemMessage.title.asc", "消息标题 升序"),
    TITLE_DESC(4, "a.title DESC", "systemMessage.title.desc", "消息标题 降序"),
    MESSAGE_ASC(5, "a.message ASC", "systemMessage.message.asc", "消息内容 升序"),
    MESSAGE_DESC(6, "a.message DESC", "systemMessage.message.desc", "消息内容 降序"),
    MESSAGE_LEVEL_ASC(7, "a.message_level ASC", "systemMessage.message_level.asc", "消息级别 升序"),
    MESSAGE_LEVEL_DESC(8, "a.message_level DESC", "systemMessage.message_level.desc", "消息级别 降序"),
    MESSAGE_MODULE_ASC(9, "a.message_module ASC", "systemMessage.message_module.asc", "消息所在模块（类） 升序"),
    MESSAGE_MODULE_DESC(10, "a.message_module DESC", "systemMessage.message_module.desc", "消息所在模块（类） 降序"),
    MESSAGE_LINE_ASC(11, "a.message_line ASC", "systemMessage.message_line.asc", "消息所在位置 升序"),
    MESSAGE_LINE_DESC(12, "a.message_line DESC", "systemMessage.message_line.desc", "消息所在位置 降序"),
    MESSAGE_TIME_ASC(13, "a.message_time ASC", "systemMessage.message_time.asc", "消息时间 升序"),
    MESSAGE_TIME_DESC(14, "a.message_time DESC", "systemMessage.message_time.desc", "消息时间 降序");

    /** id */
    private Integer id;
    /** option选项值 */
    private String value;
    /** 本地化标识 */
    private String textCode;
    /** 描述 */
    private String description;

    SystemMessageOrderingEnum(
            Integer id, String value, String textCode, String description) {
        this.id = id;
        this.value = value;
        this.textCode = textCode;
        this.description = description;
    }

    /**
    * 根据id值获取对应的枚举值
    * @param id String id值
    * @return SystemMessageOrderingEnum 枚举值，如果不存在返回 null
    */
    public static SystemMessageOrderingEnum valueOf(Integer id) {
        if (id != null) {
            for (SystemMessageOrderingEnum orderingEnum: values()) {
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
    * @return SystemMessageOrderingEnum 排序枚举值，如果不存在，返回 null
    */
    public static SystemMessageOrderingEnum enumOfValue(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (SystemMessageOrderingEnum orderingEnum: values()) {
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
        SystemMessageOrderingEnum orderingEnum = valueOf(id);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }

    /**
     * 根据value 直接获取描述信息
     * @param value String 值字符串
     * @return String 对应枚举的描述，如果不是有效枚举返回null
     */
    public static String getDescriptionOfValue(String value) {
        SystemMessageOrderingEnum orderingEnum = enumOfValue(value);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }

    /**
     * 返回默认枚举值
     * @return SystemMessageOrderingEnum 默认枚举值
     */
    public static SystemMessageOrderingEnum getDefaultEnum() {
        return TITLE_ASC;
    }
}