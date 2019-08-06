package com.github.vazmin.manage.component.dao.users;

import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.component.model.users.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 平台用户角色 MyBatis 映射接口类
 *
 */
@Mapper
public interface UserRoleMapper extends LongPKBaseMapper<UserRole> {
    /**
     * 根据用户id获取用户角色列表
     * @param userId Long 用户id
     * @return List<UserRole> 用户角色列表
     */
    List<UserRole> getListByUserId(Long userId);

    /**
     * 根据角色id获取用户角色列表
     * @param roleId Long 角色id
     * @return List<UserRole> 用户角色列表
     */
    List<UserRole> getListByRoleId(Long roleId);
}
