package com.github.vazmin.manage.component.service.users;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.core.util.DateUtil;
import com.github.vazmin.framework.core.util.tools.LongTools;
import com.github.vazmin.manage.component.dao.users.DepartmentUserMapper;
import com.github.vazmin.manage.component.model.users.DepartmentUser;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 部门用户关系 业务处理类
*
 *
*/
@Service
public class DepartmentUserService
        extends LongPKBaseService<DepartmentUser> {
    private static final Logger log = LoggerFactory.getLogger(DepartmentUserService.class);

    @Autowired
    private DepartmentUserMapper departmentUserMapper;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<DepartmentUser, Long> getMapper() {
        return departmentUserMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return DepartmentUser 部门用户关系对象
    */
    public DepartmentUser getSafety(Long id) {
        DepartmentUser departmentUser = departmentUserMapper.getLeftJoin(id);
        if (departmentUser == null) {
            departmentUser = new DepartmentUser();
        }
        return departmentUser;
    }

    /**
     * 获取所有记录列表
     * @return List<DepartmentUser> 记录列表
     */
    public List<DepartmentUser> getListLeftJoin() {
        return departmentUserMapper.getListLeftJoin();
    }

    /**
     * 获取符合条件的所有记录
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<DepartmentUser> 符合条件的记录列表
     */
    public List<DepartmentUser> getListLeftJoin(Map<String, Object> conditions) {
        return departmentUserMapper.getListLeftJoin(conditions);
    }

    /**
     * 分页查询记录列表
     * @param pagination Pagination 分页对象
     * @return List<DepartmentUser> 记录列表
     */
    public List<DepartmentUser> getListLeftJoin(Pagination pagination) {
        return getListLeftJoin(pagination, null);
    }

    /**
     * 按条件分页查询记录列表
     * @param pagination Pagination 分页对象
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<DepartmentUser> 符合条件的记录列表
     */
    public List<DepartmentUser> getListLeftJoin(Pagination pagination, Map<String, Object> conditions) {
        pagination.setTotalCount(getCount(conditions));
        if (pagination.getTotalCount() <= 0) {
            return new ArrayList<>();
        }
        RowBounds rowBounds =
                new RowBounds(pagination.getOffset(), pagination.getPageSize());
        return departmentUserMapper.getListLeftJoin(rowBounds, conditions);
    }

    /**
     * 根据部门id和用户id查询关系记录
     * @param departmentId Long 部门id
     * @param userId Long 用户id
     * @return DepartmentUser
     */
    public DepartmentUser getByDepartmentIdAndUserId(Long departmentId, Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("departmentId", departmentId);
        params.put("userId", userId);
        return getByDynamicWhere(params);
    }

    /**
     * 根据部门id获取部门成员列表
     * @param departmentId Long 部门id
     * @return List<DepartmentUser> 部门成员关系列表
     */
    public List<DepartmentUser> getListByDepartmentId(Long departmentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("departmentId", departmentId);
        return getList(params);
    }

    /**
     * 根据用户id获取部门用户关联列表
     * @param userId Long 用户id
     * @return List<DepartmentUser> 部门成员关系列表
     */
    public List<DepartmentUser> getListByUserId(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return getList(params);
    }

    /**
     * 保存部门、用户关系记录
     * @param departmentUser DepartmentUser
     * @return int 受影响记录数
     * @throws ServiceProcessException
     */
    public int save(DepartmentUser departmentUser) throws ServiceProcessException {
        DepartmentUser oldOne = getByDepartmentIdAndUserId(
                departmentUser.getDepartmentId(), departmentUser.getUserId());
        if (LongTools.lessEqualZero(departmentUser.getId())) {
            if (oldOne != null) {
                throw new ServiceProcessException(
                        "userId",
                        "invalid.userId",
                        "您选择用户已加入该部门，不能再次添加");
            }
            departmentUser.setAssignTime(DateUtil.getTimestamp());
            return insert(departmentUser);
        } else {
            if (oldOne != null && oldOne.getId() != departmentUser.getId()) {
                throw new ServiceProcessException(
                        "userId",
                        "invalid.userId",
                        "您选择用户已加入该部门，不能修改");
            }
            return update(departmentUser);
        }
    }

    /**
     * 获取未分组的成员列表
     * @param departmentId Long 部门id
     * @return List<ManageUser> 未分组成员列表
     */
    public List<DepartmentUser> getUngroupList(Long departmentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("departmentId", departmentId);
        return departmentUserMapper.getUngroupList(params);
    }

}