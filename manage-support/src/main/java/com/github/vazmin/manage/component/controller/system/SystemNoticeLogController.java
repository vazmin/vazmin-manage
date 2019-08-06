package com.github.vazmin.manage.component.controller.system;

import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.framework.web.support.annotation.Command;
import com.github.vazmin.framework.web.support.annotation.Module;
import com.github.vazmin.manage.component.controller.ManageControllerInterface;
import com.github.vazmin.manage.log.context.model.SystemNoticeLog;
import com.github.vazmin.manage.log.context.service.SystemNoticeLogService;
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
 * 系统通知记录 管理控制器类。
 * Created by wangzhiming on 10/27/18.
 */
@Module(value = SystemNoticeLogController.MODULE_NAME, order = 6)
@RestController
@RequestMapping(SystemNoticeLogController.URL_PREFIX)
public class SystemNoticeLogController implements ManageControllerInterface {
    private static final Logger log = LoggerFactory.getLogger(SystemNoticeLogController.class);

    public static final String MODULE_NAME = "系统通知记录";
    public static final String URL_PREFIX = "/api/system/system-notice-log";

    @Autowired
    private SystemNoticeLogService systemNoticeLogService;

    /**
     * GET /list : get SystemNoticeLog list
     *
     * @param pagination to paging
     * @param query params
     * @return the ResponseEntity with status 200 (OK) and with body the SystemNoticeLog list
     */
    @Command(MODULE_NAME + " list")
    @RequestMapping(value = LIST_URL, method = RequestMethod.GET)
    public ResponseEntity list(Pagination pagination, Query query) {

        List<SystemNoticeLog> systemNoticeLogList
                = systemNoticeLogService.getListLeftJoin(pagination, query.getFilter());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pagination, URL_PREFIX);
        return new ResponseEntity<>(systemNoticeLogList, headers, HttpStatus.OK);
    }

    /**
    * GET /detail?id={id} : get the "id" systemNoticeLog.
    *
    * @param id the id of the systemNoticeLog to find
    * @return the ResponseEntity with status 200 (OK) and with body the "id" systemNoticeLog, or with status 404 (Not Found)
    */
    @Command(MODULE_NAME + " detail")
    @RequestMapping(value = DETAIL_URL, method = RequestMethod.GET)
    public ResponseEntity<SystemNoticeLog> get(@RequestParam Long id) {
        return ResponseUtil.wrapOrNotFound(Optional.of(systemNoticeLogService.getLeftJoin(id)));
    }

}
