package com.github.vazmin.manage.component.controller.account;

import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.core.util.tools.LongTools;
import com.github.vazmin.framework.web.support.annotation.Command;
import com.github.vazmin.framework.web.support.annotation.Module;
import com.github.vazmin.manage.component.controller.ManageControllerInterface;
import com.github.vazmin.manage.component.model.users.GroupInfo;
import com.github.vazmin.manage.component.service.users.GroupInfoService;
import com.github.vazmin.manage.component.controller.errors.BadRequestAlertException;
import com.github.vazmin.manage.support.query.Query;
import com.github.vazmin.manage.support.util.HeaderUtil;
import com.github.vazmin.manage.support.util.PaginationUtil;
import com.github.vazmin.manage.support.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


/**
 * 业务组信息 管理控制器类。
 * Created by wangzhiming on 10/15/18.
 */
@Module(value = GroupInfoController.MODULE_NAME, order = 3, enable = false)
@RestController
@RequestMapping(GroupInfoController.URL_PREFIX)
public class GroupInfoController implements ManageControllerInterface {
    private static final Logger log = LoggerFactory.getLogger(GroupInfoController.class);

    public static final String MODULE_NAME = "业务组信息";
    public static final String URL_PREFIX = "/api/account/group-info";

    @Autowired
    private GroupInfoService groupInfoService;


    /**
     * GET /list : get GroupInfo list
     *
     * @param pagination to paging
     * @param query params
     * @return the ResponseEntity with status 200 (OK) and with body the GroupInfo list
     */
    @Command(MODULE_NAME + " list")
    @RequestMapping(value = LIST_URL, method = RequestMethod.GET)
    public ResponseEntity list(Pagination pagination, Query query) {

        List<GroupInfo> groupInfoList
                = groupInfoService.getList(pagination, query.getFilter());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pagination, URL_PREFIX);
        return new ResponseEntity<>(groupInfoList, headers, HttpStatus.OK);
    }

    /**
    * PUT /edit : Updates an existing GroupInfo.
    *
    * @param groupInfo the user to update
    * @return the ResponseEntity with status 200 (OK) and with body the updated user
    */
    @Command(MODULE_NAME + " edit")
    @RequestMapping(value = EDIT_URL, method = RequestMethod.PUT)
    public ResponseEntity<Integer> update(@Valid @RequestBody GroupInfo groupInfo)
             throws ServiceProcessException {
        log.debug("REST request to update User : {}", groupInfo);
        // GroupInfo existingGroupInfo =
        //        groupInfoService.getByUsername(groupInfo.getUsername());

        // if (existingGroupInfo != null &&
        //      (!existingGroupInfo.getUsername().equals(groupInfo.getUsername()))) {
        //     throw new LoginAlreadyUsedException();
        // }
        int res = groupInfoService.update(groupInfo);

        return ResponseUtil.wrapOrNotFound(res,
            HeaderUtil.createEntityUpdateAlert(groupInfo.getId().toString()));
    }

    /**
    * POST /new : Creates a new GroupInfo.
    *
    * @param groupInfo the groupInfo to create
    * @return the ResponseEntity with status 200 (OK) and with body the created groupInfo
    * @throws ServiceProcessException 业务处理异常
    * @throws URISyntaxException url syntax Exception
    */
    @Command(MODULE_NAME + " new")
    @RequestMapping(value = NEW_URL, method = RequestMethod.POST)
    public ResponseEntity<GroupInfo> newUser(@Valid @RequestBody GroupInfo groupInfo)
        throws ServiceProcessException, URISyntaxException {
        if (groupInfo.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "groupInfo", "idexists");
            // Lowercase the user login before comparing with database
        // } else if (groupInfoService.getByUsername(groupInfo.getUsername()) != null) {
        //    throw new LoginAlreadyUsedException();
        } else {
            Integer res = groupInfoService.insert(groupInfo);
            return ResponseEntity.created(new URI(URL_PREFIX + "group-info/detail?id=" + groupInfo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(groupInfo.getId().toString()))
            .body(groupInfo);
        }
    }

    /**
    * GET /detail?id={id} : get the "id" groupInfo.
    *
    * @param id the id of the groupInfo to find
    * @return the ResponseEntity with status 200 (OK) and with body the "id" groupInfo, or with status 404 (Not Found)
    */
    @Command(MODULE_NAME + " detail")
    @RequestMapping(value = DETAIL_URL, method = RequestMethod.GET)
    public ResponseEntity<GroupInfo> get(@RequestParam Long id) {
        return ResponseUtil.wrapOrNotFound(Optional.of(groupInfoService.get(id)));
    }

    /**
    * DELETE /delete?ids={ids} : delete id in the "ids" GroupInfo.
    *
    * @param ids id in the ids of the user to delete
    * @return the ResponseEntity with status 200 (OK)
    */
    @Command(MODULE_NAME + " delete")
    @RequestMapping(value = DELETE_URL, method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@RequestParam String ids) throws ServiceProcessException {
        groupInfoService.batchDelete(LongTools.parseList(ids));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ids)).build();
    }


}
