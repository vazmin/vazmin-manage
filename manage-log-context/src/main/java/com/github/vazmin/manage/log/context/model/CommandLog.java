package com.github.vazmin.manage.log.context.model;

import com.github.vazmin.framework.core.enu.BoolEnum;
import com.github.vazmin.framework.core.util.DateUtil;

import java.io.Serializable;

/**
* 用户操作日志 Bean
*
* Created by zhiming on 10/24/16.
*/
public class CommandLog implements Serializable {

    /** 记录id */
    private Long id;
    /** 用户id */
    private Long userId;
    /** 姓名 */
    private String name;
    /** 命令id */
    private Long commandId;
    /** 命令名称 */
    private String commandName;
    /** 模块名称 */
    private String moduleName;
    /** 请求路径 */
    private String requestPath;
    /** 请求参数 */
    private String parameters;
    /** 用户IP */
    private String userIp;
    /** 操作是否成功,0为失败，1为成功 */
    private Integer resultSuccesss;
    /** 操作提示信息 */
    private String resultMessage;
    /** 操作时间 */
    private Long actionTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCommandId() {
        return commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Integer getResultSuccesss() {
        return resultSuccesss;
    }

    public void setResultSuccesss(Integer resultSuccesss) {
        this.resultSuccesss = resultSuccesss;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Long getActionTime() {
        return actionTime;
    }

    public void setActionTime(Long actionTime) {
        this.actionTime = actionTime;
    }

//    public String getActionTimeFullFormat(){
//        return DateUtil.fullFormat(actionTime);
//    }
//
//    public String getResultStr(){
//        return BoolEnum.getDescription(resultSuccesss);
//    }

}