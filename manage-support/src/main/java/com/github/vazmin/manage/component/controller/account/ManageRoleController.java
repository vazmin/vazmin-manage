package com.github.vazmin.manage.component.controller.account;

import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.core.util.tools.LongTools;
import com.github.vazmin.framework.web.support.annotation.Command;
import com.github.vazmin.framework.web.support.annotation.Module;
import com.github.vazmin.manage.component.controller.ManageAuthorizeControllerInterface;
import com.github.vazmin.manage.component.controller.ManageControllerInterface;
import com.github.vazmin.manage.component.model.NgxTreeItem;
import com.github.vazmin.manage.component.model.users.ManageRole;
import com.github.vazmin.manage.component.service.users.ManageRoleService;
import com.github.vazmin.manage.component.service.users.RolePrivilegeService;

import com.github.vazmin.manage.component.controller.errors.BadRequestAlertException;
import com.github.vazmin.manage.component.vm.UserPrivilegeVM;
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
 * 平台角色信息 管理控制器类。
 * Created by wangzhiming on 10/8/18.
 */
@Module(value = ManageRoleController.MODULE_NAME, order = 2)
@RestController
@RequestMapping(ManageRoleController.URL_PREFIX)
public class ManageRoleController implements ManageControllerInterface, ManageAuthorizeControllerInterface {
    private static final Logger log = LoggerFactory.getLogger(ManageRoleController.class);

    public static final String MODULE_NAME = "平台角色信息";
    public static final String URL_PREFIX = "/api/account/role";

    @Autowired
    private ManageRoleService manageRoleService;

    @Autowired
    private RolePrivilegeService rolePrivilegeService;

    /**
     * GET /list : get ManageRole list
     *
     * @param pagination to paging
     * @param query params
     * @return the ResponseEntity with status 200 (OK) and with body the ManageRole list
     */
    @Command(MODULE_NAME + " list")
    @RequestMapping(value = LIST_URL, method = RequestMethod.GET)
    public ResponseEntity list(Pagination pagination, Query query) {

        List<ManageRole> manageRoleList
                = manageRoleService.getList(pagination, query.getFilter());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pagination, URL_PREFIX);
        return new ResponseEntity<>(manageRoleList, headers, HttpStatus.OK);
    }

    /**
    * PUT /edit : Updates an existing ManageRole.
    *
    * @param manageRole the user to update
    * @return the ResponseEntity with status 200 (OK) and with body the updated user
    */
    @Command(MODULE_NAME + " edit")
    @RequestMapping(value = EDIT_URL, method = RequestMethod.PUT)
    public ResponseEntity<Integer> update(@Valid @RequestBody ManageRole manageRole)
             throws ServiceProcessException {
        log.debug("REST request to update User : {}", manageRole);
        // ManageRole existingManageRole =
        //        manageRoleService.getByUsername(manageRole.getUsername());

        // if (existingManageRole != null &&
        //      (!existingManageRole.getUsername().equals(manageRole.getUsername()))) {
        //     throw new LoginAlreadyUsedException();
        // }
        int res = manageRoleService.update(manageRole);

        return ResponseUtil.wrapOrNotFound(res,
            HeaderUtil.createEntityUpdateAlert(manageRole.getId().toString()));
    }

    /**
    * POST /new : Creates a new ManageRole.
    *
    * @param manageRole the manageRole to create
    * @return the ResponseEntity with status 200 (OK) and with body the created manageRole
    * @throws ServiceProcessException 业务处理异常
    * @throws URISyntaxException url syntax Exception
    */
    @Command(MODULE_NAME + " new")
    @RequestMapping(value = NEW_URL, method = RequestMethod.POST)
    public ResponseEntity<ManageRole> newUser(@Valid @RequestBody ManageRole manageRole)
        throws ServiceProcessException, URISyntaxException {
        if (manageRole.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "manageRole", "idexists");
            // Lowercase the user login before comparing with database
        // } else if (manageRoleService.getByUsername(manageRole.getUsername()) != null) {
        //    throw new LoginAlreadyUsedException();
        } else {
            Integer res = manageRoleService.insert(manageRole);
            return ResponseEntity.created(new URI(URL_PREFIX + "manage-role/detail?id=" + manageRole.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(manageRole.getId().toString()))
            .body(manageRole);
        }
    }

    /**
    * GET /detail?id={id} : get the "id" manageRole.
    *
    * @param id the id of the manageRole to find
    * @return the ResponseEntity with status 200 (OK) and with body the "id" manageRole, or with status 404 (Not Found)
    */
    @Command(MODULE_NAME + " detail")
    @RequestMapping(value = DETAIL_URL, method = RequestMethod.GET)
    public ResponseEntity<ManageRole> get(@RequestParam Long id) {
        return ResponseUtil.wrapOrNotFound(Optional.of(manageRoleService.get(id)));
    }

    /**
    * DELETE /delete?ids={ids} : delete id in the "ids" ManageRole.
    *
    * @param ids id in the ids of the user to delete
    * @return the ResponseEntity with status 200 (OK)
    */
    @Command(MODULE_NAME + " delete")
    @RequestMapping(value = DELETE_URL, method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@RequestParam String ids) throws ServiceProcessException {
        manageRoleService.batchDelete(LongTools.parseList(ids));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ids)).build();
    }

    /**
     * GET /authorize?id={id} : 获取用户权限列表
     *
     * @param id 用户id
     * @return List<NgxTreeItem> with status 200 (OK)
     */
    @Command(MODULE_NAME + " 权限配置")
    @RequestMapping(value = AUTHORIZE_URL, method = RequestMethod.GET)
    public ResponseEntity<List<NgxTreeItem>> authorize(@RequestParam Long id)
            throws ServiceProcessException {
        List<NgxTreeItem> ngxTreeItemList = rolePrivilegeService.getNgxPrivilegeTree(id);
        return new ResponseEntity<>(ngxTreeItemList, HttpStatus.OK);
    }

    @Command(MODULE_NAME + " 更改权限配置")
    @RequestMapping(value = SAVE_AUTHORIZE_URL, method = RequestMethod.POST)
    public ResponseEntity<Void> saveAuthorize(@RequestBody UserPrivilegeVM userPrivilegeVM) throws ServiceProcessException {
        rolePrivilegeService.save(userPrivilegeVM.getId(), userPrivilegeVM.getPrivilege());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(userPrivilegeVM.getId().toString())).build();
    }

}
