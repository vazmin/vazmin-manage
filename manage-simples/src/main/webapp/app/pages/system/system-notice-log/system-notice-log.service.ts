import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AbstractManageService} from '../../../shared/manage/abstract-manage.service';

@Injectable()
export class SystemNoticeLogService extends AbstractManageService {

    constructor(protected http: HttpClient) {
        super(http);
        this.CREATE_URL = '/api/system/system-notice-log/new';
        this.UPDATE_URL = '/api/system/system-notice-log/edit';
        this.DETAIL_URL = '/api/system/system-notice-log/detail';
        this.DELETE_URL = '/api/system/system-notice-log/delete';
    }

}
