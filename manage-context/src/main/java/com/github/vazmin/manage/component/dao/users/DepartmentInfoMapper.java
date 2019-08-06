package com.github.vazmin.manage.component.dao.users;

import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.component.model.users.DepartmentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
* 部门信息 MyBatis 映射接口类
*
 *
*/
@Mapper
public interface DepartmentInfoMapper
        extends LongPKBaseMapper<DepartmentInfo> {
    /**
     * 查询所有记录列表
     * @return List<E> 记录列表
     */
    List<DepartmentInfo> getListLeftJoin();

    /**
     * 根据条件查询记录列表
     * @param conditions Map<String, Object> 条件map
     * @return List<DepartmentInfo> 记录列表
     */
    List<DepartmentInfo> getListLeftJoin(Map<String, Object> conditions);

    /**
     * 查询全部记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @return List<DepartmentInfo> 记录列表
     */
    List<DepartmentInfo> getListLeftJoin(RowBounds rowBounds);

    /**
     * 根据条件查询记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @param conditions Map<String, Object> 条件map
     * @return List<E> 记录列表
     */
    List<DepartmentInfo> getListLeftJoin(RowBounds rowBounds, Map<String, Object> conditions);
}