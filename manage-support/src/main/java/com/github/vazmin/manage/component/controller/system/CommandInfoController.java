package com.github.vazmin.manage.component.controller.system;

import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.framework.web.support.annotation.Command;
import com.github.vazmin.framework.web.support.annotation.Module;
import com.github.vazmin.framework.web.support.model.CommandInfo;
import com.github.vazmin.manage.component.controller.ManageControllerInterface;
import com.github.vazmin.manage.component.service.system.CommandInfoService;
import com.github.vazmin.manage.support.query.Query;
import com.github.vazmin.manage.support.util.PaginationUtil;
import com.github.vazmin.manage.support.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


/**
 * 命令信息 管理控制器类。
 * Created by wangzhiming on 9/29/18.
 */
@Module(value = CommandInfoController.MODULE_NAME, order = 3)
@RestController
@RequestMapping(CommandInfoController.URL_PREFIX)
public class CommandInfoController implements ManageControllerInterface {
    private static final Logger log = LoggerFactory.getLogger(CommandInfoController.class);

    public static final String MODULE_NAME = "命令信息";
    public static final String URL_PREFIX = "/api/system/command-info";

    @Autowired
    private CommandInfoService commandInfoService;

    /**
     * GET /list : get CommandInfo list
     *
     * @param pagination to paging
     * @param query params
     * @return the ResponseEntity with status 200 (OK) and with body the CommandInfo list
     */
    @Command(MODULE_NAME + " list")
    @RequestMapping(value = LIST_URL, method = RequestMethod.GET)
    public ResponseEntity list(Pagination pagination, Query query) {

        List<CommandInfo> commandInfoList
                = commandInfoService.getList(pagination, query.getFilter());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pagination, URL_PREFIX);
        return new ResponseEntity<>(commandInfoList, headers, HttpStatus.OK);
    }

    /**
    * GET /detail?id={id} : get the "id" commandInfo.
    *
    * @param id the id of the commandInfo to find
    * @return the ResponseEntity with status 200 (OK) and with body the "id" commandInfo, or with status 404 (Not Found)
    */
    @Command(MODULE_NAME + " detail")
    @RequestMapping(value = DETAIL_URL, method = RequestMethod.GET)
    public ResponseEntity<CommandInfo> get(@RequestParam Long id) {
        return ResponseUtil.wrapOrNotFound(Optional.of(commandInfoService.get(id)));
    }

}
