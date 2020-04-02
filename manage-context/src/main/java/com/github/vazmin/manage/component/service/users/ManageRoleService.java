package com.github.vazmin.manage.component.service.users;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.enu.StatusEnum;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.manage.component.dao.users.ManageRoleMapper;
import com.github.vazmin.manage.component.enu.system.TaskEnum;
import com.github.vazmin.manage.component.model.Constants;
import com.github.vazmin.manage.component.model.users.ManageRole;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台角色业务处理类
 *
 */
@Service
public class ManageRoleService extends LongPKBaseService<ManageRole> {
    private static final Logger log = LoggerFactory.getLogger(ManageRoleService.class);

    @Autowired
    private ManageRoleMapper manageRoleMapper;

    /**
     * 获取数据层mapper接口对象，子类必须实现该方法
     *
     * @return GenericMapper<E, PK> 数据层mapper接口对象
     */
    @Override
    protected GenericMapper<ManageRole, Long> getMapper() {
        return manageRoleMapper;
    }

    /**
     * 根据角色id获取带角色对象，如果为空，返回空对象
     * @param id Long 角色id
     * @return ManageRole 角色对象
     */
    public ManageRole getSafety(Long id) {
        ManageRole manageRole = id != null ? get(id) : new ManageRole();
        if (manageRole == null) {
            manageRole = new ManageRole();
        }
        return manageRole;
    }

    /**
     * 根据用户id获取角色列表
     * @param userId Long 用户id
     * @return List<ManageRole> 用户设置的角色列表
     */
    // TODO: add cache put
    @Cacheable(cacheNames = Constants.CacheKey.USER_ROLE, key = "#userId")
    public List<ManageRole> getListByUserId(Long userId) {
        return manageRoleMapper.getListByUserId(userId);
    }

    /**
     * 获取符合条件的所有记录
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<ManageRole> 符合条件的记录列表
     */
    public List<ManageRole> getListLeftJoin(Map<String, Object> conditions) {
        return manageRoleMapper.getListLeftJoin(conditions);
    }

    /**
     * 分页查询记录列表
     * @param pagination Pagination 分页对象
     * @return List<ManageRole> 记录列表
     */
    public List<ManageRole> getListLeftJoin(Pagination pagination) {
        return getListLeftJoin(pagination, null);
    }

    /**
     * 按条件分页查询记录列表
     * @param pagination Pagination 分页对象
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<ManageRole> 符合条件的记录列表
     */
    public List<ManageRole> getListLeftJoin(
            Pagination pagination, Map<String, Object> conditions) {
        pagination.setTotalCount(getCount(conditions));
        if (pagination.getTotalCount() <= 0) {
            return new ArrayList<>();
        }
        RowBounds rowBounds =
                new RowBounds(pagination.getOffset(), pagination.getPageSize());
        return manageRoleMapper.getListLeftJoin(rowBounds, conditions);
    }

    /**
     * 根据操作指令task修改角色状态
     *
     * @param idList List<Long> 帐号id列表
     * @param task   String 表单提交的操作指令
     * @return int 生效记录数
     * @throws ServiceProcessException
     */
    public int updateStatus(List<Long> idList, String task)
            throws ServiceProcessException {
        StatusEnum statusEnum;
        TaskEnum taskEnum = TaskEnum.valueOfCode(task);
        switch (taskEnum) {
            case BLOCK:
                statusEnum = StatusEnum.INVALID;
                break;
            case UNBLOCK:
                statusEnum = StatusEnum.VALID;
                break;
            default:
                throw new ServiceProcessException("task", "invalid.task", "无效操作指令");
        }
        int res = 0;
        Map<String, Object> param = new HashMap<>();
        param.put("status", statusEnum.getValue());
        for (Long id : idList) {
            ManageRole oldOne = get(id);
            if (oldOne != null
                    && StatusEnum.valueOf(oldOne.getStatus()) != statusEnum) {
                param.put("id", id);
                res += this.manageRoleMapper.updateStatus(param);
            }
        }
        return res;
    }

}
