package com.github.vazmin.manage.log.context.enu;

/**
 * 系统错误消息级别枚举类
 *
 */
public enum MessageLevelEnum {
    DEBUG(0, "调试"),
    INFO(1, "普通"),
    WARN(2, "警告"),
    ERROR(3, "错误"),
    UNKNOWN(-1, "未知");

    private Integer code;
    private String description;

    private MessageLevelEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据code值获取对应的枚举值
     * @param code String code值
     * @return MessageLevelEnum 枚举值，如果不存在返回 UNKNOWN
     */
    public static MessageLevelEnum valueOf(Integer code) {
        if (code != null) {
            for (MessageLevelEnum typeEnum: values()) {
                if (typeEnum.getValue().intValue() == code.intValue()) {
                    return typeEnum;
                }
            }
        }
        return UNKNOWN;
    }

    public Integer getValue() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据code 直接获取描述信息
     * @param code Integer 数字编号
     * @return String 对应枚举的描述，如果不是有效枚举返回“未知”
     */
    public static String getDescription(Integer code){
        return valueOf(code).getDescription();
    }
}
