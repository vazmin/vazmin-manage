package com.github.vazmin.manage.component.dao.users;

import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.component.model.users.UserPrivilege;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 平台用户权限 MyBatis 映射接口类
 *
 */
@Mapper
public interface UserPrivilegeMapper extends LongPKBaseMapper<UserPrivilege> {
    /**
     * 根据用户id获取用户权限列表
     * @param userId Long 用户id
     * @return List<UserPrivilege> 用户权限列表
     */
    List<UserPrivilege> getListByUserId(Long userId);

    List<UserPrivilege> getListLeftJoin(Map<String, Object> conditions);
}
