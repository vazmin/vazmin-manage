import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {SystemNoticeLogService} from './system-notice-log.service';
import {AbstractManageDetailComponent} from '../../../shared/manage/abstract-manage-detail.component';
import {DateFormat} from '../../../shared/model/base-constants';

@Component({
    selector: 'ngx-system-notice-log-detail',
    templateUrl: './system-notice-log-detail.component.html'
})
export class SystemNoticeLogDetailComponent extends AbstractManageDetailComponent {

    systemNoticeLog: any;

    dateFormat: DateFormat.LONG_TIME;

    constructor(private systemNoticeLogService: SystemNoticeLogService,
                protected route: ActivatedRoute) {
        super(systemNoticeLogService, route);
    }

    setData(data) {
        this.systemNoticeLog = data;
    }

}
