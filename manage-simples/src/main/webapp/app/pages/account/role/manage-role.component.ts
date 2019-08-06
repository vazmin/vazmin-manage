import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ServerDataSource} from '../../../@theme/ng2-smart-table';
import {ActivatedRoute, Router} from '@angular/router';
import {Principal} from '../../../@core/auth/principal.service';
import {StateStorageService} from '../../../@core/auth/state-storage.service';
import {ManageRoleService} from './manage-role.service';
import {TranslateService} from '@ngx-translate/core';
import {
    ToggleIconViewComponent
} from '../../../@theme/ng2-smart-table/custom/toggle-icon-view.component';
import {AbstractManageListComponent} from '../../../shared/manage/abstract-manage-list.component';


@Component({
    selector: 'ngx-manage-role',
    templateUrl: './manage-role.component.html',
    styleUrls: ['./manage-role.component.scss'],
//    providers: [{provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}]
})
export class ManageRoleComponent extends AbstractManageListComponent {

    manageConfig = {
        endPoint : '/api/account/role',
        columns : {
            id: {
                title: this.translateService.instant('manageRole.id'),
                type: 'string'
            },
            name: {
                title: '角色名',
                type: 'string'
            },
            memo: {
                title: '备注',
                type: 'string'
            },
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
                title: '状态',
                type: 'custom',
                class: 'ng2-smart-actions text-center',
                extData: {
                    toggleIcon: ['ion-md-lock', 'ion-md-unlock'],
                    disable: true
                },
                renderComponent: ToggleIconViewComponent,
            },
            manager: {
                title: '是否是管理层',
                type: 'custom',
                class: 'ng2-smart-actions text-center',
                extData: {
                    toggleIcon: ['ion-md-close', 'ion-md-checkmark'],
                    disable: true
                },
                renderComponent: ToggleIconViewComponent,
            },
        }
    };


    constructor(http: HttpClient,
                principal: Principal,
                protected route: ActivatedRoute,
                protected router: Router,
                private manageRoleService: ManageRoleService,
                protected stateStorageService: StateStorageService,
                private translateService: TranslateService) {
        super(http, principal, manageRoleService, route, router, stateStorageService);
        this.init();
    }

}
