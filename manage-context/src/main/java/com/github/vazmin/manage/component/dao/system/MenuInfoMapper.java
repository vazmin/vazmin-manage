package com.github.vazmin.manage.component.dao.system;

import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.framework.web.support.model.MenuInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单信息 MyBatis 映射接口类
 *
 */
@Mapper
public interface MenuInfoMapper extends LongPKBaseMapper<MenuInfo> {
    /**
     * 更新菜单的discard状态
     * @param menuInfo MenuInfo 菜单对象
     * @return int 受影响的结果记录数
     */
    int updateDiscard(MenuInfo menuInfo);

    MenuInfo getByPkgName(String pkgName);

}
