import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ServerDataSource} from '../../../@theme/ng2-smart-table';
import {ActivatedRoute, Router} from '@angular/router';
import {NgbDatepickerI18n} from '@ng-bootstrap/ng-bootstrap';
import {CustomDatepickerI18n} from '../../../@theme/components/datepicker/datepicker-i18n';
import {Principal} from '../../../@core/auth/principal.service';
import {StateStorageService} from '../../../@core/auth/state-storage.service';
import {GroupInfoService} from './group-info.service';
import {TranslateService} from '@ngx-translate/core';
import {AbstractManageListComponent} from '../../../shared/manage/abstract-manage-list.component';


@Component({
    selector: 'ngx-group-info',
    templateUrl: './group-info.component.html',
    styleUrls: ['./group-info.component.scss']
})
export class GroupInfoComponent extends AbstractManageListComponent {

    manageConfig = {
        endPoint : '/api/account/group-info',
        columns : {
            id: {
                title: this.translateService.instant('groupInfo.id'),
                type: 'string'
            },
            departmentId: {
                title: this.translateService.instant('groupInfo.departmentId'),
                type: 'string'
            },
            groupName: {
                title: this.translateService.instant('groupInfo.groupName'),
                type: 'string'
            },
            groupMemo: {
                title: this.translateService.instant('groupInfo.groupMemo'),
                type: 'string'
            },
            status: {
                title: this.translateService.instant('groupInfo.status'),
                type: 'string'
            },
            createTime: {
                title: this.translateService.instant('groupInfo.createTime'),
                type: 'string'
            },
        }
    };


    constructor(http: HttpClient,
                principal: Principal,
                protected route: ActivatedRoute,
                protected router: Router,
                private groupInfoService: GroupInfoService,
                protected stateStorageService: StateStorageService,
                private translateService: TranslateService) {
        super(http, principal, groupInfoService, route, router, stateStorageService);
        this.init();
    }

    setQueryFilters() {
        this.queryFilter['groupName'] = null;
    }

}
