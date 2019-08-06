package com.github.vazmin.manage.component.vm;

import javax.validation.constraints.NotNull;

/**
 * Created by Chwing on 2018/10/12.
 */
public class UserPrivilegeVM {

    @NotNull
    private Long id;

    private String privilege;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
