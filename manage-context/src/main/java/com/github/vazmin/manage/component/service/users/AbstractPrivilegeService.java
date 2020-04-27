package com.github.vazmin.manage.component.service.users;

import com.github.vazmin.framework.core.enu.StatusEnum;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.web.support.model.CommandInfo;
import com.github.vazmin.framework.web.support.model.MenuInfo;
import com.github.vazmin.framework.web.support.model.ModuleInfo;
import com.github.vazmin.framework.web.support.model.ModuleTree;
import com.github.vazmin.manage.component.enu.system.ItemTypeEnum;
import com.github.vazmin.manage.component.model.NgxTreeItem;
import com.github.vazmin.manage.component.model.ZtreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 平台用户权限业务处理类抽象基类
 *
 */
public abstract class AbstractPrivilegeService<E> extends LongPKBaseService<E> {
    private static final Logger log = LoggerFactory.getLogger(AbstractPrivilegeService.class);

    @Autowired
    protected ModuleTree moduleTree;

    /**
     * 根据id获取权限列表
     * @param id Long 用户或角色id
     * @return List<E> 权限关联列表
     */
    public abstract List<E> getPrivilegeList(Long id);

    /**
     * 根据用户或角色id获取权限树
     * @param userId Long id
     * @return List<ZtreeItem> 权限树,前台显示用
     */
    public List<ZtreeItem> getPrivilegeTree(Long userId) {
        List<E> privilegeList = getPrivilegeList(userId);
        Map<String, E> privilegeMap = buildPrivilegeMap(privilegeList);
        List<ZtreeItem> ztreeItemList = new ArrayList<>();
        ZtreeItem ztreeItem = new ZtreeItem();
        ztreeItem.setId("0");
        ztreeItem.setpId("");
        ztreeItem.setName("所有权限");
        ztreeItem.setOpen(true);
        ztreeItem.setParent(true);
        ztreeItem.setChildCount(moduleTree.getRootMenuList().size()
                + moduleTree.getRootModuleList().size());
        ztreeItem.setItemType(ItemTypeEnum.MENU.getValue());
        if (privilegeList.size() > 0) {
            ztreeItem.setChecked(true);
        }
        ztreeItemList.add(ztreeItem);
        for (MenuInfo menuInfo: moduleTree.getRootMenuList()) {
            buildZtreeItem(menuInfo, privilegeMap, ztreeItemList);
        }
        for (ModuleInfo moduleInfo: moduleTree.getRootModuleList()) {
            buildZtreeItem(moduleInfo, privilegeMap, ztreeItemList);
        }
        return ztreeItemList;
    }

    /**
     * 将用户权限列表构造成以"类型-id"为key的Map
     * @param privilegeList List<UserPrivilege> 权限列表
     * @return Map<String, UserPrivilege> 权限集合
     */
    protected abstract Map<String, E> buildPrivilegeMap(List<E> privilegeList);

    /**
     * 将菜单构造成ZtreeItem
     * @param menuInfo MenuInfo 菜单信息对象
     * @param privilegeMap Map<String, E> 权限map
     * @param ztreeItemList List<ZtreeItem> 权限树列表
     */
    private void buildZtreeItem(
            MenuInfo menuInfo, Map<String, E> privilegeMap,
            List<ZtreeItem> ztreeItemList) {
        ZtreeItem ztreeItem = new ZtreeItem();
        ztreeItem.setId(menuInfo.getMenuKey());
        ztreeItem.setpId(menuInfo.getParentKey());
        ztreeItem.setName(menuInfo.getValue());
        ztreeItem.setOpen(true);
        ztreeItem.setParent(true);
        ztreeItem.setChildCount(menuInfo.getSubMenuList().size() + menuInfo.getModuleList().size());
        ztreeItem.setItemType(ItemTypeEnum.MENU.getValue());
        if (privilegeMap.containsKey(menuInfo.getMenuKey())) {
            ztreeItem.setChecked(true);
        }
        ztreeItemList.add(ztreeItem);
        for (MenuInfo subMenuInfo: menuInfo.getSubMenuList()) {
            buildZtreeItem(subMenuInfo, privilegeMap, ztreeItemList);
        }
        for (ModuleInfo moduleInfo: menuInfo.getModuleList()) {
            buildZtreeItem(moduleInfo, privilegeMap, ztreeItemList);
        }
    }

    /**
     * 将模块构造成ZtreeItem
     * @param moduleInfo ModuleInfo 模块信息对象
     * @param privilegeMap Map<String, E> 权限map
     * @param ztreeItemList List<ZtreeItem> 权限树列表
     */
    private void buildZtreeItem(
            ModuleInfo moduleInfo, Map<String, E> privilegeMap,
            List<ZtreeItem> ztreeItemList) {
        ZtreeItem ztreeItemBean = new ZtreeItem();
        ztreeItemBean.setId(moduleInfo.getModuleKey());
        ztreeItemBean.setpId(moduleInfo.getParentKey());
        ztreeItemBean.setName(moduleInfo.getValue());
        ztreeItemBean.setOpen(true);
        ztreeItemBean.setParent(true);
        ztreeItemBean.setChildCount(moduleInfo.getCommandList().size());
        ztreeItemBean.setItemType(ItemTypeEnum.MODULE.getValue());
        if (privilegeMap.containsKey(moduleInfo.getModuleKey())) {
            ztreeItemBean.setChecked(true);
        }
        ztreeItemList.add(ztreeItemBean);
        for (CommandInfo commandInfo: moduleInfo.getCommandList()) {
            buildZtreeItem(commandInfo, privilegeMap, ztreeItemList);
        }
    }

    /**
     * 将命令构造成ZtreeItem
     * @param commandInfo CommandInfo 命令信息对象
     * @param privilegeMap Map<String, E> 权限map
     * @param ztreeItemList List<ZtreeItem> 权限树列表
     */
    private void buildZtreeItem(
            CommandInfo commandInfo, Map<String, E> privilegeMap,
            List<ZtreeItem> ztreeItemList) {
        ZtreeItem ztreeItemBean = new ZtreeItem();
        ztreeItemBean.setId(commandInfo.getCommandKey());
        ztreeItemBean.setpId(commandInfo.getParentKey());
        ztreeItemBean.setName(commandInfo.getValue());
        ztreeItemBean.setParent(false);
        ztreeItemBean.setItemType(ItemTypeEnum.COMMAND.getValue());
        if (privilegeMap.containsKey(commandInfo.getCommandKey())) {
            ztreeItemBean.setChecked(true);
        }
        ztreeItemList.add(ztreeItemBean);
    }

    public List<NgxTreeItem> getNgxPrivilegeTree(Long userId) {
        List<E> privilegeList = getPrivilegeList(userId);
        Map<String, E> privilegeMap = buildPrivilegeMap(privilegeList);
        List<NgxTreeItem> ngxTreeItemList = new ArrayList<>();

        for (MenuInfo menuInfo: moduleTree.getRootMenuList()) {
            buildNgxTreeItem(menuInfo, privilegeMap, ngxTreeItemList);
        }
        for (ModuleInfo moduleInfo: moduleTree.getRootModuleList()){
            buildNgxTreeItem(moduleInfo, privilegeMap, ngxTreeItemList);
        }
        return ngxTreeItemList;
    }

    /**
     * 生成权限树
     * @param menuInfo
     * @param privilegeMap
     * @param ngxTreeItemList
     */
    private void buildNgxTreeItem(
            MenuInfo menuInfo, Map<String, E> privilegeMap,
            List<NgxTreeItem> ngxTreeItemList) {
        NgxTreeItem ngxTreeItem = new NgxTreeItem();
        ngxTreeItem.setId(menuInfo.getMenuKey());
        ngxTreeItem.setName(menuInfo.getValue());
        ngxTreeItem.setOpen(true);
        ngxTreeItem.setAccessReg(menuInfo.isCommon());
        if (privilegeMap.containsKey(menuInfo.getMenuKey())) {
            ngxTreeItem.setChecked(true);
        }

        List<NgxTreeItem> children = new ArrayList<>();
        ngxTreeItem.setChildren(children);

        ngxTreeItemList.add(ngxTreeItem);

        for(ModuleInfo moduleInfo: menuInfo.getModuleList()){
            buildNgxTreeItem(moduleInfo, privilegeMap, children);
        }
    }

    private void buildNgxTreeItem(
            ModuleInfo moduleInfo, Map<String, E> privilegeMap,
            List<NgxTreeItem> parentChildrenList) {
        NgxTreeItem ngxTreeItem = new NgxTreeItem();
        ngxTreeItem.setId(moduleInfo.getModuleKey());
        ngxTreeItem.setName(moduleInfo.getValue());
        ngxTreeItem.setOpen(true);
        ngxTreeItem.setAccessReg(moduleInfo.isCommon());
        if (privilegeMap.containsKey(moduleInfo.getModuleKey())) {
            ngxTreeItem.setChecked(true);
        }

        List<NgxTreeItem> children = new ArrayList<>();
        ngxTreeItem.setChildren(children);

        parentChildrenList.add(ngxTreeItem);
        boolean accessRegFlag = true;
        for(CommandInfo commandInfo: moduleInfo.getCommandList()){
            buildNgxTreeItem(commandInfo, privilegeMap, children);
            if (!commandInfo.isCommon()) {
                accessRegFlag = false;
            }
        }
        if (!accessRegFlag) {
            ngxTreeItem.setAccessReg(false);
        }
    }

    private void buildNgxTreeItem(
            CommandInfo commandInfo, Map<String, E> privilegeMap,
            List<NgxTreeItem> parentChildrenList) {
        NgxTreeItem ngxTreeItem = new NgxTreeItem();
        ngxTreeItem.setId(commandInfo.getCommandKey());
        ngxTreeItem.setName(commandInfo.getValue());
        ngxTreeItem.setAccessReg(commandInfo.isCommon());
//        ngxTreeItem.setOpen(true);

        if (privilegeMap.containsKey(commandInfo.getCommandKey())) {
            ngxTreeItem.setChecked(true);
        }
        parentChildrenList.add(ngxTreeItem);
    }

    abstract List<E> getListLeftJoin(Map<String, Object> conditions);

    /**
     * 获取用户/角色 权限列表
     * @param itemId
     * @param typeEnum
     * @return
     */
    public List<E> getListByPrivilege(Long itemId, ItemTypeEnum typeEnum) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("itemId", itemId);
        conditions.put("itemType", typeEnum.getValue());
        conditions.put("status", StatusEnum.VALID.getValue());
        return getListLeftJoin(conditions);
    }

    public Set<Long> getRoleOrUserIdSetByItem(Long itemId, ItemTypeEnum typeEnum) {
        List<E> rolePrivilegeList = getListByPrivilege(itemId, typeEnum);
        if (rolePrivilegeList == null) {
            return new HashSet<>();
        }
        return rolePrivilegeList.stream().map(this::getUserOrRoleId).collect(Collectors.toSet());
    }

    abstract Long getUserOrRoleId(E e);
}
