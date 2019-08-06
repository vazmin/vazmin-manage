package com.github.vazmin.manage.log.context.enu.options;

import org.apache.commons.lang3.StringUtils;

/**
* 通知类型 列表排序枚举类
*/
public enum NoticeTypeOrderingEnum {
    ID_ASC(1, "a.id ASC", "noticeType.id.asc", "记录id 升序"),
    ID_DESC(2, "a.id DESC", "noticeType.id.desc", "记录id 降序"),
    CODE_ASC(3, "a.code ASC", "noticeType.code.asc", "类型编码 升序"),
    CODE_DESC(4, "a.code DESC", "noticeType.code.desc", "类型编码 降序"),
    DESCRIPTION_ASC(5, "a.description ASC", "noticeType.description.asc", "类型描述 升序"),
    DESCRIPTION_DESC(6, "a.description DESC", "noticeType.description.desc", "类型描述 降序");

    /** id */
    private Integer id;
    /** option选项值 */
    private String value;
    /** 本地化标识 */
    private String textCode;
    /** 描述 */
    private String description;

    NoticeTypeOrderingEnum(
            Integer id, String value, String textCode, String description) {
        this.id = id;
        this.value = value;
        this.textCode = textCode;
        this.description = description;
    }

    /**
    * 根据id值获取对应的枚举值
    * @param id String id值
    * @return NoticeTypeOrderingEnum 枚举值，如果不存在返回 null
    */
    public static NoticeTypeOrderingEnum valueOf(Integer id) {
        if (id != null) {
            for (NoticeTypeOrderingEnum orderingEnum: values()) {
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
    * @return NoticeTypeOrderingEnum 排序枚举值，如果不存在，返回 null
    */
    public static NoticeTypeOrderingEnum enumOfValue(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (NoticeTypeOrderingEnum orderingEnum: values()) {
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
        NoticeTypeOrderingEnum orderingEnum = valueOf(id);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }

    /**
     * 根据value 直接获取描述信息
     * @param value String 值字符串
     * @return String 对应枚举的描述，如果不是有效枚举返回null
     */
    public static String getDescriptionOfValue(String value) {
        NoticeTypeOrderingEnum orderingEnum = enumOfValue(value);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }

    /**
     * 返回默认枚举值
     * @return NoticeTypeOrderingEnum 默认枚举值
     */
    public static NoticeTypeOrderingEnum getDefaultEnum() {
        return CODE_ASC;
    }
}