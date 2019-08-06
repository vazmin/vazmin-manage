package com.github.vazmin.manage.log.context.service;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.manage.log.context.dao.CommandLogMapper;
import com.github.vazmin.manage.log.context.model.CommandLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* 用户操作日志 业务处理类
*
* Created by zhiming on 10/24/16.
*/
@Service
public class CommandLogService
        extends LongPKBaseService<CommandLog> {
    private static final Logger log = LoggerFactory.getLogger(CommandLogService.class);

    @Autowired
    private CommandLogMapper commandLogMapper;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<CommandLog, Long> getMapper() {
        return commandLogMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return CommandLog 用户操作日志对象
    */
    public CommandLog getSafety(Long id) {
        CommandLog commandLog = get(id);
        if (commandLog == null) {
            commandLog = new CommandLog();
        }
        return commandLog;
    }
}