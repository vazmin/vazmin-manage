package com.github.vazmin.manage.log.context.service;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.util.DateUtil;
import com.github.vazmin.manage.log.context.dao.SystemMessageMapper;
import com.github.vazmin.manage.log.context.enu.MessageLevelEnum;
import com.github.vazmin.manage.log.context.model.SystemMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
* 系统错误日志 业务处理类
*
 *
*/
@Service
public class SystemMessageService
        extends LongPKBaseService<SystemMessage> {
    private static final Logger log = LoggerFactory.getLogger(SystemMessageService.class);

    @Autowired
    private SystemMessageMapper systemMessageMapper;

    /**
    * 获取数据层mapper接口对象，子类必须实现该方法
    *
    * @return GenericMapper<E, PK> 数据层mapper接口对象
    */
    @Override
    protected GenericMapper<SystemMessage, Long> getMapper() {
        return systemMessageMapper;
    }

    /**
    * 根据id获取对象，如果为空，返回空对象
    * @param id Long 公司id
    * @return SystemMessage 系统错误日志对象
    */
    public SystemMessage getSafety(Long id) {
        SystemMessage systemMessage = get(id);
        if (systemMessage == null) {
            systemMessage = new SystemMessage();
        }
        return systemMessage;
    }

    /**
     * 保存异常消息
     * @param title String 标题，简单的描述文字
     * @param errorClass Class<?> 异常所在类
     * @param e Exception 异常对象
     */
    public void save(String title, Class<?> errorClass, Exception e) {
        try {
            SystemMessage message = new SystemMessage();
            message.setTitle(title);
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            message.setMessage(errors.toString());
            message.setMessageLevel(MessageLevelEnum.ERROR.getValue());
            message.setMessageModule(errorClass.getName());
            message.setMessageLine("" + e.getStackTrace()[0].getLineNumber());
            message.setMessageTime(DateUtil.getTimestamp());
            insert(message);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    /**
     * 保存错误消息，非抛出异常消息
     * @param title String 标题，简单的描述文字
     * @param errorClass Class<?> 错误所在类
     * @param messageContent String 错误内容
     */
    public void save(String title, Class<?> errorClass, String messageContent) {
        save(title, errorClass, messageContent, MessageLevelEnum.ERROR);
    }

    /**
     * 保存错误消息，非抛出异常消息
     * @param title String 标题，简单的描述文字
     * @param errorClass Class<?> 错误所在类
     * @param messageContent String 错误内容
     * @param levelEnum MessageLevelEnum 消息级别
     */
    public void save(String title, Class<?> errorClass, String messageContent,
                     MessageLevelEnum levelEnum) {
        try {
            SystemMessage message = new SystemMessage();
            message.setTitle(title);
            message.setMessage(messageContent);
            message.setMessageLevel(levelEnum.getValue());
            message.setMessageModule(errorClass.getName());
            message.setMessageLine("");
            message.setMessageTime(DateUtil.getTimestamp());
            insert(message);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }
}