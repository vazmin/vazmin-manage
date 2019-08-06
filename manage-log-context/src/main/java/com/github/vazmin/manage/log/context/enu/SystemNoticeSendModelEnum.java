package com.github.vazmin.manage.log.context.enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 系统通知发送方式枚举
 * Created by zhiming on 2016/9/27.
 */
public enum SystemNoticeSendModelEnum {
    EMAIL(0, "电子邮件"),
    WECHAT(1, "微信"),
    UNKNOWN(-1, "未知");
    private Integer code;
    private String description;

    SystemNoticeSendModelEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据code值获取对应的枚举值
     * @param code String code值
     * @return DevTypeEnum 枚举值，如果不存在返回 UNKNOWN
     */
    public static SystemNoticeSendModelEnum valueOf(Integer code) {
        if (code != null) {
            for (SystemNoticeSendModelEnum typeEnum: values()) {
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

    public static List<SystemNoticeSendModelEnum> getEnumList() {
        List<SystemNoticeSendModelEnum> enumList = new ArrayList<>();
        Collections.addAll(enumList, values());
        enumList.remove(UNKNOWN);
        return enumList;
    }
}
