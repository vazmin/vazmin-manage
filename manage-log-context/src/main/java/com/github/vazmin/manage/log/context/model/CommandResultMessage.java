package com.github.vazmin.manage.log.context.model;

/**
 * Created by Chwing on 2018/10/17.
 */
public class CommandResultMessage {

    public CommandResultMessage(String alert, String params, String error) {
        this.alert = alert;
        this.params = params;
        this.error = error;
    }

    public CommandResultMessage() {
    }

    private String alert;

    private String params;

    private String error;

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
