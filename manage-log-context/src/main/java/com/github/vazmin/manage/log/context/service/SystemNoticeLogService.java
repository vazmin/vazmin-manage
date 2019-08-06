package com.github.vazmin.manage.log.context.service;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.manage.log.context.dao.SystemNoticeLogMapper;
import com.github.vazmin.manage.log.context.enu.SystemNoticeModelEnum;
import com.github.vazmin.manage.log.context.model.SystemNoticeLog;
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
* 系统通知记录 业务处理类
*
* Created by zhiming on 9/27/16.
*/
@Service
public class SystemNoticeLogService
        extends LongPKBaseService<SystemNoticeLog> {
    private static final Logger log = LoggerFactory.getLogger(SystemNoticeLogService.class);

    @Autowired
    private SystemNoticeLogMapper systemNoticeLogMapper;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<SystemNoticeLog, Long> getMapper() {
        return systemNoticeLogMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return SystemNoticeLog 系统通知记录对象
    */
    public SystemNoticeLog getSafety(Long id) {
        SystemNoticeLog systemNoticeLog = get(id);
        if (systemNoticeLog == null) {
            systemNoticeLog = new SystemNoticeLog();
        }
        return systemNoticeLog;
    }

    /**
     * 按条件分页查询记录列表
     * @param pagination Pagination 分页对象
     * @param conditions Map<String, Object> conditions 条件Map
     * @return List<E> 符合条件的记录列表
     */
    public List<SystemNoticeLog> getListLeftJoin(
            Pagination pagination, Map<String, Object> conditions) {
        pagination.setTotalCount(getCount(conditions));
        if (pagination.getTotalCount() <= 0) {
            return new ArrayList<>();
        }
        RowBounds rowBounds =
                new RowBounds(pagination.getOffset(), pagination.getPageSize());
        return systemNoticeLogMapper.getListLeftJoin(rowBounds, conditions);
    }

    /**
     * 根据id获取历史纪录，包括登录名
     * @param id Long 记录id
     * @return SystemNoticeConfig 系统通知配置
     */
    public SystemNoticeLog getLeftJoin(Long id) {
        return id != null ? systemNoticeLogMapper.getLeftJoin(id) : null;
    }

    /**
     * 获取指定通知类型某个时间段之后的消息记录
     * @param noticeModelEnum 系统通知类型枚举
     * @param beginTime 开始时间
     * @return 符合条件的记录列表
     */
    public List<SystemNoticeLog> getListByNoticeType(SystemNoticeModelEnum noticeModelEnum, Long beginTime) {
        return getListByNoticeType(noticeModelEnum.getValue(), beginTime);
    }

    /**
     * 获取指定通知类型某个时间段之后的消息记录
     * @param noticeType 系统通知类型枚举
     * @param beginTime 开始时间
     * @return 符合条件的记录列表
     */
    public List<SystemNoticeLog> getListByNoticeType(Integer noticeType, Long beginTime) {
        return getListByNoticeType(null, noticeType, beginTime);
    }

    /**
     * 获取指定通知类型某个时间段之后的消息记录
     * @param noticeType 系统通知类型枚举
     * @param beginTime 开始时间
     * @return 符合条件的记录列表
     */
    public List<SystemNoticeLog> getListByNoticeType(String title, Integer noticeType, Long beginTime) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("title", title);
        conditions.put("noticeType", noticeType);
        conditions.put("beginTime", beginTime);
        return getList(conditions);
    }

    /**
     * 批量插入
     * @param systemNoticeLogList 通知列表
     * @return int
     */
    public int batchInsert(List<SystemNoticeLog> systemNoticeLogList) {
        if (systemNoticeLogList == null || systemNoticeLogList.isEmpty()) {
            return 0;
        }
        return systemNoticeLogMapper.batchInsert(systemNoticeLogList);
    }
}