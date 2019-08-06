package com.github.vazmin.manage.component.dao.users;

import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.component.model.users.DepartmentUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
* 部门用户关系 MyBatis 映射接口类
*
 *
*/
@Mapper
public interface DepartmentUserMapper
        extends LongPKBaseMapper<DepartmentUser> {

    /**
     * 查询部门用户记录
     * @param id Long 记录id
     * @return DepartmentUser 记录列表
     */
    DepartmentUser getLeftJoin(Long id);

    /**
     * 查询所有记录列表
     * @return List<E> 记录列表
     */
    List<DepartmentUser> getListLeftJoin();

    /**
     * 根据条件查询记录列表
     * @param conditions Map<String, Object> 条件map
     * @return List<DepartmentUser> 记录列表
     */
    List<DepartmentUser> getListLeftJoin(Map<String, Object> conditions);

    /**
     * 查询全部记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @return List<DepartmentUser> 记录列表
     */
    List<DepartmentUser> getListLeftJoin(RowBounds rowBounds);

    /**
     * 根据条件查询记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @param conditions Map<String, Object> 条件map
     * @return List<DepartmentUser> 记录列表
     */
    List<DepartmentUser> getListLeftJoin(RowBounds rowBounds, Map<String, Object> conditions);

    /**
     * 根据部门id查询未分组记录列表
     * @param conditions Map<String, Object> 条件map
     * @return List<DepartmentUser> 记录列表
     */
    List<DepartmentUser> getUngroupList(Map<String, Object> conditions);
}