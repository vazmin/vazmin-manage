import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ServerDataSource} from '../../../@theme/ng2-smart-table';
import {ActivatedRoute, Router} from '@angular/router';
import {NgbDatepickerI18n} from '@ng-bootstrap/ng-bootstrap';
import {CustomDatepickerI18n} from '../../../@theme/components/datepicker/datepicker-i18n';
import {Principal} from '../../../@core/auth/principal.service';
import {StateStorageService} from '../../../@core/auth/state-storage.service';
import {SystemMessageService} from './system-message.service';
import {getLongDateTimeViewOption} from '../../helpers';
import {AbstractManageListComponent} from '../../../shared/manage/abstract-manage-list.component';


@Component({
    selector: 'ngx-system-message',
    templateUrl: './system-message.component.html',
    styleUrls: ['./system-message.component.scss'],
//    providers: [{provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}]
})
export class SystemMessageComponent extends AbstractManageListComponent {

    manageConfig = {
        endPoint : '/api/system/system-message',
        columns : {
            id: {
                title: '记录id',
                type: 'string'
            },
            title: {
                title: '消息标题',
                type: 'string'
            },
            // message: {
            //     title: '消息内容',
            //     type: 'string'
            // },
            messageLevel: {
                title: '消息级别',
                type: 'string'
            },
            messageModule: {
                title: '消息所在模块（类）',
                type: 'string'
            },
            messageLine: {
                title: '消息所在位置',
                type: 'string'
            },
            messageTime: getLongDateTimeViewOption('消息时间'),
        }
    };


    constructor(http: HttpClient,
                principal: Principal,
                protected route: ActivatedRoute,
                protected router: Router,
                private systemMessageService: SystemMessageService,
                protected stateStorageService: StateStorageService) {
        super(http, principal, systemMessageService, route, router, stateStorageService);
        this.settings.selectMode = '';
        this.init();
    }

    setQueryFilters() {
        this.queryFilter['title'] = null;
    }
}
