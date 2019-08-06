package com.github.vazmin.manage.component.vm;

import com.github.vazmin.framework.web.support.enu.ItemTypeEnum;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chwing on 2018/3/8.
 */
public class PrivilegeVM {

    public PrivilegeVM() {
    }
    public PrivilegeVM(ItemTypeEnum type, String path) {
        this.type = type.getValue();
        this.path = path;
    }
    public PrivilegeVM(Integer type, String path) {
        this.type = type;
        this.path = path;
    }

    private String path;

    private Integer type;

    private Set<PrivilegeVM> children;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setType(ItemTypeEnum typeEnum){
        this.type = typeEnum.getValue();
    }

    public Set<PrivilegeVM> getChildren() {
        return children;
    }

    public void setChildren(Set<PrivilegeVM> children) {
        this.children = children;
    }

    public void addChildren(PrivilegeVM children){
        if(this.children == null){
            this.children = new HashSet<>();
        }
        this.children.add(children);
    }
}
