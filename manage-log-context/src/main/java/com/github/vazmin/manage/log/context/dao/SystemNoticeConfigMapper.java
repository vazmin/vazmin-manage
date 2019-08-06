package com.github.vazmin.manage.log.context.dao;

import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.log.context.model.SystemNoticeConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
* 系统通知配置 MyBatis 映射接口类
*
* Created by zhiming on 9/26/16.
*/
@Mapper
public interface SystemNoticeConfigMapper
        extends LongPKBaseMapper<SystemNoticeConfig> {
    List<SystemNoticeConfig> getListLeftJoin();

    /**
     * 根据条件查询记录列表
     * @param conditions Map<String, Object> 条件map
     * @return List<E> 记录列表
     */
    List<SystemNoticeConfig> getListLeftJoin(Map<String, Object> conditions);

    /**
     * 查询全部记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @return List<E> 记录列表
     */
    List<SystemNoticeConfig> getListLeftJoin(RowBounds rowBounds);

    /**
     * 根据条件查询记录列表，带翻页
     * @param rowBounds RowBounds 翻页对象：起始位置、返回条数
     * @param conditions Map<String, Object> 条件map
     * @return List<E> 记录列表
     */
    List<SystemNoticeConfig> getListLeftJoin(RowBounds rowBounds, Map<String, Object> conditions);

    /**
     * 根据id获取migu和mm的开发者密码更新历史,带开发者登录名、基地名
     * @param id Long 记录id
     * @return MiguMmUpdatePasswordHistory migu和mm的开发者密码更新历史
     */
    SystemNoticeConfig getLeftJoin(Long id);
}