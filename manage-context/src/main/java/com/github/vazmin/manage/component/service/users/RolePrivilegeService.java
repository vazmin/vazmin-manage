package com.github.vazmin.manage.component.service.users;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.core.util.tools.IntegerTools;
import com.github.vazmin.framework.core.util.tools.LongTools;
import com.github.vazmin.framework.web.support.model.ModuleTree;
import com.github.vazmin.manage.component.dao.users.RolePrivilegeMapper;
import com.github.vazmin.manage.component.model.users.RolePrivilege;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台角色业务处理类
 *
 */
@Service
public class RolePrivilegeService extends AbstractPrivilegeService<RolePrivilege> {
    private static final Logger log = LoggerFactory.getLogger(RolePrivilegeService.class);

    @Autowired
    private RolePrivilegeMapper rolePrivilegeMapper;

    @Autowired
    private ModuleTree moduleTree;

    /**
     * 获取数据层mapper接口对象，子类必须实现该方法
     *
     * @return GenericMapper<E, PK> 数据层mapper接口对象
     */
    @Override
    protected GenericMapper<RolePrivilege, Long> getMapper() {
        return rolePrivilegeMapper;
    }

    /**
     * 根据角色id列表获取角色权限列表
     * @param roleIdList List<Long> 角色id列表
     * @return List<RolePrivilege> 角色权限列表
     */
    public List<RolePrivilege> getListByRoleId(List<Long> roleIdList) {
        return rolePrivilegeMapper.getListByRoleIdList(roleIdList);
    }

    @Override
    public List<RolePrivilege> getPrivilegeList(Long id) {
        return  rolePrivilegeMapper.getListByRoleId(id);
    }

    /**
     * 将角色权限列表构造成以"类型-id"为key的Map
     * @param privilegeList List<RolePrivilege> 权限列表
     * @return Map<String, RolePrivilege> 权限集合
     */
    protected Map<String, RolePrivilege> buildPrivilegeMap(
            List<RolePrivilege> privilegeList) {
        Map<String, RolePrivilege> privilegeMap = new HashMap<>();
        for (RolePrivilege privilege: privilegeList) {
            privilegeMap.put(privilege.getPrivilegeKey(), privilege);
        }
        return privilegeMap;
    }

    /**
     * 保存角色权限列表
     * @param roleId Long 角色id
     * @param privilegeKeys String 角色权限字符串,以逗号分隔
     * @return int 保存的新权限记录数
     * @throws ServiceProcessException
     */
    public int save(Long roleId, String privilegeKeys) throws ServiceProcessException {
        if (roleId == null) {
            throw new ServiceProcessException("roleId", "invalid.roleId", "角色id为空");
        }
        if (privilegeKeys == null) {
            throw new ServiceProcessException("rolePrivilege", "invalid.rolePrivilege", "角色权限为空");
        }
        int res = 0;
        List<RolePrivilege> oldRolePrivilegeList = getPrivilegeList(roleId);
        Map<String, RolePrivilege> oldPrivilegeMap = buildPrivilegeMap(oldRolePrivilegeList);
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
                String[] keyPair = key.split("-");
                RolePrivilege rolePrivilege = new RolePrivilege();
                rolePrivilege.setRoleId(roleId);
                rolePrivilege.setItemType(IntegerTools.parse(keyPair[0]));
                rolePrivilege.setItemId(LongTools.parse(keyPair[1]));
                res += insert(rolePrivilege);
            }
        }
        for (RolePrivilege delOne: oldPrivilegeMap.values()) {
            delete(delOne.getId());
        }
        return res;
    }

    @Override
    List<RolePrivilege> getListLeftJoin(Map<String, Object> conditions) {
        return rolePrivilegeMapper.getListLeftJoin(conditions);
    }

    @Override
    Long getUserOrRoleId(RolePrivilege rolePrivilege) {
        return rolePrivilege.getRoleId();
    }
}
