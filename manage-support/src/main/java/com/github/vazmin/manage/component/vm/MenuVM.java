package com.github.vazmin.manage.component.vm;

import com.github.vazmin.framework.web.support.enu.ItemTypeEnum;
import com.github.vazmin.framework.web.support.model.BaseInfo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Chwing on 2018/3/8.
 */
public class MenuVM {

    public MenuVM() {
    }
    public MenuVM(ItemTypeEnum type,String title, String url) {
        this.type = type.getValue();
        this.title = title;
        this.url = url;
    }

    public MenuVM(ItemTypeEnum type, BaseInfo baseInfo) {
        this.type = type.getValue();
        this.title = baseInfo.getValue();
        this.url = baseInfo.getPath();
        this.order = baseInfo.getOrderNumber();
    }

    public MenuVM(ItemTypeEnum type, BaseInfo baseInfo, boolean hidden) {
        this.type = type.getValue();
        this.title = baseInfo.getValue();
        this.url = baseInfo.getPath();
        this.order = baseInfo.getOrderNumber();
        this.hidden = hidden;
    }

    public MenuVM(Integer type, String url) {
        this.type = type;
        this.url = url;
    }

    private String title;

    private String url;

    private Integer type;

    private String icon;

    private Integer order;

    private boolean hidden;

    private String method;

    private Set<MenuVM> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
//        if(url == null && children != null
//                && ItemTypeEnum.MENU == ItemTypeEnum.valueOf(type)){
//            children.stream()
//                    .findFirst()
//                    .ifPresent(c ->
//                            url = c.url.substring(0, c.url.lastIndexOf("/")));
//        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Set<MenuVM> getChildren() {
        return children;
    }

    public void setChildren(Set<MenuVM> children) {
        this.children = children;
    }

    public void addChildren(MenuVM children){
        addChildren(children, true);
    }

    public void addChildren(MenuVM children, boolean order){
        if(this.children == null){
            this.children = order ? new TreeSet<>(Comparator.comparing(MenuVM::getOrder)) : new HashSet<>();
        }
        this.children.add(children);
    }

    public void addChildren(Set<MenuVM> menuVMSet){
        if (this.getChildren() == null) {
            this.children = menuVMSet;
        } else {
            this.children.addAll(menuVMSet);
        }
    }
}
