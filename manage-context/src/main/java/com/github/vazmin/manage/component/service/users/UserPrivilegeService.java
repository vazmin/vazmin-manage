package com.github.vazmin.manage.component.service.users;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.core.util.tools.IntegerTools;
import com.github.vazmin.framework.core.util.tools.LongTools;
import com.github.vazmin.manage.component.dao.users.UserPrivilegeMapper;
import com.github.vazmin.manage.component.model.users.UserPrivilege;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台用户却权限业务处理类
 *
 */
@Service
public class UserPrivilegeService extends AbstractPrivilegeService<UserPrivilege> {
    private static final Logger log = LoggerFactory.getLogger(UserPrivilegeService.class);

    @Autowired
    private UserPrivilegeMapper userPrivilegeMapper;

    /**
     * 获取数据层mapper接口对象，子类必须实现该方法
     *
     * @return GenericMapper<E, PK> 数据层mapper接口对象
     */
    @Override
    protected GenericMapper<UserPrivilege, Long> getMapper() {
        return userPrivilegeMapper;
    }

    @Override
    public List<UserPrivilege> getPrivilegeList(Long id) {
        return userPrivilegeMapper.getListByUserId(id);
    }

    /**
     * 将用户权限列表构造成以"类型-id"为key的Map
     * @param privilegeList List<UserPrivilege> 权限列表
     * @return Map<String, UserPrivilege> 权限集合
     */
    protected Map<String, UserPrivilege> buildPrivilegeMap(
            List<UserPrivilege> privilegeList) {
        Map<String, UserPrivilege> privilegeMap = new HashMap<>();
        for (UserPrivilege privilege: privilegeList) {
            privilegeMap.put(privilege.getPrivilegeKey(), privilege);
        }
        return privilegeMap;
    }

    /**
     * 保存用户权限列表
     * @param userId Long 用户id
     * @param privilegeKeys String 用户权限字符串,以逗号分隔
     * @return int 保存的新权限记录数
     * @throws ServiceProcessException
     */
    public int save(Long userId, String privilegeKeys) throws ServiceProcessException {
        if (userId == null) {
            throw new ServiceProcessException("userId", "invalid.userId", "用户id为空");
        }
        if (privilegeKeys == null) {
            throw new ServiceProcessException("userPrivilege", "invalid.userPrivilege", "用户权限为空");
        }
        int res = 0;
        List<UserPrivilege> oldUserPrivilegeList = getPrivilegeList(userId);
        Map<String, UserPrivilege> oldPrivilegeMap = buildPrivilegeMap(oldUserPrivilegeList);
        String[] privileges = privilegeKeys.split(",");
        for (String privilegeKey: privileges) {
            String key = privilegeKey.trim();
            if (!key.contains("-")) {
                continue;
            }
            if (oldPrivilegeMap.containsKey(key)) {
                oldPrivilegeMap.remove(key);
            } else {
                //新增
                String[] keyPair = privilegeKey.trim().split("-");
                UserPrivilege userPrivilege = new UserPrivilege();
                userPrivilege.setUserId(userId);
                userPrivilege.setItemType(IntegerTools.parse(keyPair[0]));
                userPrivilege.setItemId(LongTools.parse(keyPair[1]));
                res += insert(userPrivilege);
            }
        }
        for (UserPrivilege delOne: oldPrivilegeMap.values()) {
            delete(delOne.getId());
        }
        return res;
    }

    @Override
    List<UserPrivilege> getListLeftJoin(Map<String, Object> conditions) {
        return userPrivilegeMapper.getListLeftJoin(conditions);
    }

    @Override
    Long getUserOrRoleId(UserPrivilege userPrivilege) {
        return userPrivilege.getUserId();
    }
}
