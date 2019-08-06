package com.github.vazmin.manage.log.context.dao;


import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.log.context.model.SystemMessage;
import org.apache.ibatis.annotations.Mapper;

/**
* 系统错误日志 MyBatis 映射接口类
*
 *
*/
@Mapper
public interface SystemMessageMapper
        extends LongPKBaseMapper<SystemMessage> {
}