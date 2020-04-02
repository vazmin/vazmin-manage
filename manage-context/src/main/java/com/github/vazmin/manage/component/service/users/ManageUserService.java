package com.github.vazmin.manage.component.service.users;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.enu.StatusEnum;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.core.util.DateUtil;
import com.github.vazmin.framework.core.util.tools.LongTools;
import com.github.vazmin.manage.component.dao.users.ManageUserMapper;
import com.github.vazmin.manage.component.enu.system.ItemTypeEnum;
import com.github.vazmin.manage.component.enu.system.TaskEnum;
import com.github.vazmin.manage.component.model.Constants;
import com.github.vazmin.manage.component.model.users.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * URP用户业务处理类
 *
 */
@Service
public class ManageUserService extends LongPKBaseService<ManageUser> {
    private static final Logger log = LoggerFactory.getLogger(ManageUserService.class);

    @Autowired
    private ManageUserMapper manageUserMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserPrivilegeService userPrivilegeService;

    @Autowired
    private RolePrivilegeService rolePrivilegeService;

    @Autowired
    private ManageRoleService manageRoleService;

    @Autowired
    private UserCacheService userCacheService;


    /* 重置密码key有效期 一天*/
    public final long RESET_KEY_VALIDITY_PERIOD = 86400L;

    /** 重置密码key长度 */
    public final int RESET_KEY_LENGTH = 30;

    /**
     * 获取数据层mapper接口对象，子类必须实现该方法
     *
     * @return GenericMapper<E, PK> 数据层mapper接口对象
     */
    @Override
    protected GenericMapper<ManageUser, Long> getMapper() {
        return manageUserMapper;
    }

    /**
     * 根据用户id获取带角色列表的用户对象
     * @param id Long 用户id
     * @return ManageUser 带角色列表的用户对象
     */
    public ManageUser getWithRoles(Long id) {
        ManageUser manageUser = get(id);
        if (manageUser != null) {
            List<UserRole> manageUserRoles =
                    userRoleService.getListByUserId(manageUser.getId());
            for (UserRole manageUserRole: manageUserRoles) {
                manageUser.getRoleIdSet().add(manageUserRole.getRoleId());
            }
            return manageUser;
        }
        return null;
    }

    /**
     * 根据用户id获取带角色列表的用户对象，如果为空，返回空对象
     * @param id Long 用户id
     * @return ManageUser 带角色列表的用户对象
     */
    public ManageUser getSafetyWithRoles(Long id) {
        ManageUser manageUser = id != null ? getWithRoles(id) : new ManageUser();
        if (manageUser == null) {
            manageUser = new ManageUser();
        }
        return manageUser;
    }

    /**
     * 根据用户名获取用户信息
     * @param username String 用户登录名
     * @return ManageUser 用户对象
     */
    public ManageUser getByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        return getByDynamicWhere(params);
    }

    /**
     * 根据用户名获取用户信息, 携带权限凭证
     * @param username String 用户登录名
     * @return ManageUser 用户对象
     */
    public ManageUser getByUsernameTakePrincipal(String username) {
        ManageUser manageUser = userCacheService.getByUsername(username);
        if (manageUser == null) {
            return null;
        }
        manageUser.setManageRoleList(manageRoleService.getListByUserId(manageUser.getId()));
        List<Long> roleIdList = manageUser.getManageRoleList()
                .stream()
                .map(ManageRole::getId)
                .collect(Collectors.toList());
        Set<String> privilegeSet = getPrivilegeSet(manageUser.getId(), roleIdList);
        manageUser.getPrivilegeKeySet().addAll(privilegeSet);
        return manageUser;
    }

    /**
     * 根据用户邮箱获取用户信息
     * @param email String 用户邮箱
     * @return ManageUser 用户对象
     */
    public ManageUser getByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        return getByDynamicWhere(params);
    }

    public ManageUser getByResetKey(String resetKey) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("resetKey", resetKey);
        return getByDynamicWhere(conditions);
    }

    /**
     * 根据用户邮箱获取用户信息
     * @param email 用户邮箱
     * @return 用户对象
     */
    public ManageUser requestResetPassword(String email) throws ServiceProcessException {
        ManageUser user = getByUsername(email);
        if (user == null) {
            user = getByEmail(email);
        }
        if (user == null) {
            return null;
        }
        user.setResetDate(DateUtil.getTimestamp());
        user.setResetKey(RandomStringUtils.randomAlphanumeric(RESET_KEY_LENGTH));
        update(user);
        return user;
    }


    /**
     * 完成重置密码
     * @param key 重置key
     * @param newPassword 新密码
     * @return manageUser
     * @throws ServiceProcessException 业务处理异常
     */
    public ManageUser completePasswordReset(String key, String newPassword) throws ServiceProcessException {
        ManageUser user = getByResetKey(key);
        if (user == null || DateUtil.getTimestamp() - user.getResetDate() > RESET_KEY_VALIDITY_PERIOD) {
            return null;
        }
        user.setPassword(newPassword);
        user.setResetKey(null);
        user.setResetDate(null);
        update(user);
        return user;
    }


    public ManageUser changePassword(ManageUser user , String newPassword) throws ServiceProcessException {
        user.setPassword(newPassword);
        update(user);
        return user;
    }

    /**
     * 根据OpenId获取用户信息
     * @param openid String 用户登录名
     * @return ManageUser 用户对象
     */
    public ManageUser getByOpenid(String openid) {
        Map<String, Object> params = new HashMap<>();
        params.put("openid", openid);
        return getByDynamicWhere(params);
    }

    /**
     * 根据用户id获取权限key集合,包括其角色的权限
     * @param id Long 用户id
     * @return Set<String> 权限 key 集合
     */

    public Set<String> getPrivilegeSet(Long id, List<Long> roleIdList) {
        if (id == null) {
            return null;
        }
        Set<String> privilegeSet = userPrivilegeService.getUserPrivilegeSet(id);
        if (roleIdList.isEmpty()) {
           return privilegeSet;
        }
        // add role's privilege
        Set<String> rolePrivilegeSet = roleIdList.stream()
                .flatMap(roleId -> rolePrivilegeService.getPrivilegeSetByRoleId(roleId).stream())
                .collect(Collectors.toSet());
        privilegeSet.addAll(rolePrivilegeSet);
        return privilegeSet;
    }



    /**
     * 保存用户信息
     * @param manageUser ManageUser 用户信息对象
     * @return int 结果记录数
     * @throws ServiceProcessException
     */
    @CachePut(cacheNames = Constants.CacheKey.MANAGE_USER, key = "#manageUser.id")
    public int save(ManageUser manageUser) throws ServiceProcessException {
        int res;
        if (LongTools.lessEqualZero(manageUser.getId())) {
            ManageUser oldOne = getByUsername(manageUser.getUsername());
            if (oldOne != null) {
                throw new ServiceProcessException(
                        "username",
                        "invalid.username",
                        "该用户名已存在，无法添加：" + manageUser.getUsername());
            }
            manageUser.setCreateDate(DateUtil.getTimestamp());
            res = insert(manageUser);
        } else {
            res = update(manageUser);
        }
        userRoleService.save(manageUser.getId(), manageUser.getRoleIdSet());
        return res;
    }

    /**
     * 更新指定id的用户最后访问时间
     * @param id Long 用户id
     */
    public void updateLastVisitDate(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("lastVisitDate", DateUtil.getTimestamp());
        this.manageUserMapper.updateLastVisitDate(params);
    }

    /**
     * 获取有效用户列表
     * @return List<ManageUser> 有效用户列表
     */
    public List<ManageUser> getValidUsers() {
        Map<String, Object> params = new HashMap<>();
        params.put("status", StatusEnum.VALID.getValue());
        return this.manageUserMapper.getList(params);
    }

    /**
     * 设置指定用户的openid
     * @param userId Long 用户id
     * @param openid String 用户的微信openid
     */
    public void setOpenid(Long userId, String openid) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", userId);
        params.put("openid", openid);
        manageUserMapper.updateOpenid(params);
    }

    /**
     * 用户取消关注微信后，移除用户的openid
     * @param openid String 用户的微信openid
     */
    public void removeOpenid(String openid) {
        Map<String, Object> params = new HashMap<>();
        params.put("openid", openid);
        manageUserMapper.removeOpenid(params);
    }

    /**
     * 获取符合条件的记录条数
     * @param conditions Map<String, Object> 查询条件
     * @return int 符合条件的记录条数
     */
    public int getCountExcludeDepartment(Map<String, Object> conditions) {
        return manageUserMapper.getCountExcludeDepartment(conditions);
    }

    /**
     * 获取符合条件的记录条数
     * @param conditions Map<String, Object> 查询条件
     * @return int 符合条件的记录条数
     */
    public int getCountExcludeGroup(Map<String, Object> conditions) {
        return manageUserMapper.getCountExcludeGroup(conditions);
    }

    /**
     * 获取符合条件的所有记录
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<ManageUser> 符合条件的记录列表
     */
    public List<ManageUser> getListExcludeDepartment(Map<String, Object> conditions) {
        return manageUserMapper.getListExcludeDepartment(conditions);
    }

    /**
     * 按条件分页查询记录列表
     * @param pagination Pagination 分页对象
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<ManageUser> 符合条件的记录列表
     */
    public List<ManageUser> getListExcludeDepartment(
            Pagination pagination, Map<String, Object> conditions) {
        pagination.setTotalCount(getCountExcludeDepartment(conditions));
        if (pagination.getTotalCount() <= 0) {
            return new ArrayList<>();
        }
        RowBounds rowBounds =
                new RowBounds(pagination.getOffset(), pagination.getPageSize());
        return manageUserMapper.getListExcludeDepartment(rowBounds, conditions);
    }

    /**
     * 按条件分页查询记录列表
     * @param pagination Pagination 分页对象
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<ManageUser> 符合条件的记录列表
     */
    public List<ManageUser> getListExcludeGroup(
            Pagination pagination, Map<String, Object> conditions) {
        pagination.setTotalCount(getCountExcludeGroup(conditions));
        if (pagination.getTotalCount() <= 0) {
            return new ArrayList<>();
        }
        RowBounds rowBounds =
                new RowBounds(pagination.getOffset(), pagination.getPageSize());
        return manageUserMapper.getListExcludeGroup(rowBounds, conditions);
    }

    /**
     * 根据操作指令task修改用户状态
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
            ManageUser oldOne = get(id);
            if (oldOne != null
                    && StatusEnum.valueOf(oldOne.getStatus()) != statusEnum) {
                param.put("id", id);
                res += this.manageUserMapper.updateStatus(param);
            }
        }
        return res;
    }

    /**
     * 仅获取name，username和id
     * @return List<ManageUser>
     */
    public List<ManageUser> getListOpen() {
        return manageUserMapper.getListOpen();
    }

    /**
     * 仅获取name，username和id
     * @param pagination Pagination 分页对象
     * @param conditions Map<String, Object> conditions 条件Map
     * @param noPaging 分页， null不分页
     * @return List<E> 符合条件的记录列表
     */
    public List<ManageUser> getListOpen(
            Pagination pagination, Map<String, Object> conditions, Integer noPaging) {
        if (noPaging == null) {
            pagination.setTotalCount(getCount(conditions));
            if (pagination.getTotalCount() <= 0) {
                return new ArrayList<>();
            }
            RowBounds rowBounds =
                    new RowBounds(pagination.getOffset(), pagination.getPageSize());
            return manageUserMapper.getListOpen(rowBounds, conditions);
        } else {
            return manageUserMapper.getListOpen();
        }
    }

    /**
     * 根据id集合获取用户列表
     * @param idSet id集合
     * @return 用户列表
     */
    public List<ManageUser> getListByIdSet(Set<Long> idSet) {
        if (idSet == null || idSet.isEmpty()) {
            return null;
        }
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("idSet", idSet);
        return getList(conditions);
    }

    /**
     * 获取有该权限的用户列表
     * @param privilegeId 权限id
     * @param itemTypeEnum 类型
     * @return 用户列表
     */
    public List<ManageUser> getListByPrivilegeId(Long privilegeId, ItemTypeEnum itemTypeEnum) {
        Set<Long> roleIdSet = rolePrivilegeService.getRoleOrUserIdSetByItem(privilegeId, itemTypeEnum);
        Set<Long> userIdSet = userPrivilegeService.getRoleOrUserIdSetByItem(privilegeId, itemTypeEnum);
        Set<Long> userIdSetRole = userRoleService.getUserIdSet(roleIdSet);
        userIdSet.addAll(userIdSetRole);
        return getListByIdSet(userIdSet);
    }

    public int requestPasswordReset(String mail) {
        return 0;
    }


}
