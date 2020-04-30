package com.github.vazmin.manage.component.service;

import com.github.vazmin.framework.web.support.enu.ItemTypeEnum;
import com.github.vazmin.framework.web.support.model.*;
import com.github.vazmin.manage.component.vm.MenuVM;
import com.github.vazmin.manage.support.security.PermissionUserDetailsInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 验证 服务； 获取用户权限菜单
 * Created by Chwing on 2018/3/8.
 */
@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private ModuleTree moduleTree;

    /**
     * 获取用户权限列表
     * TODO: 该方法需优化
     * @param manageUserDetails 用户凭证详情
     * @return 凭证视图Set
     */
    public Set<MenuVM> getMenuList (PermissionUserDetailsInterface manageUserDetails){
        Set<MenuVM> menuVMSet = new TreeSet<>(
                Comparator.comparing(MenuVM::getOrder).thenComparing(MenuVM::getTitle));
        if (manageUserDetails != null) {
            List<MenuInfo> menuInfoList = moduleTree.getRootMenuList();
            for(MenuInfo menuInfo : menuInfoList){
                if(!menuInfo.isDiscard() && manageUserDetails.hasPermission(menuInfo)) {
                    menuVMSet.add(parseMenuVMByMenuInfo(menuInfo, manageUserDetails));
                }
            }
            menuVMSet.addAll(
                    parseMenuVMByModuleInfoList(manageUserDetails, moduleTree.getRootModuleList()));
        }
        return menuVMSet;
    }

    /**
     * 处理权限菜单
     * @param menuInfo MenuInfo
     * @param manageUserDetails 用户信息
     * @return MenuVM
     */
    private MenuVM parseMenuVMByMenuInfo(MenuInfo menuInfo, PermissionUserDetailsInterface manageUserDetails){
        MenuVM menuVM =
                new MenuVM(ItemTypeEnum.MENU, menuInfo);
        menuVM.setIcon(menuInfo.getIcon());
        if (!menuInfo.getSubMenuList().isEmpty()) {
            for (MenuInfo subMenu: menuInfo.getSubMenuList()) {
                menuVM.addChildren(parseMenuVMByMenuInfo(subMenu, manageUserDetails));
            }
        }
        menuVM.addChildren(
                parseMenuVMByModuleInfoList(manageUserDetails, menuInfo.getModuleList()));
        return menuVM;
    }

    private Set<MenuVM> parseMenuVMByModuleInfoList(
            PermissionUserDetailsInterface manageUserDetails,
            List<ModuleInfo> moduleInfoList) {
        Set<MenuVM> menuVMSet =  new TreeSet<>(
                Comparator.comparing(MenuVM::getOrder).thenComparing(MenuVM::getTitle));
        for (ModuleInfo moduleInfo : moduleInfoList) {
            if(!moduleInfo.isDiscard() && manageUserDetails.hasPermission(moduleInfo)) {
                MenuVM moduleMenuVM =
                        new MenuVM(ItemTypeEnum.MODULE, moduleInfo, moduleInfo.isHideInMenu());
                moduleMenuVM.setIcon(moduleInfo.getIcon());
                menuVMSet.add(moduleMenuVM);
                List<CommandInfo> commandInfoList = moduleInfo.getCommandList();
                for (CommandInfo commandInfo : commandInfoList) {
                    if(!commandInfo.isDiscard() && manageUserDetails.hasPermission(commandInfo)) {
                        MenuVM commandMenuVM =
                                new MenuVM(ItemTypeEnum.COMMAND, commandInfo);
                        commandMenuVM.setHidden(!commandInfo.isShowInMenu());
                        commandMenuVM.setMethod(commandInfo.getMethod());
                        moduleMenuVM.addChildren(commandMenuVM, false);
                    }
                }
            }
        }
        return menuVMSet;
    }
}
