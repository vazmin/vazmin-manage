package com.github.vazmin.manage.component.vm;

import javax.validation.constraints.NotNull;

/**
 * View Model object for storing the user's key and password.
 */
public class KeyAndPasswordVM {

    @NotNull
    private String key;

    @NotNull
    private String newPassword;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
