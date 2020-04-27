package com.github.vazmin.manage.simple.mat.web;

import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.web.support.annotation.Command;
import com.github.vazmin.framework.web.support.annotation.Module;
import com.github.vazmin.framework.web.support.model.CommandInfo;
import com.github.vazmin.framework.web.support.model.MenuInfo;
import com.github.vazmin.manage.component.service.system.CommandInfoService;
import com.github.vazmin.manage.component.service.system.MenuInfoService;
import com.github.vazmin.manage.simple.mat.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Chwing on 2019/8/7.
 */
@Module(value = "FooModule", order = 1, icon = "icon ion-md-color-palette",
        common = true)
@RestController
@RequestMapping("/api/foo")
public class FooController {

    @Autowired
    private  CommandInfoService commandInfoService;

    @Autowired
    private  FooService fooService;


    @Command("command")
    @GetMapping("/")
    public List<CommandInfo> foo() throws ServiceProcessException {
        return commandInfoService.getList();
    }

    @Command("foo detail")
    @GetMapping("/{id}")
    public CommandInfo foo(@PathVariable Long id) throws ServiceProcessException {
        return commandInfoService.get(id);
    }
}
