package com.github.vazmin.manage.component.vm;


import com.github.vazmin.manage.component.model.users.ManageUser;

import java.util.Set;

/**
 * Created by Chwing on 2018/3/14.
 */
public class PrincipalVM {

    private Set<MenuVM> userMenu;

    private ManageUser user;

    public PrincipalVM(Set<MenuVM> userMenu, ManageUser user) {
        this.userMenu = userMenu;
        this.user = user;
//        this.user.setPassword(null);
    }

    public PrincipalVM() {
    }

    public Set<MenuVM> getUserMenu() {
        return userMenu;
    }

    public void setUserMenu(Set<MenuVM> userMenu) {
        this.userMenu = userMenu;
    }

    public ManageUser getUser() {
        return user;
    }

    public void setUser(ManageUser user) {
        this.user = user;
    }
}
