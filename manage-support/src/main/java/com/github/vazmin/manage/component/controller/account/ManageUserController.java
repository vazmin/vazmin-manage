package com.github.vazmin.manage.component.controller.account;

import com.github.vazmin.framework.core.service.Pagination;
import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.core.util.GsonUtils;
import com.github.vazmin.framework.core.util.tools.LongTools;
import com.github.vazmin.framework.web.support.annotation.Command;
import com.github.vazmin.framework.web.support.annotation.Module;
import com.github.vazmin.manage.component.controller.ManageAuthorizeControllerInterface;
import com.github.vazmin.manage.component.controller.ManageControllerInterface;
import com.github.vazmin.manage.component.model.users.ManageUser;
import com.github.vazmin.manage.component.service.users.ManageUserService;
import com.github.vazmin.manage.component.service.users.UserPrivilegeService;
import com.github.vazmin.manage.component.model.NgxTreeItem;
import com.github.vazmin.manage.component.controller.errors.BadRequestAlertException;
import com.github.vazmin.manage.component.vm.KeyAndPasswordVM;
import com.github.vazmin.manage.component.vm.UserPrivilegeVM;
import com.github.vazmin.manage.support.query.Query;
import com.github.vazmin.manage.support.security.ManageUserDetails;
import com.github.vazmin.manage.support.util.HeaderUtil;
import com.github.vazmin.manage.support.util.PaginationUtil;
import com.github.vazmin.manage.support.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


/**
 * 平台用户信息 管理控制器类。
 * Created by wangzhiming on 9/28/18.
 */
@Module(value = ManageUserController.MODULE_NAME, order = 1)
@RestController
@RequestMapping(ManageUserController.URL_PREFIX)
public class ManageUserController implements ManageControllerInterface, ManageAuthorizeControllerInterface {
    private static final Logger log = LoggerFactory.getLogger(ManageUserController.class);

    public static final String MODULE_NAME = "平台用户信息";
    public static final String URL_PREFIX = "/api/account/user";

    @Autowired
    private ManageUserService manageUserService;

    @Autowired
    private UserPrivilegeService userPrivilegeService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * GET /list : get ManageUser list
     *
     * @param pagination to paging
     * @param query params
     * @return the ResponseEntity with status 200 (OK) and with body the ManageUser list
     */
    @Command(MODULE_NAME + " list")
    @GetMapping(value = "")
    public ResponseEntity<List<ManageUser>> list(Pagination pagination, Query query) {

        List<ManageUser> manageUserList
                = manageUserService.getList(pagination, query.getFilter());
        manageUserList.forEach(manageUser -> manageUser.setPassword(""));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pagination, URL_PREFIX);
        return new ResponseEntity<>(manageUserList, headers, HttpStatus.OK);
    }

    /**
    * PUT /edit : Updates an existing ManageUser.
    *
    * @param manageUser the user to update
    * @return the ResponseEntity with status 200 (OK) and with body the updated user
    */
    @Command(MODULE_NAME + " edit")
    @RequestMapping(value = EDIT_URL, method = RequestMethod.PUT)
    public ResponseEntity<Integer> update(@RequestBody ManageUser manageUser)
             throws ServiceProcessException {
        log.debug("REST request to update User : {}", GsonUtils.toJson(manageUser));
        if (StringUtils.isNotBlank(manageUser.getPassword())) {
            manageUser.setPassword(passwordEncoder.encode(manageUser.getPassword()));
        }
        int res = manageUserService.save(manageUser);

        return ResponseUtil.wrapOrNotFound(res,
            HeaderUtil.createEntityUpdateAlert(manageUser.getId().toString()));
    }

    /**
    * POST /new : Creates a new ManageUser.
    *
    * @param manageUser the manageUser to create
    * @return the ResponseEntity with status 200 (OK) and with body the created manageUser
    * @throws ServiceProcessException 业务处理异常
    * @throws URISyntaxException url syntax Exception
    */
    @Command(MODULE_NAME + " new")
    @RequestMapping(value = NEW_URL, method = RequestMethod.POST)
    public ResponseEntity<ManageUser> newUser(@RequestBody ManageUser manageUser)
        throws ServiceProcessException, URISyntaxException {
        if (manageUser.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "manageUser", "idexists");
            // Lowercase the user login before comparing with database
        // } else if (manageUserService.getByUsername(manageUser.getUsername()) != null) {
        //    throw new LoginAlreadyUsedException();
        } else {
            Integer res = manageUserService.save(manageUser);
            return ResponseEntity.created(new URI(URL_PREFIX + "manage-user/detail?id=" + manageUser.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(manageUser.getId().toString()))
            .body(manageUser);
        }
    }

    /**
    * GET /detail?id={id} : get the "id" manageUser.
    *
    * @param id the id of the manageUser to find
    * @return the ResponseEntity with status 200 (OK) and with body the "id" manageUser, or with status 404 (Not Found)
    */
    @Command(MODULE_NAME + " detail")
    @RequestMapping(value = DETAIL_URL, method = RequestMethod.GET)
    public ResponseEntity<ManageUser> get(@RequestParam Long id) {
        Optional<ManageUser> manageUserOptional = Optional.of(manageUserService.getSafetyWithRoles(id));
        manageUserOptional.ifPresent(manageUser -> manageUser.setPassword(""));
        return ResponseUtil.wrapOrNotFound(manageUserOptional);
    }

    /**
    * DELETE /delete?ids={ids} : delete id in the "ids" ManageUser.
    *
    * @param ids id in the ids of the user to delete
    * @return the ResponseEntity with status 200 (OK)
    */
    @Command(MODULE_NAME + " delete")
    @RequestMapping(value = DELETE_URL, method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@RequestParam String ids) throws ServiceProcessException {
        manageUserService.batchDelete(LongTools.parseList(ids));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ids)).build();
    }

    /**
     * 修改密码
     * @param keyAndPassword 新老密码
     * @throws ServiceProcessException
     */
    @ResponseBody
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @Command(value = MODULE_NAME + " 修改密码", common = true)
    public void changePassword(@RequestAttribute ManageUser manageUser, @Valid @RequestBody KeyAndPasswordVM keyAndPassword)
            throws ServiceProcessException {
        ManageUserDetails manageUserDetails =
                (ManageUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (manageUserDetails.getManageUser().isAdmin()) {
            throw new ServiceProcessException("Admin can't change password!");
        }
        ManageUser user = manageUserService.get(manageUserDetails.getManageUser().getId());
        if (user == null) {
            throw new ServiceProcessException("NOT FOUND USER!");
        }
        if (!passwordEncoder.matches(keyAndPassword.getKey(), user.getPassword())) {
            throw new ServiceProcessException("The old password no match!");
        }
        manageUserService.changePassword(user, keyAndPassword.getNewPassword());
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
        List<NgxTreeItem> ngxTreeItemList = userPrivilegeService.getNgxPrivilegeTree(id);
        return new ResponseEntity<>(ngxTreeItemList, HttpStatus.OK);
    }

    @Command(MODULE_NAME + " 更改权限配置")
    @RequestMapping(value = SAVE_AUTHORIZE_URL, method = RequestMethod.POST)
    public ResponseEntity<Void> saveAuthorize(@RequestBody UserPrivilegeVM userPrivilegeVM) throws ServiceProcessException {
        userPrivilegeService.save(userPrivilegeVM.getId(), userPrivilegeVM.getPrivilege());
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(userPrivilegeVM.getId().toString()))
                .build();
    }
}
