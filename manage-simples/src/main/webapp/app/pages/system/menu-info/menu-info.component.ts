import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ServerDataSource} from '../../../@theme/ng2-smart-table';
import {ActivatedRoute, Router} from '@angular/router';
import {NgbDatepickerI18n} from '@ng-bootstrap/ng-bootstrap';
import {CustomDatepickerI18n} from '../../../@theme/components/datepicker/datepicker-i18n';
import {Principal} from '../../../@core/auth/principal.service';
import {StateStorageService} from '../../../@core/auth/state-storage.service';
import {MenuInfoService} from './menu-info.service';
import {AbstractManageListComponent} from '../../../shared/manage/abstract-manage-list.component';


@Component({
    selector: 'ngx-menu-info',
    templateUrl: './menu-info.component.html',
    styleUrls: ['./menu-info.component.scss'],
//    providers: [{provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}]
})
export class MenuInfoComponent extends AbstractManageListComponent {

    manageConfig = {
        endPoint : '/api/system/menu-info',
        columns : {
            id: {
                title: '条目id',
                type: 'string'
            },
            parentId: {
                title: '上级id',
                type: 'string'
            },
            code: {
                title: '条目编码',
                type: 'string'
            },
            value: {
                title: '条目名称',
                type: 'string'
            },
            orderNumber: {
                title: '同级显示顺序',
                type: 'string'
            },
            pkgName: {
                title: '包名',
                type: 'string'
            },
            path: {
                title: '访问路径',
                type: 'string'
            },
            enable: {
                title: '是否启用',
                type: 'string'
            },
            discard: {
                title: '是否舍弃',
                type: 'string'
            },
        }
    };


    constructor(http: HttpClient,
                principal: Principal,
                protected route: ActivatedRoute,
                protected router: Router,
                private menuInfoService: MenuInfoService,
                protected stateStorageService: StateStorageService) {
        super(http, principal, menuInfoService, route, router, stateStorageService);
        this.init();
    }


    setQueryFilters() {
        this.queryFilter['pkgName'] = null;
    }
}
