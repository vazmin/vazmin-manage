package com.github.vazmin.manage.component.service.users;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.core.util.DateUtil;
import com.github.vazmin.framework.core.util.tools.LongTools;
import com.github.vazmin.manage.component.dao.users.GroupUserMapper;
import com.github.vazmin.manage.component.model.users.GroupUser;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* 业务组用户关系 业务处理类
*
 *
*/
@Service
public class GroupUserService
        extends LongPKBaseService<GroupUser> {
    private static final Logger log = LoggerFactory.getLogger(GroupUserService.class);

    @Autowired
    private GroupUserMapper groupUserMapper;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<GroupUser, Long> getMapper() {
        return groupUserMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return GroupUser 业务组用户关系对象
    */
    public GroupUser getSafety(Long id) {
        GroupUser groupUser = groupUserMapper.getLeftJoin(id);
        if (groupUser == null) {
            groupUser = new GroupUser();
        }
        return groupUser;
    }

    /**
     * 获取所有记录列表
     * @return List<GroupUser> 记录列表
     */
    public List<GroupUser> getListLeftJoin() {
        return groupUserMapper.getListLeftJoin();
    }

    /**
     * 获取符合条件的所有记录
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<GroupUser> 符合条件的记录列表
     */
    public List<GroupUser> getListLeftJoin(Map<String, Object> conditions) {
        return groupUserMapper.getListLeftJoin(conditions);
    }

    /**
     * 分页查询记录列表
     * @param pagination Pagination 分页对象
     * @return List<GroupUser> 记录列表
     */
    public List<GroupUser> getListLeftJoin(Pagination pagination) {
        return getListLeftJoin(pagination, null);
    }

    /**
     * 按条件分页查询记录列表
     * @param pagination Pagination 分页对象
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<GroupUser> 符合条件的记录列表
     */
    public List<GroupUser> getListLeftJoin(
            Pagination pagination, Map<String, Object> conditions) {
        pagination.setTotalCount(getCount(conditions));
        if (pagination.getTotalCount() <= 0) {
            return new ArrayList<>();
        }
        RowBounds rowBounds =
                new RowBounds(pagination.getOffset(), pagination.getPageSize());
        return groupUserMapper.getListLeftJoin(rowBounds, conditions);
    }

    /**
     * 根据业务组id和用户id查询关系记录
     * @param groupId Long 业务组id
     * @param userId Long 用户id
     * @return DepartmentUser
     */
    public GroupUser getByGroupIdAndUserId(Long groupId, Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("userId", userId);
        return getByDynamicWhere(params);
    }

    /**
     * 保存业务组、用户关系记录
     * @param groupUser GroupUser
     * @return int 受影响记录数
     * @throws ServiceProcessException
     */
    public int save(GroupUser groupUser) throws ServiceProcessException {
        GroupUser oldOne = getByGroupIdAndUserId(
                groupUser.getGroupId(), groupUser.getUserId());
        if (LongTools.lessEqualZero(groupUser.getId())) {
            if (oldOne != null) {
                throw new ServiceProcessException(
                        "userId",
                        "invalid.userId",
                        "您选择用户已加入该业务组，不能再次添加");
            }
            groupUser.setAssignTime(DateUtil.getTimestamp());
            return insert(groupUser);
        } else {
            if (oldOne != null && !Objects.equals(oldOne.getId(), groupUser.getId())) {
                throw new ServiceProcessException(
                        "userId",
                        "invalid.userId",
                        "您选择用户已加入该业务组，不能修改");
            }
            return update(groupUser);
        }
    }

    /**
     * 根据业务组id获取用户关系列表
     * @param groupId Long 业务组id
     * @return List<GroupUser> 业务组、用户关系列表
     */
    public List<GroupUser> getListByGroupId(Long groupId) {
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        return groupUserMapper.getList(params);
    }

    /**
     * 根据用户id获取用户关系列表
     * @param userId Long 用户id
     * @return List<GroupUser> 业务组、用户关系列表
     */
    public List<GroupUser> getListByUserId(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return groupUserMapper.getList(params);
    }

    /**
     * 批量删除指定业务组id下的成员
     * @param groupId Long 业务组id
     * @param idList List<Long> 记录id列表
     * @return int 删除记录数
     */
    public int batchDelete(Long groupId, List<Long> idList)
            throws ServiceProcessException {
        int res = 0;
        for (Long id: idList) {
            GroupUser groupUser = get(id);
            if (groupUser != null
                    && Objects.equals(groupUser.getGroupId(), groupId)) {
                res += delete(id);
            }
        }
        return res;
    }
}