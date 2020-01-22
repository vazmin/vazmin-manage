import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ServerDataSource} from '../../../@theme/ng2-smart-table';
import {ActivatedRoute, Router} from '@angular/router';
import {NgbDatepickerI18n} from '@ng-bootstrap/ng-bootstrap';
import {CustomDatepickerI18n} from '../../../@theme/components/datepicker/datepicker-i18n';
import {Principal} from '../../../@core/auth/principal.service';
import {StateStorageService} from '../../../@core/auth/state-storage.service';
import {ModuleInfoService} from './module-info.service';
import {AbstractManageListComponent} from '../../../shared/manage/abstract-manage-list.component';


@Component({
    selector: 'ngx-module-info',
    templateUrl: './module-info.component.html',
    styleUrls: ['./module-info.component.scss'],
//    providers: [{provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}]
})
export class ModuleInfoComponent extends AbstractManageListComponent {

    manageConfig = {
        endPoint : '/api/system/module-info',
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
                title: '包名和类名',
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
            inletUri: {
                title: '模块入口地址',
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
                private moduleInfoService: ModuleInfoService,
                protected stateStorageService: StateStorageService) {
        super(http, principal, moduleInfoService, route, router, stateStorageService);
        this.init();
    }

    setQueryFilters() {
        this.queryFilter['pkgName'] = null;
    }
}
