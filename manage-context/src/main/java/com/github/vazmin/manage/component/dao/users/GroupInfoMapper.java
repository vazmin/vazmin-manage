package com.github.vazmin.manage.component.dao.users;

import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.component.model.users.GroupInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
* 业务组信息 MyBatis 映射接口类
*
 *
*/
@Mapper
public interface GroupInfoMapper
        extends LongPKBaseMapper<GroupInfo> {
    /**
     * 查询所有记录列表
     * @return List<E> 记录列表
     */
    List<GroupInfo> getListLeftJoin();

    /**
     * 根据条件查询记录列表
     * @param conditions Map<String, Object> 条件map
     * @return List<GroupInfo> 记录列表
     */
    List<GroupInfo> getListLeftJoin(Map<String, Object> conditions);

    /**
     * 查询全部记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @return List<GroupInfo> 记录列表
     */
    List<GroupInfo> getListLeftJoin(RowBounds rowBounds);

    /**
     * 根据条件查询记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @param conditions Map<String, Object> 条件map
     * @return List<GroupInfo> 记录列表
     */
    List<GroupInfo> getListLeftJoin(RowBounds rowBounds, Map<String, Object> conditions);
}