package com.github.vazmin.manage.component.service.system;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.web.support.model.MenuInfo;
import com.github.vazmin.manage.component.dao.system.MenuInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单信息业务处理类
 *
 */
@Service
public class MenuInfoService extends LongPKBaseService<MenuInfo>{
    private static final Logger log = LoggerFactory.getLogger(MenuInfoService.class);

    @Autowired
    private MenuInfoMapper menuInfoMapper;

    /**
     * 获取数据层mapper接口对象，子类必须实现该方法
     *
     * @return GenericMapper<E, PK> 数据层mapper接口对象
     */
    @Override
    protected GenericMapper<MenuInfo, Long> getMapper() {
        return menuInfoMapper;
    }

    /**
     * 保存菜单信息，根据包名查询是否已有记录，如果存在则更新，如果不存在则插入。
     * @param menuInfo MenuInfo 菜单信息对象
     * @throws ServiceProcessException
     */
    public void save(MenuInfo menuInfo) throws ServiceProcessException {
        MenuInfo oldMenuInfo = getByPkgName(menuInfo.getPkgName());
        if (oldMenuInfo != null) {
            menuInfo.setId(oldMenuInfo.getId());
            update(menuInfo);
        } else {
            insert(menuInfo);
        }
    }

    /**
     * 根据包名获取菜单信息对象
     * @param pkgName String 包名字符串
     * @return MenuInfo 菜单信息对象
     */
    public MenuInfo getByPkgName(String pkgName) {
        return menuInfoMapper.getByPkgName(pkgName);
    }

    /**
     * 更新菜单的discard状态
     * @param menuInfo MenuInfo 菜单对象
     * @return int 受影响的结果记录数
     */
    public int updateDiscard(MenuInfo menuInfo) {
        return menuInfoMapper.updateDiscard(menuInfo);
    }

    /**
     * 获取菜单集合，pkgName为 key，菜单对象为 value
     * @return Map<String, MenuInfo>
     */
    public Map<String, MenuInfo> getMenuMap() {
        Map<String, MenuInfo> menuMap = new HashMap<>();
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("discard", false);
        List<MenuInfo> menuInfoList = getList(conditions);
        for (MenuInfo menuInfo: menuInfoList) {
            menuMap.put(menuInfo.getPkgName(), menuInfo);
        }
        return menuMap;
    }

    /**
     * 根据id获取对象，如果为空，返回空对象
     * @param id Long 记录id
     * @return MenuInfo 菜单对象
     */
    public MenuInfo getSafety(Long id) {
        MenuInfo menuInfo = get(id);
        if (menuInfo == null) {
            menuInfo = new MenuInfo();
        }
        return menuInfo;
    }
}
