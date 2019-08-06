package com.github.vazmin.manage.component.controller.system;

import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.framework.web.support.annotation.Command;
import com.github.vazmin.framework.web.support.annotation.Module;
import com.github.vazmin.manage.component.controller.ManageControllerInterface;
import com.github.vazmin.manage.log.context.model.SystemMessage;
import com.github.vazmin.manage.log.context.service.SystemMessageService;
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
 * 系统错误日志 管理控制器类。
 * Created by wangzhiming on 9/30/18.
 */
@Module(value = SystemMessageController.MODULE_NAME, order = 5)
@RestController
@RequestMapping(SystemMessageController.URL_PREFIX)
public class SystemMessageController implements ManageControllerInterface {
    private static final Logger log = LoggerFactory.getLogger(SystemMessageController.class);

    public static final String MODULE_NAME = "系统错误日志";
    public static final String URL_PREFIX = "/api/system/system-message";

    @Autowired
    private SystemMessageService systemMessageService;


    /**
     * GET /list : get SystemMessage list
     *
     * @param pagination to paging
     * @param query params
     * @return the ResponseEntity with status 200 (OK) and with body the SystemMessage list
     */
    @Command(MODULE_NAME + " list")
    @RequestMapping(value = LIST_URL, method = RequestMethod.GET)
    public ResponseEntity list(Pagination pagination, Query query) {

        List<SystemMessage> systemMessageList
                = systemMessageService.getList(pagination, query.getFilter());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pagination, URL_PREFIX);
        return new ResponseEntity<>(systemMessageList, headers, HttpStatus.OK);
    }

    /**
    * GET /detail?id={id} : get the "id" systemMessage.
    *
    * @param id the id of the systemMessage to find
    * @return the ResponseEntity with status 200 (OK) and with body the "id" systemMessage, or with status 404 (Not Found)
    */
    @Command(MODULE_NAME + " detail")
    @RequestMapping(value = DETAIL_URL, method = RequestMethod.GET)
    public ResponseEntity<SystemMessage> get(@RequestParam Long id) {
        return ResponseUtil.wrapOrNotFound(Optional.of(systemMessageService.get(id)));
    }

}
