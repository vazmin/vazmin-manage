package com.github.vazmin.manage.log.context.dao;


import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.log.context.model.NoticeType;
import org.apache.ibatis.annotations.Mapper;

/**
* 通知类型 MyBatis 映射接口类
*/
@Mapper
public interface NoticeTypeMapper
        extends LongPKBaseMapper<NoticeType> {
}