package com.github.vazmin.manage.component.controller;

/**
 * 平台系统控制器接口(带授权路径)
 * 主要定义一些基本的公共常量
 *
 */
public interface ManageAuthorizeControllerInterface {
    /** 模块授权列表路径 */
    String AUTHORIZE_URL = "/authorize";
    /** 保存模块授权路径 */
    String SAVE_AUTHORIZE_URL = "/savePrivilege";
}
