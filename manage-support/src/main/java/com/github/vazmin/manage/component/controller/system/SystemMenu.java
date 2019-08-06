package com.github.vazmin.manage.component.controller.system;


import com.github.vazmin.framework.web.support.annotation.Menu;

/**
 * 管理菜单类，该类仅用于构造管理系统菜单导航结构，由系统自动扫描 @Menu 注解，
 * 获取相应的 “包路径”
 */
@Menu(value = "系统", order = 100, icon = "icon ion-md-cog")
public class SystemMenu {
}