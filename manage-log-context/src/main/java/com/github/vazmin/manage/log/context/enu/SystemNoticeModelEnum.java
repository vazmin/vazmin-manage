package com.github.vazmin.manage.log.context.enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 系统通知类型枚举
 *
 * Created by zhiming on 2016/9/26.
 */
public enum SystemNoticeModelEnum {
    DEVELOPER_INVAILD(0, "开发者账号失效通知"),
    ANNOUNCE(1, "公告通知"),
    TASK(2, "任务通知"),
    WARNING(3, "预警通知"),
    RESET_PASS(4, "重置密码"),
    UNKNOWN(-1, "未知");
    private Integer code;
    private String description;

    SystemNoticeModelEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据code值获取对应的枚举值
     * @param code String code值
     * @return DevTypeEnum 枚举值，如果不存在返回 UNKNOWN
     */
    public static SystemNoticeModelEnum valueOf(Integer code) {
        if (code != null) {
            for (SystemNoticeModelEnum typeEnum: values()) {
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

    public static List<SystemNoticeModelEnum> getEnumList() {
        List<SystemNoticeModelEnum> enumList = new ArrayList<>();
        Collections.addAll(enumList, values());
        enumList.remove(UNKNOWN);
        return enumList;
    }
}
