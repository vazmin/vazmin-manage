package com.github.vazmin.manage.component.service.users;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.manage.component.dao.users.UserRoleMapper;
import com.github.vazmin.manage.component.model.users.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 平台用户角色业务处理类
 *
 */
@Service
public class UserRoleService extends LongPKBaseService<UserRole> {
    private static final Logger log = LoggerFactory.getLogger(UserRoleService.class);

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 获取数据层mapper接口对象，子类必须实现该方法
     *
     * @return GenericMapper<E, PK> 数据层mapper接口对象
     */
    @Override
    protected GenericMapper<UserRole, Long> getMapper() {
        return userRoleMapper;
    }

    /**
     * 根据用户id获取用户角色列表
     * @param userId Long 用户id
     * @return List<UserRole> 用户、角色关联记录列表
     */
    public List<UserRole> getListByUserId(Long userId) {
        return userRoleMapper.getListByUserId(userId);
    }

    /**
     * 根据角色id获取用户角色列表
     * @param roleId Long 角色id
     * @return List<UserRole> 用户角色列表
     */
    List<UserRole> getListByRoleId(Long roleId) {
        return userRoleMapper.getListByRoleId(roleId);
    }

    /**
     * 根据角色id列表获取用户角色列表
     * @param roleIdSet Long 角色id列表
     * @return List<UserRole> 用户角色列表
     */
    public List<UserRole> getListByRoleIdSet(Set<Long> roleIdSet) {
        if (roleIdSet == null || roleIdSet.isEmpty()) {
            return null;
        }
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("roleIdSet", roleIdSet);
        return getList(conditions);
    }

    /**
     * 根据角色集合获取用户id列表
     * @param roleIdSet 角色id列表
     * @return 用户id列表
     */
    public Set<Long> getUserIdSet(Set<Long> roleIdSet) {
        List<UserRole> userRoleList = getListByRoleIdSet(roleIdSet);
        if (userRoleList == null) {
            return new HashSet<>();
        }
        return userRoleList.stream().map(UserRole::getUserId).collect(Collectors.toSet());
    }

    /**
     * 保存用户角色关系
     * @param userId Long 用户id
     * @param roleIdSet Set<Long> 角色id集合
     * @return int 保存角色数
     */
    public int save(Long userId, Set<Long> roleIdSet) throws ServiceProcessException {
        int res = 0;
        if (userId == null || roleIdSet == null) {
            return res;
        }
        List<UserRole> oldRoles = getListByUserId(userId);
        Map<Long, UserRole> oldRolesMap = new HashMap<>();
        for (UserRole userRole: oldRoles) {
            oldRolesMap.put(userRole.getRoleId(), userRole);
        }
        for (Long roleId: roleIdSet) {
            if (oldRolesMap.containsKey(roleId)) {
                oldRolesMap.remove(roleId);
            } else {
                UserRole newUserRole = new UserRole();
                newUserRole.setUserId(userId);
                newUserRole.setRoleId(roleId);
                res += insert(newUserRole);
            }
        }
        //删掉旧的角色
        for (UserRole oldOne: oldRolesMap.values()) {
            delete(oldOne.getId());
        }
        return res;
    }
}
