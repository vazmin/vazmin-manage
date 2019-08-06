package com.github.vazmin.manage.log.context.dao;


import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.manage.log.context.model.CommandLog;
import org.apache.ibatis.annotations.Mapper;

/**
* 用户操作日志 MyBatis 映射接口类
*
* Created by zhiming on 10/24/16.
*/
@Mapper
public interface CommandLogMapper
        extends LongPKBaseMapper<CommandLog> {
}