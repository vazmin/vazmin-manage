package com.github.vazmin.manage.component.dao.users;

import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.component.model.users.ManageUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * 平台用户 MyBatis 映射接口类
 *
 */
@Mapper
public interface ManageUserMapper extends LongPKBaseMapper<ManageUser> {
    /**
     * 更新最后访问时间
     * @param params Map<String, Object> id、最后访问时间
     */
    void updateLastVisitDate(Map<String, Object> params);

    /**
     * 根据业务组id获取成员列表
     * @param groupId Long 业务组id
     * @return List<ManageUser> 成员列表
     */
    List<ManageUser> getListByGroupId(Long groupId);

    /**
     * 获取未分组的成员列表
     * @return List<ManageUser> 成员列表
     */
    List<ManageUser> getUngroupList();

    /**
     * 更新openid
     * @param params Map<String, Object>
     * @return int
     */
    int updateOpenid(Map<String, Object> params);

    /**
     * 移除openid
     * @param params Map<String, Object>
     * @return int
     */
    int removeOpenid(Map<String, Object> params);

    /**
     * 根据条件查询未加入指定部门的总记录数
     * @param conditions Map<String, Object> 条件map
     * @return int 匹配记录数
     */
    int getCountExcludeDepartment(Map<String, Object> conditions);

    /**
     * 根据条件查询未加入指定业务组的总记录数
     * @param conditions Map<String, Object> 条件map
     * @return int 匹配记录数
     */
    int getCountExcludeGroup(Map<String, Object> conditions);

    /**
     * 根据条件查询未加入指定部门的记录列表
     * @param conditions Map<String, Object> 条件map
     * @return List<ManageUser> 记录列表
     */
    List<ManageUser> getListExcludeDepartment(Map<String, Object> conditions);

    /**
     * 根据条件查询未加入指定部门的记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @param conditions Map<String, Object> 条件map
     * @return List<ManageUser> 记录列表
     */
    List<ManageUser> getListExcludeDepartment(RowBounds rowBounds, Map<String, Object> conditions);

    /**
     * 根据条件查询未加入指定业务组的记录列表
     * @param conditions Map<String, Object> 条件map
     * @return List<ManageUser> 记录列表
     */
    List<ManageUser> getListExcludeGroup(Map<String, Object> conditions);

    /**
     * 根据条件查询未加入指定业务组的记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @param conditions Map<String, Object> 条件map
     * @return List<ManageUser> 记录列表
     */
    List<ManageUser> getListExcludeGroup(RowBounds rowBounds, Map<String, Object> conditions);

    /**
     * 更新用户状态
     * @param conditions Map<String, Object> 条件参数
     * @return int 受影响记录数
     */
    int updateStatus(Map<String, Object> conditions);

    /**
     * 获取列表，仅包含id,和名称
     * @return
     */
    List<ManageUser> getListOpen();
    List<ManageUser> getListOpen(RowBounds rowBounds);
    List<ManageUser> getListOpen(RowBounds rowBounds, Map<String, Object> conditions);
}
