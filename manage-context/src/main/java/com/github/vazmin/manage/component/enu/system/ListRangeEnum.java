package com.github.vazmin.manage.component.enu.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 列表时间范围枚举类
 *
 */
public enum ListRangeEnum {
    TODAY(1, "today", "今天"),
    PAST_WEEK(7, "past_week", "最近 1 周"),
    PAST_1MONTH(30, "past_1month", "最近 1 个月"),
    PAST_3MONTH(90, "past_3month", "最近 3 个月"),
    PAST_6MONTH(180, "past_6month", "最近 6 个月"),
    PAST_YEAR(365, "past_year", "最近 1 年"),
    POST_YEAR(366, "post_year", "1 年以前"),
    UNKNOWN(-1, "unknown", "未知");

    /** id */
    private Integer id;
    /** 标识 */
    private String code;
    /** 描述 */
    private String description;

    ListRangeEnum(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    /**
     * 根据id值获取对应的枚举值
     * @param id String id值
     * @return ListLimitEnum 枚举值，如果不存在返回 null
     */
    public static ListRangeEnum valueOf(Integer id) {
        if (id != null) {
            for (ListRangeEnum orderingEnum: values()) {
                if (orderingEnum.getId().intValue() == id.intValue()) {
                    return orderingEnum;
                }
            }
        }
        return UNKNOWN;
    }

    /**
     * 根据code获取对应的枚举值
     * @param code String 标识
     * @return ListLimitEnum 枚举值，如果不存在返回 null
     */
    public static ListRangeEnum valueOfCode(String code) {
        if (code != null) {
            for (ListRangeEnum orderingEnum: values()) {
                if (orderingEnum.getCode().equals(code)) {
                    return orderingEnum;
                }
            }
        }
        return UNKNOWN;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据id 直接获取描述信息
     * @param id Integer 数字编号
     * @return String 对应枚举的描述，如果不是有效枚举返回“未知”
     */
    public static String getDescription(Integer id){
        return valueOf(id).getDescription();
    }

    public static List<ListRangeEnum> getEnumList() {
        List<ListRangeEnum> enumList = new ArrayList<>();
        Collections.addAll(enumList, values());
        enumList.remove(UNKNOWN);
        return enumList;
    }
}
