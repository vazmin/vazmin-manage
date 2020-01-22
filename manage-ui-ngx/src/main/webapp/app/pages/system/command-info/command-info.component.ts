import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ServerDataSource} from '../../../@theme/ng2-smart-table';
import {ActivatedRoute, Router} from '@angular/router';
import {NgbDatepickerI18n} from '@ng-bootstrap/ng-bootstrap';
import {CustomDatepickerI18n} from '../../../@theme/components/datepicker/datepicker-i18n';
import {Principal} from '../../../@core/auth/principal.service';
import {StateStorageService} from '../../../@core/auth/state-storage.service';
import {CommandInfoService} from './command-info.service';
import {AbstractManageListComponent} from '../../../shared/manage/abstract-manage-list.component';


@Component({
    selector: 'ngx-command-info',
    templateUrl: './command-info.component.html',
    styleUrls: ['./command-info.component.scss'],
//    providers: [{provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}]
})
export class CommandInfoComponent extends AbstractManageListComponent {

  animations: string[] = ['fade', 'flyLeft', 'flyRight', 'slideDown', 'slideUp'];
  animationType = 'fade';

    manageConfig = {
        endPoint : '/api/system/command-info',
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
                title: '包名和方法名',
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
            inlet: {
                title: '是否是模块入口',
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
                private commandInfoService: CommandInfoService,
                protected stateStorageService: StateStorageService) {
        super(http, principal, commandInfoService, route, router, stateStorageService);
        this.init();
    }

    setQueryFilters() {
        this.queryFilter['pkgName'] = null;
    }

}
