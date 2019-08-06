package com.github.vazmin.manage.log.context.enu.options;

import org.apache.commons.lang3.StringUtils;

/**
* 用户操作日志 列表排序枚举类
*
* Created by zhiming on 10/24/16.
*/
public enum CommandLogOrderingEnum {
    ID_ASC(1, "a.id ASC", "commandLog.id.asc", "记录id 升序"),
    ID_DESC(2, "a.id DESC", "commandLog.id.desc", "记录id 降序"),
    USER_ID_ASC(3, "a.user_id ASC", "commandLog.user_id.asc", "用户id 升序"),
    USER_ID_DESC(4, "a.user_id DESC", "commandLog.user_id.desc", "用户id 降序"),
    NAME_ASC(5, "a.name ASC", "commandLog.name.asc", "姓名 升序"),
    NAME_DESC(6, "a.name DESC", "commandLog.name.desc", "姓名 降序"),
    COMMAND_ID_ASC(7, "a.command_id ASC", "commandLog.command_id.asc", "命令id 升序"),
    COMMAND_ID_DESC(8, "a.command_id DESC", "commandLog.command_id.desc", "命令id 降序"),
    COMMAND_NAME_ASC(9, "a.command_name ASC", "commandLog.command_name.asc", "命令名称 升序"),
    COMMAND_NAME_DESC(10, "a.command_name DESC", "commandLog.command_name.desc", "命令名称 降序"),
    MODULE_NAME_ASC(11, "a.module_name ASC", "commandLog.module_name.asc", "模块名称 升序"),
    MODULE_NAME_DESC(12, "a.module_name DESC", "commandLog.module_name.desc", "模块名称 降序"),
    REQUEST_PATH_ASC(13, "a.request_path ASC", "commandLog.request_path.asc", "请求路径 升序"),
    REQUEST_PATH_DESC(14, "a.request_path DESC", "commandLog.request_path.desc", "请求路径 降序"),
    PARAMETERS_ASC(15, "a.parameters ASC", "commandLog.parameters.asc", "请求参数 升序"),
    PARAMETERS_DESC(16, "a.parameters DESC", "commandLog.parameters.desc", "请求参数 降序"),
    USER_IP_ASC(17, "a.user_ip ASC", "commandLog.user_ip.asc", "用户IP 升序"),
    USER_IP_DESC(18, "a.user_ip DESC", "commandLog.user_ip.desc", "用户IP 降序"),
    RESULT_SUCCESSS_ASC(19, "a.result_successs ASC", "commandLog.result_successs.asc", "操作是否成功 升序"),
    RESULT_SUCCESSS_DESC(20, "a.result_successs DESC", "commandLog.result_successs.desc", "操作是否成功 降序"),
    RESULT_MESSAGE_ASC(21, "a.result_message ASC", "commandLog.result_message.asc", "操作提示信息 升序"),
    RESULT_MESSAGE_DESC(22, "a.result_message DESC", "commandLog.result_message.desc", "操作提示信息 降序"),
    ACTION_TIME_ASC(23, "a.action_time ASC", "commandLog.action_time.asc", "操作时间 升序"),
    ACTION_TIME_DESC(24, "a.action_time DESC", "commandLog.action_time.desc", "操作时间 降序");

    /** id */
    private Integer id;
    /** option选项值 */
    private String value;
    /** 本地化标识 */
    private String textCode;
    /** 描述 */
    private String description;

    CommandLogOrderingEnum(
            Integer id, String value, String textCode, String description) {
        this.id = id;
        this.value = value;
        this.textCode = textCode;
        this.description = description;
    }

    /**
    * 根据id值获取对应的枚举值
    * @param id String id值
    * @return CommandLogOrderingEnum 枚举值，如果不存在返回 null
    */
    public static CommandLogOrderingEnum valueOf(Integer id) {
        if (id != null) {
            for (CommandLogOrderingEnum orderingEnum: values()) {
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
    * @return CommandLogOrderingEnum 排序枚举值，如果不存在，返回 null
    */
    public static CommandLogOrderingEnum enumOfValue(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (CommandLogOrderingEnum orderingEnum: values()) {
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
        CommandLogOrderingEnum orderingEnum = valueOf(id);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }

    /**
     * 根据value 直接获取描述信息
     * @param value String 值字符串
     * @return String 对应枚举的描述，如果不是有效枚举返回null
     */
    public static String getDescriptionOfValue(String value) {
        CommandLogOrderingEnum orderingEnum = enumOfValue(value);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }

    /**
     * 返回默认枚举值
     * @return CommandLogOrderingEnum 默认枚举值
     */
    public static CommandLogOrderingEnum getDefaultEnum() {
        return USER_ID_ASC;
    }
}