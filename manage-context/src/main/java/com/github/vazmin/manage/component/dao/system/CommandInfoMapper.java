package com.github.vazmin.manage.component.dao.system;


import com.github.vazmin.framework.core.dao.LongPKBaseMapper;
import com.github.vazmin.framework.web.support.model.CommandInfo;
import org.apache.ibatis.annotations.Mapper;


/**
 * 命令信息 MyBatis 映射接口类
 *
 */
@Mapper
public interface CommandInfoMapper extends LongPKBaseMapper<CommandInfo> {
    /**
     * 更新命令的discard状态
     * @param commandInfo CommandInfo 命令对象
     * @return int 受影响的结果记录数
     */
    int updateDiscard(CommandInfo commandInfo);

    CommandInfo getByPath(String path);
}
