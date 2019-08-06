package com.github.vazmin.manage.component.dao.users;

import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.component.model.users.GroupUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
* 业务组用户关系 MyBatis 映射接口类
*
 *
*/
@Mapper
public interface GroupUserMapper
        extends LongPKBaseMapper<GroupUser> {

    /**
     * 查询业务组用户记录
     * @param id Long 记录id
     * @return GroupUser 记录列表
     */
    GroupUser getLeftJoin(Long id);

    /**
     * 查询所有记录列表
     * @return List<E> 记录列表
     */
    List<GroupUser> getListLeftJoin();

    /**
     * 根据条件查询记录列表
     * @param conditions Map<String, Object> 条件map
     * @return List<GroupUser> 记录列表
     */
    List<GroupUser> getListLeftJoin(Map<String, Object> conditions);

    /**
     * 查询全部记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @return List<GroupUser> 记录列表
     */
    List<GroupUser> getListLeftJoin(RowBounds rowBounds);

    /**
     * 根据条件查询记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @param conditions Map<String, Object> 条件map
     * @return List<GroupUser> 记录列表
     */
    List<GroupUser> getListLeftJoin(RowBounds rowBounds, Map<String, Object> conditions);
}