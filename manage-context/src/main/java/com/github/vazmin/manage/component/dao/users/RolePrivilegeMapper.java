package com.github.vazmin.manage.component.dao.users;

import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.component.model.users.RolePrivilege;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 平台角色权限 MyBatis 映射接口类
 *
 */
@Mapper
public interface RolePrivilegeMapper extends LongPKBaseMapper<RolePrivilege> {
    /**
     * 根据角色id获取角色权限列表
     * @param roleId Long 角色id
     * @return List<RolePrivilege> 角色权限列表
     */
    List<RolePrivilege> getListByRoleId(Long roleId);

    /**
     * 根据角色id列表获取角色权限列表
     * @param roleIdList List<Long> 角色id列表
     * @return List<RolePrivilege> 角色权限列表
     */
    List<RolePrivilege> getListByRoleIdList(List<Long> roleIdList);

    List<RolePrivilege> getListLeftJoin(Map<String, Object> conditions);
}
