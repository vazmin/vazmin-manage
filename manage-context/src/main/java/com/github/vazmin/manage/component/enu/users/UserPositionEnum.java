package com.github.vazmin.manage.component.enu.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 成员职位枚举类
 *  职位,0-普通,1-负责人
 *
 */
public enum UserPositionEnum {
    MANAGER(1, "负责人"),
    NORMAL(2, "普通"),
    UNKNOWN(-1, "未知");

    private Integer code;
    private String description;

    private UserPositionEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据code值获取对应的枚举值
     * @param code String code值
     * @return UserPositionEnum 枚举值，如果不存在返回 UNKNOWN
     */
    public static UserPositionEnum valueOf(Integer code) {
        if (code != null) {
            for (UserPositionEnum itemTypeEnum: values()) {
                if (itemTypeEnum.getValue().intValue() == code.intValue()) {
                    return itemTypeEnum;
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

    public static List<UserPositionEnum> getEnumList() {
        List<UserPositionEnum> enumList = new ArrayList<>();
        Collections.addAll(enumList, values());
        enumList.remove(UNKNOWN);
        return enumList;
    }
}
