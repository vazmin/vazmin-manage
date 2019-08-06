package com.github.vazmin.manage.component.dao.system;

import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.framework.web.support.model.ModuleInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 模块信息 MyBatis 映射接口类
 *
 */
@Mapper
public interface ModuleInfoMapper extends LongPKBaseMapper<ModuleInfo> {
    /**
     * 更新模块的discard状态
     * @param moduleInfo ModuleInfo 模块对象
     * @return int 受影响的结果记录数
     */
    int updateDiscard(ModuleInfo moduleInfo);

    ModuleInfo getByPkgName(String pkgName);
}
