package com.github.vazmin.manage.component.model;

import java.util.List;

/**
 * Ngx Tree Item
 * Created by Chwing on 2018/10/12.
 */
public class NgxTreeItem {

    private String id;

    private String name;
    // 选者状态
    private boolean checked;
    // 是否打开
    private boolean open;
    // 允许注册用户
    private boolean accessReg;

    private List<NgxTreeItem> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<NgxTreeItem> getChildren() {
        return children;
    }

    public void setChildren(List<NgxTreeItem> children) {
        this.children = children;
    }

    public boolean isAccessReg() {
        return accessReg;
    }

    public void setAccessReg(boolean accessReg) {
        this.accessReg = accessReg;
    }
}
