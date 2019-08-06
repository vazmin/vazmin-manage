package com.github.vazmin.manage.component.dao.users;


import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.component.model.users.ManageRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * 平台角色 MyBatis 映射接口类
 *
 */
@Mapper
public interface ManageRoleMapper extends LongPKBaseMapper<ManageRole> {
    /**
     * 根据用户id获取角色列表
     * @param userId Long 用户id
     * @return List<ManageRole> 用户设置的角色列表
     */
    List<ManageRole> getListByUserId(Long userId);

    /**
     * 查询所有记录列表
     * @return List<ManageRole> 记录列表
     */
    List<ManageRole> getListLeftJoin();

    /**
     * 根据条件查询记录列表
     * @param conditions Map<String, Object> 条件map
     * @return List<ManageRole> 记录列表
     */
    List<ManageRole> getListLeftJoin(Map<String, Object> conditions);

    /**
     * 查询全部记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @return List<ManageRole> 记录列表
     */
    List<ManageRole> getListLeftJoin(RowBounds rowBounds);

    /**
     * 根据条件查询记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @param conditions Map<String, Object> 条件map
     * @return List<E> 记录列表
     */
    List<ManageRole> getListLeftJoin(RowBounds rowBounds, Map<String, Object> conditions);

    /**
     * 更新用户状态
     * @param conditions Map<String, Object> 条件参数
     * @return int 受影响记录数
     */
    int updateStatus(Map<String, Object> conditions);
}
