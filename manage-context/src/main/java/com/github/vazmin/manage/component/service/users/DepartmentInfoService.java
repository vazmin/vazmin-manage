package com.github.vazmin.manage.component.service.users;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.core.util.DateUtil;
import com.github.vazmin.framework.core.util.tools.LongTools;
import com.github.vazmin.manage.component.dao.users.DepartmentInfoMapper;
import com.github.vazmin.manage.component.model.users.DepartmentInfo;
import com.github.vazmin.manage.component.model.users.DepartmentUser;
import com.github.vazmin.manage.component.model.users.GroupInfo;
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
* 部门信息 业务处理类
*
 *
*/
@Service
public class DepartmentInfoService
        extends LongPKBaseService<DepartmentInfo> {
    private static final Logger log = LoggerFactory.getLogger(DepartmentInfoService.class);

    @Autowired
    private DepartmentInfoMapper departmentInfoMapper;

    @Autowired
    private DepartmentUserService departmentUserService;

    @Autowired
    private GroupInfoService groupInfoService;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<DepartmentInfo, Long> getMapper() {
        return departmentInfoMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return DepartmentInfo 部门信息对象
    */
    public DepartmentInfo getSafety(Long id) {
        DepartmentInfo departmentInfo = get(id);
        if (departmentInfo == null) {
            departmentInfo = new DepartmentInfo();
        }
        return departmentInfo;
    }

    /**
     * 获取所有记录列表
     * @return List<DepartmentInfo> 记录列表
     */
    public List<DepartmentInfo> getListLeftJoin() {
        return departmentInfoMapper.getListLeftJoin();
    }

    /**
     * 获取符合条件的所有记录
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<DepartmentInfo> 符合条件的记录列表
     */
    public List<DepartmentInfo> getListLeftJoin(Map<String, Object> conditions) {
        return departmentInfoMapper.getListLeftJoin(conditions);
    }

    /**
     * 分页查询记录列表
     * @param pagination Pagination 分页对象
     * @return List<DepartmentInfo> 记录列表
     */
    public List<DepartmentInfo> getListLeftJoin(Pagination pagination) {
        return getListLeftJoin(pagination, null);
    }

    /**
     * 按条件分页查询记录列表
     * @param pagination Pagination 分页对象
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<DepartmentInfo> 符合条件的记录列表
     */
    public List<DepartmentInfo> getListLeftJoin(Pagination pagination, Map<String, Object> conditions) {
        pagination.setTotalCount(getCount(conditions));
        if (pagination.getTotalCount() <= 0) {
            return new ArrayList<>();
        }
        RowBounds rowBounds =
                new RowBounds(pagination.getOffset(), pagination.getPageSize());
        return departmentInfoMapper.getListLeftJoin(rowBounds, conditions);
    }

    /**
     * 根据部门名称查询部门信息
     * @param departmentName String 部门名称
     * @return DepartmentInfo 部门信息对象
     */
    public DepartmentInfo getByName(String departmentName) {
        Map<String, Object> params = new HashMap<>();
        params.put("departmentName", departmentName);
        return getByDynamicWhere(params);
    }
    /**
     * 保存部门信息
     * @param departmentInfo DepartmentInfo 部门信息对象
     * @return int 受影响记录数
     * @throws ServiceProcessException
     */
    public int save(DepartmentInfo departmentInfo) throws ServiceProcessException {
        DepartmentInfo oldOne = getByName(departmentInfo.getDepartmentName());
        if (LongTools.lessEqualZero(departmentInfo.getId())) {
            if (oldOne != null) {
                throw new ServiceProcessException(
                        "departmentName",
                        "invalid.departmentName",
                        "您输入的部门名称已存在，不能再次添加");
            }
            departmentInfo.setCreateTime(DateUtil.getTimestamp());
            return insert(departmentInfo);
        } else {
            if (oldOne != null && oldOne.getId() != departmentInfo.getId()) {
                throw new ServiceProcessException(
                        "departmentName",
                        "invalid.departmentName",
                        "您输入的部门名称已存在，不能修改");
            }
            return update(departmentInfo);
        }
    }

    /**
     * 重写父类方法，用于判断部门内是否有业务组和成员
     * @param idList List<Long> 要删除的部门id列表
     * @return int 受影响记录数
     * @throws ServiceProcessException
     */
    public int batchDelete(List<Long> idList) throws ServiceProcessException {
        int res = 0;
        for (Long id: idList) {
            List<GroupInfo> groupInfoList = groupInfoService.getListByDepartmentId(id);
            if (groupInfoList != null && groupInfoList.size() > 0) {
                throw new ServiceProcessException(
                        "id",
                        "invalid.id",
                        "删除部门前，必须移除该部门的业务组，部门id：" + id);
            }
            List<DepartmentUser> departmentUserList =
                    departmentUserService.getListByDepartmentId(id);
            if (departmentUserList != null && departmentUserList.size() > 0) {
                throw new ServiceProcessException(
                        "id",
                        "invalid.id",
                        "删除部门前，必须移除该部门的成员，部门id：" + id);
            }
            res += delete(id);
        }
        return res;
    }
}