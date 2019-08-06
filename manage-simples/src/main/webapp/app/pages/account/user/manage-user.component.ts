import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ServerDataSource} from '../../../@theme/ng2-smart-table';
import {ActivatedRoute, Router} from '@angular/router';
import {Principal} from '../../../@core/auth/principal.service';
import {StateStorageService} from '../../../@core/auth/state-storage.service';
import {ManageUserService} from './manage-user.service';
import {
    ToggleIconViewComponent
} from '../../../@theme/ng2-smart-table/custom/toggle-icon-view.component';
import {AbstractManageListComponent} from '../../../shared/manage/abstract-manage-list.component';


@Component({
    selector: 'ngx-manage-user',
    templateUrl: './manage-user.component.html',
    styleUrls: ['./manage-user.component.scss']
})
export class ManageUserComponent extends AbstractManageListComponent {

    manageConfig = {
        endPoint : '/api/account/user',
        columns : {
            id: {
                title: '用户id',
                type: 'string'
            },
            username: {
                title: '系统用户名（邮箱）',
                type: 'string'
            },
            // password: {
            //     title: '登录密码',
            //     type: 'string'
            // },
            name: {
                title: '姓名',
                type: 'string'
            },
            // phone: {
            //     title: '电话',
            //     type: 'string'
            // },
            // email: {
            //     title: '业务邮箱',
            //     type: 'string'
            // },
            // openid: {
            //     title: '微信公众号对应的openid',
            //     type: 'string'
            // },
            // memo: {
            //     title: '备注',
            //     type: 'string'
            // },
            // sendEmail: {
            //     title: '是否发送系统邮件',
            //     type: 'string'
            // },
            security: {
                title: '权限配置',
                type: 'custom',
                class: 'ng2-smart-actions text-center',
                extData: {
                    toggleIcon: ['ion-md-settings'],
                    disable: true,
                    color: 'text-link'
                },
                renderComponent: ToggleIconViewComponent,
                onComponentInitFunction: (instance) => {
                    instance.save.subscribe(row => {
                        this.navigate('./authorize', row.id);
                    });
                }
            },
            status: {
                title: '锁定状态',
                type: 'custom',
                class: 'ng2-smart-actions text-center',
                extData: {
                    toggleIcon: ['ion-md-lock', 'ion-md-unlock'],
                    disable: true
                },
                renderComponent: ToggleIconViewComponent,
                onComponentInitFunction(instance) {
                    instance.save.subscribe(row => {
                        // do something
                    });
                }
            },
            createDateStr: {
                title: '创建时间',
                type: 'string'
            },
            // lastVisitDate: {
            //     title: '最后访问时间',
            //     type: 'string'
            // },
        }
    };


    constructor(http: HttpClient,
                principal: Principal,
                protected route: ActivatedRoute,
                protected router: Router,
                protected manageService: ManageUserService,
                protected stateStorageService: StateStorageService) {
        super(http, principal, manageService, route, router, stateStorageService);
        this.init();
    }

    setQueryFilters() {
        this.queryFilter['username'] = null;
    }

}
