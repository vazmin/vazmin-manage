package com.github.vazmin.manage.component.enu.system;

import org.apache.commons.lang3.StringUtils;

/**
 * 页面操作任务类型枚举类
 *
 */
public enum TaskEnum {
    APPLY(0, "apply", "JTOOLBAR_APPLY", "保存"),
    SAVE(1, "save", "JTOOLBAR_SAVE", "保存 & 关闭"),
    SAVE2NEW(2, "save2new", "JTOOLBAR_SAVE_AND_NEW", "保存 & 新增"),
    COPY(3, "copy", "JTOOLBAR_SAVE_AS_COPY", "保存为拷贝"),
    CANCEL(4, "cancel", "JTOOLBAR_CANCEL", "取消"),
    SAVE2PRE(5, "save2pre", "JTOOLBAR_SAVE_AND_PRE", "保存 & 上一步"),
    SAVE2NEXT(6, "save2next", "JTOOLBAR_SAVE_AND_NEXT", "保存 & 下一步"),
    PRE(7, "pre", "JTOOLBAR_PRE", "上一步"),
    NEXT(8, "next", "JTOOLBAR_NEXT", "下一步"),
    PUBLISH(9,"publish","JTOOLBAR_PUBLISH","发布"),
    REFRESH(10,"refresh","JTOOLBAR_REFRESH_CACHE","刷新"),
    BLOCK(11, "block", "USERS_TOOLBAR_BLOCK", "锁定"),
    UNBLOCK(12, "unblock", "USERS_TOOLBAR_UNBLOCK", "解锁"),
    UNKNOWN(-1, "unknown", "JTOOLBAR_UNKNOWN", "未知");

    /** id */
    private Integer id;
    /** 标识 */
    private String code;
    /** 本地化标识 */
    private String textCode;
    /** 描述 */
    private String description;

    private TaskEnum(Integer id, String code, String textCode, String description) {
        this.id = id;
        this.code = code;
        this.textCode = textCode;
        this.description = description;
    }

    /**
     * 根据id值获取对应的枚举值
     * @param id String id值
     * @return TaskEnum 枚举值，如果不存在返回 UNKNOWN
     */
    public static TaskEnum valueOf(Integer id) {
        if (id != null) {
            for (TaskEnum taskEnum: values()) {
                if (taskEnum.getValue().equals(id)) {
                    return taskEnum;
                }
            }
        }
        return UNKNOWN;
    }

    /**
     * 根据code值获取对应的枚举值
     * @param code String code值
     * @return TaskEnum 枚举值，如果不存在返回 UNKNOWN
     */
    public static TaskEnum valueOfCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (TaskEnum taskEnum: values()) {
                if (taskEnum.getCode().equals(code)) {
                    return taskEnum;
                }
            }
        }
        return UNKNOWN;
    }

    public Integer getValue() {
        return id;
    }

    public String getCode() {
        return code;
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
     * @return String 对应枚举的描述，如果不是有效枚举返回“未知”
     */
    public static String getDescription(Integer id){
        return valueOf(id).getDescription();
    }
}
