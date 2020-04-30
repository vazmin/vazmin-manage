package com.github.vazmin.manage.component.service.system;

import com.github.vazmin.framework.core.dao.GenericMapper;
import com.github.vazmin.framework.core.service.LongPKBaseService;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.web.support.model.CommandInfo;
import com.github.vazmin.manage.component.dao.system.CommandInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 命令信息业务处理类
 *
 */
@Service
public class CommandInfoService extends LongPKBaseService<CommandInfo> {
    private static final Logger log = LoggerFactory.getLogger(CommandInfoService.class);

    @Autowired
    private CommandInfoMapper commandInfoMapper;

    /**
     * 获取数据层mapper接口对象，子类必须实现该方法
     *
     * @return GenericMapper<E, PK> 数据层mapper接口对象
     */
    @Override
    protected GenericMapper<CommandInfo, Long> getMapper() {
        return commandInfoMapper;
    }

    /**
     * 保存命令信息，根据访问路径查询是否已有记录，如果存在则更新，如果不存在则插入。
     * @param commandInfo CommandInfo 命令信息对象
     * @throws ServiceProcessException
     */
    public void save(CommandInfo commandInfo) throws ServiceProcessException {
        CommandInfo oldCommandInfo = getByPathAndMethod(
                commandInfo.getPath(), commandInfo.getMethod());
        if (oldCommandInfo != null) {
            commandInfo.setId(oldCommandInfo.getId());
            update(commandInfo);
        } else {
            insert(commandInfo);
        }
    }

    /**
     * 根据包名获取命令信息对象
     * @param path String 访问路径字符串
     * @return CommandInfo 命令信息对象
     */
    public CommandInfo getByPath(String path) {
        return commandInfoMapper.getByPath(path);
    }

    public CommandInfo getByPathAndMethod(String path, String method) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("path", path);
        conditions.put("method", method);
        return getByDynamicWhere(conditions);
    }

    /**
     * 更新命令的discard状态
     * @param commandInfo CommandInfo 命令对象
     * @return int 受影响的结果记录数
     */
    public int updateDiscard(CommandInfo commandInfo) {
        return commandInfoMapper.updateDiscard(commandInfo);
    }

    /**
     * 获取菜单集合，pkgName为 key，菜单对象为 value
     * @return Map<String, CommandInfo>
     */
    public Map<CommandInfo.Key, CommandInfo> getCommandMap() {
        Map<CommandInfo.Key, CommandInfo> commandMap = new HashMap<>();
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("discard", false);
        List<CommandInfo> commandInfoList = getList(conditions);
        for (CommandInfo commandInfo: commandInfoList) {
            commandInfo.setRequestMethod(RequestMethod.valueOf(commandInfo.getMethod()));
            commandMap.put(commandInfo.buildKey(), commandInfo);
        }
        return commandMap;
    }

    /**
     * 根据id获取对象，如果为空，返回空对象
     * @param id Long 记录id
     * @return CommandInfo 命令对象
     */
    public CommandInfo getSafety(Long id) {
        CommandInfo commandInfo = get(id);
        if (commandInfo == null) {
            commandInfo = new CommandInfo();
        }
        return commandInfo;
    }
}
