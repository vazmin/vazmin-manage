package com.github.vazmin.manage.simple.web;

import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.web.support.annotation.Command;
import com.github.vazmin.framework.web.support.annotation.Module;
import com.github.vazmin.framework.web.support.model.MenuInfo;
import com.github.vazmin.manage.component.service.system.MenuInfoService;
import com.github.vazmin.manage.simple.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Chwing on 2019/8/7.
 */
@Module(value = "FooModule", order = 1, icon = "icon ion-md-color-palette",
        allowAccessAuthenticated = true)
@RestController
@RequestMapping("/api/foo")
public class FooController {

    private final MenuInfoService menuInfoService;

    private final FooService fooService;

    @Autowired
    public FooController(MenuInfoService menuInfoService, FooService fooService) {
        this.menuInfoService = menuInfoService;
        this.fooService = fooService;
    }


    @Command("command")
    @GetMapping("/")
    public List<MenuInfo> foo() throws ServiceProcessException {
        return menuInfoService.getList();
    }


}
