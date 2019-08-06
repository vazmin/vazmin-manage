package com.github.vazmin.manage.component.enu.system;

/**
 * 列表每页显示条数枚举类
 *
 */
public enum ListLimitEnum {
    FIVE(5, "5"),
    TEN(10, "10"),
    FIFTEEN(15, "15"),
    TWENTY(20, "20"),
    TWENTY_FIVE(25, "25"),
    THIRTY(30, "30"),
    FIFTY(50, "50"),
    HUNDRED(100, "100");

    /** id */
    private Integer id;
    /** 描述 */
    private String description;

    ListLimitEnum(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * 根据id值获取对应的枚举值
     * @param id String id值
     * @return ListLimitEnum 枚举值，如果不存在返回 null
     */
    public static ListLimitEnum valueOf(Integer id) {
        if (id != null) {
            for (ListLimitEnum orderingEnum: values()) {
                if (orderingEnum.getId().intValue() == id.intValue()) {
                    return orderingEnum;
                }
            }
        }
        return TWENTY;
    }

    public Integer getId() {
        return id;
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
        ListLimitEnum orderingEnum = valueOf(id);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }
}
