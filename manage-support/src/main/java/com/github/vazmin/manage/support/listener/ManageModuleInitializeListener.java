package com.github.vazmin.manage.support.listener;

import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.web.support.listener.AbstractModuleInitializeListener;
import com.github.vazmin.framework.web.support.model.CommandInfo;
import com.github.vazmin.framework.web.support.model.MenuInfo;
import com.github.vazmin.framework.web.support.model.ModuleInfo;
import com.github.vazmin.manage.component.service.system.CommandInfoService;
import com.github.vazmin.manage.component.service.system.MenuInfoService;
import com.github.vazmin.manage.component.service.system.ModuleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 管理模块初始化器
 * 系统启动时，获取所有带 @Menu、@Module、@Command 注解的bean，
 * 构造管理模块的栏目、模块、命令对象，同时注册到相应信息表，并生成树形结构，以用于模块显示、权限管理使用。
 * 相关注解：
 *      <code>@Menu</code>
 *      <code>@Module</code>
 *      <code>@Command</code>
 *      <code>@RequestMapping</code>
 *
 */
@Component
public class ManageModuleInitializeListener extends AbstractModuleInitializeListener {

    @Autowired
    private MenuInfoService menuInfoService;

    @Autowired
    private ModuleInfoService moduleInfoService;

    @Autowired
    private CommandInfoService commandInfoService;

    @Override
    public Map<String, MenuInfo> getOldMenuMap() {
        return menuInfoService.getMenuMap();
    }

    @Override
    public void saveMenu(MenuInfo menuInfo) throws ServiceProcessException {
        menuInfoService.save(menuInfo);
    }

    @Override
    public void updateDiscard(MenuInfo menuInfo) {
        menuInfoService.updateDiscard(menuInfo);
    }

    @Override
    public Map<String, ModuleInfo> getOldModuleMap() {
        return moduleInfoService.getModuleMap();
    }

    @Override
    public void saveModule(ModuleInfo moduleInfo) throws ServiceProcessException {
        moduleInfoService.save(moduleInfo);
    }

    @Override
    public void updateModule(ModuleInfo moduleInfo) throws ServiceProcessException {
        moduleInfoService.update(moduleInfo);
    }

    @Override
    public void updateDiscard(ModuleInfo moduleInfo) {
        moduleInfoService.updateDiscard(moduleInfo);
    }

    @Override
    public Map<String, CommandInfo> getOldCommandMap() {
        return commandInfoService.getCommandMap();
    }

    @Override
    public void saveCommand(CommandInfo commandInfo) throws ServiceProcessException {
        commandInfoService.save(commandInfo);
    }

    @Override
    public void updateDiscard(CommandInfo commandInfo) {
        commandInfoService.updateDiscard(commandInfo);
    }
}
