package com.github.vazmin.manage.component.service.users;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.core.util.DateUtil;
import com.github.vazmin.framework.core.util.tools.LongTools;
import com.github.vazmin.manage.component.dao.users.GroupInfoMapper;
import com.github.vazmin.manage.component.model.users.GroupInfo;
import com.github.vazmin.manage.component.model.users.GroupUser;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* 业务组信息 业务处理类
*
 *
*/
@Service
public class GroupInfoService
        extends LongPKBaseService<GroupInfo> {
    private static final Logger log = LoggerFactory.getLogger(GroupInfoService.class);

    @Autowired
    private GroupInfoMapper groupInfoMapper;

    @Autowired
    private GroupUserService groupUserService;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<GroupInfo, Long> getMapper() {
        return groupInfoMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return GroupInfo 业务组信息对象
    */
    public GroupInfo getSafety(Long id) {
        GroupInfo groupInfo = get(id);
        if (groupInfo == null) {
            groupInfo = new GroupInfo();
        }
        return groupInfo;
    }

    /**
     * 获取所有记录列表
     * @return List<GroupInfo> 记录列表
     */
    public List<GroupInfo> getListLeftJoin() {
        return groupInfoMapper.getListLeftJoin();
    }

    /**
     * 获取符合条件的所有记录
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<GroupInfo> 符合条件的记录列表
     */
    public List<GroupInfo> getListLeftJoin(Map<String, Object> conditions) {
        return groupInfoMapper.getListLeftJoin(conditions);
    }

    /**
     * 分页查询记录列表
     * @param pagination Pagination 分页对象
     * @return List<GroupInfo> 记录列表
     */
    public List<GroupInfo> getListLeftJoin(Pagination pagination) {
        return getListLeftJoin(pagination, null);
    }

    /**
     * 按条件分页查询记录列表
     * @param pagination Pagination 分页对象
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<GroupInfo> 符合条件的记录列表
     */
    public List<GroupInfo> getListLeftJoin(Pagination pagination, Map<String, Object> conditions) {
        pagination.setTotalCount(getCount(conditions));
        if (pagination.getTotalCount() <= 0) {
            return new ArrayList<>();
        }
        RowBounds rowBounds =
                new RowBounds(pagination.getOffset(), pagination.getPageSize());
        return groupInfoMapper.getListLeftJoin(rowBounds, conditions);
    }

    /**
     * 根据部门id获取业务组了课表
     * @param departmentId Long 部门id
     * @return List<GroupInfo> 业务组列表
     */
    public List<GroupInfo> getListByDepartmentId(Long departmentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("departmentId", departmentId);
        return getListLeftJoin(params);
    }

    public int save(GroupInfo groupInfo) throws ServiceProcessException {
        if (LongTools.lessEqualZero(groupInfo.getId())) {
            groupInfo.setCreateTime(DateUtil.getTimestamp());
            return insert(groupInfo);
        } else {
            return update(groupInfo);
        }
    }

    /**
     * 批量删除指定部门id下的业务组
     * @param departmentId Long 部门id
     * @param idList List<Long> 记录id列表
     * @return int 删除记录数
     */
    public int batchDelete(Long departmentId, List<Long> idList)
            throws ServiceProcessException {
        int res = 0;
        for (Long id: idList) {
            GroupInfo groupInfo = get(id);
            if (groupInfo != null
                    && Objects.equals(groupInfo.getDepartmentId(), departmentId)) {
                List<GroupUser> groupUserList = groupUserService.getListByGroupId(id);
                if (groupUserList != null && groupUserList.size() > 0) {
                    throw new ServiceProcessException(
                            "groupId",
                            "invalid.groupId",
                            "删除业务组前，必须移除成员，业务组id：" + id);
                }
                res += delete(id);
            }
        }
        return res;
    }
}