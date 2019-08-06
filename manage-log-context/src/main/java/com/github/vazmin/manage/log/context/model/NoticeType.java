package com.github.vazmin.manage.log.context.model;

import java.io.Serializable;

/**
* 通知类型 Bean
*
*/
public class NoticeType implements Serializable {

    /** 记录id */
    private Long id;
    /** 类型编码 */
    private String code;
    /** 类型描述 */
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}