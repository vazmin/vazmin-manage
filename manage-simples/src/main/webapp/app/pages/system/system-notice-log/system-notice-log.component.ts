import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ServerDataSource} from '../../../@theme/ng2-smart-table';
import {ActivatedRoute, Router} from '@angular/router';
import {Principal} from '../../../@core/auth/principal.service';
import {StateStorageService} from '../../../@core/auth/state-storage.service';
import {SystemNoticeLogService} from './system-notice-log.service';
import {TranslateService} from '@ngx-translate/core';
import {
    getLongDateTimeViewOption,
    getToggleIconViewOption
} from '../../helpers';
import {AbstractManageListComponent} from '../../../shared/manage/abstract-manage-list.component';


@Component({
    selector: 'ngx-system-notice-log',
    templateUrl: './system-notice-log.component.html',
    styleUrls: ['./system-notice-log.component.scss']
})
export class SystemNoticeLogComponent extends AbstractManageListComponent {

    manageConfig = {
        endPoint : '/api/system/system-notice-log',
        columns : {
            id: {
                title: this.translateService.instant('systemNoticeLog.id'),
                type: 'string'
            },
            username: {
                title: this.translateService.instant('systemNoticeLog.userId'),
                type: 'string'
            },
            noticeTypeDescription: {
                title: this.translateService.instant('systemNoticeLog.noticeType'),
                type: 'string'
            },
            sendModeDescription: {
                title: this.translateService.instant('systemNoticeLog.sendMode'),
                type: 'string'
            },
            title: {
                title: '标题',
                type: 'string'
            },
            // sendContent: {
            //     title: this.translateService.instant('systemNoticeLog.sendContent'),
            //     type: 'string'
            // },
            // errorMsg: {
            //     title: '异常消息',
            //     type: 'string'
            // },
            noticeTime: getLongDateTimeViewOption(
                this.translateService.instant('systemNoticeLog.noticeTime')),
            result: getToggleIconViewOption(
                this.translateService.instant('systemNoticeLog.result'),
                {toggleIcon: ['ion-md-close-circle', 'ion-md-checkmark-circle'], disable: true},
                (row) => {

                }),
        },
        initData: {
            sortConf: [{
                direction: 'desc',
                field: 'noticeTime'
            }],
        }
    };


    constructor(http: HttpClient,
                principal: Principal,
                protected route: ActivatedRoute,
                protected router: Router,
                private systemNoticeLogService: SystemNoticeLogService,
                protected stateStorageService: StateStorageService,
                private translateService: TranslateService) {
        super(http, principal, systemNoticeLogService, route, router, stateStorageService);
        this.settings.selectMode = '';
        this.init();
    }

    setQueryFilters() {
        this.queryFilter['userId'] = null;
    }

}
