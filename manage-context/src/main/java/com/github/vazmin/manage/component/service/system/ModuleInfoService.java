package com.github.vazmin.manage.component.service.system;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.web.support.model.ModuleInfo;
import com.github.vazmin.manage.component.dao.system.ModuleInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模块信息业务处理类
 *
 */
@Service
public class ModuleInfoService extends LongPKBaseService<ModuleInfo> {
    private static final Logger log = LoggerFactory.getLogger(ModuleInfoService.class);

    @Autowired
    private ModuleInfoMapper moduleInfoMapper;

    /**
     * 获取数据层mapper接口对象，子类必须实现该方法
     *
     * @return GenericMapper<E, PK> 数据层mapper接口对象
     */
    @Override
    protected GenericMapper<ModuleInfo, Long> getMapper() {
        return moduleInfoMapper;
    }

    /**
     * 保存模块信息，根据包名查询是否已有记录，如果存在则更新，如果不存在则插入。
     * @param moduleInfo ModuleInfo 菜单信息对象
     * @throws ServiceProcessException
     */
    public void save(ModuleInfo moduleInfo) throws ServiceProcessException {
        ModuleInfo oldModuleInfo = getByPkgName(moduleInfo.getPkgName());
        if (oldModuleInfo != null) {
            moduleInfo.setId(oldModuleInfo.getId());
            update(moduleInfo);
        } else {
            insert(moduleInfo);
        }
    }

    /**
     * 根据包名获取模块信息对象
     * @param pkgName String 包名字符串
     * @return MenuInfo 模块信息对象
     */
    public ModuleInfo getByPkgName(String pkgName) {
        return moduleInfoMapper.getByPkgName(pkgName);
    }

    /**
     * 更新模块的discard状态
     * @param moduleInfo ModuleInfo 模块对象
     * @return int 受影响的结果记录数
     */
    public int updateDiscard(ModuleInfo moduleInfo) {
        return moduleInfoMapper.updateDiscard(moduleInfo);
    }

    /**
     * 获取模块集合，pkgName为 key，模块对象为 value
     * @return Map<String, ModuleInfo>
     */
    public Map<String, ModuleInfo> getModuleMap() {
        Map<String, ModuleInfo> moduleMap = new HashMap<>();
        List<ModuleInfo> moduleInfoList = getList();
        for (ModuleInfo moduleInfo: moduleInfoList) {
            moduleMap.put(moduleInfo.getPkgName(), moduleInfo);
        }
        return moduleMap;
    }

    /**
     * 根据id获取对象，如果为空，返回空对象
     * @param id Long 记录id
     * @return ModuleInfo 模块对象
     */
    public ModuleInfo getSafety(Long id) {
        ModuleInfo moduleInfo = get(id);
        if (moduleInfo == null) {
            moduleInfo = new ModuleInfo();
        }
        return moduleInfo;
    }
}
