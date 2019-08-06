package com.github.vazmin.manage.log.context.service;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.manage.log.context.dao.NoticeTypeMapper;
import com.github.vazmin.manage.log.context.model.NoticeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* 通知类型 业务处理类
*
*/
@Service
public class NoticeTypeService
        extends LongPKBaseService<NoticeType> {
    private static final Logger log = LoggerFactory.getLogger(NoticeTypeService.class);

    @Autowired
    private NoticeTypeMapper noticeTypeMapper;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<NoticeType, Long> getMapper() {
        return noticeTypeMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return NoticeType 通知类型对象
    */
    public NoticeType getSafety(Long id) {
        NoticeType noticeType = get(id);
        if (noticeType == null) {
            noticeType = new NoticeType();
        }
        return noticeType;
    }
}