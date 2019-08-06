package com.github.vazmin.manage.log.context.service;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.manage.log.context.dao.SystemNoticeConfigMapper;
import com.github.vazmin.manage.log.context.model.SystemNoticeConfig;
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
* 系统通知配置 业务处理类
*
* Created by zhiming on 9/26/16.
*/
@Service
public class SystemNoticeConfigService
        extends LongPKBaseService<SystemNoticeConfig> {
    private static final Logger log = LoggerFactory.getLogger(SystemNoticeConfigService.class);

    @Autowired
    private SystemNoticeConfigMapper systemNoticeConfigMapper;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<SystemNoticeConfig, Long> getMapper() {
        return systemNoticeConfigMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return SystemNoticeConfig 系统通知配置对象
    */
    public SystemNoticeConfig getSafety(Long id) {
        SystemNoticeConfig systemNoticeConfig = getLeftJoin(id);
        if (systemNoticeConfig == null) {
            systemNoticeConfig = new SystemNoticeConfig();
        }
        return systemNoticeConfig;
    }

    /**
     * 按条件分页查询记录列表
     * @param pagination Pagination 分页对象
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<E> 符合条件的记录列表
     */
    public List<SystemNoticeConfig> getListLeftJoin(
            Pagination pagination, Map<String, Object> conditions) {
        pagination.setTotalCount(getCount(conditions));
        if (pagination.getTotalCount() <= 0) {
            return new ArrayList<>();
        }
        RowBounds rowBounds =
                new RowBounds(pagination.getOffset(), pagination.getPageSize());
        return systemNoticeConfigMapper.getListLeftJoin(rowBounds, conditions);
    }

    /**
     * 根据id获取历史纪录，包括登录名、邮箱、电话
     * @param id Long 记录id
     * @return SystemNoticeConfig 系统通知配置
     */
    public SystemNoticeConfig getLeftJoin(Long id) {
        return id != null ? systemNoticeConfigMapper.getLeftJoin(id) : null;
    }

    /**
     * 根据通知类型获得系统通知配置列表
     * @param code Integer 通知类型code
     * @return 系统通知配置列表
     */
    public List<SystemNoticeConfig> getListByNoticeType(Integer code){
        Map<String, Object> params = new HashMap<>();
        params.put("noticeType", code);
        return systemNoticeConfigMapper.getListLeftJoin(params);
    }
}