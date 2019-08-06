import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AbstractManageService} from '../../shared/manage/abstract-manage.service';
import {Observable} from 'rxjs';

@Injectable()
export class NoticeInfoService extends AbstractManageService {
    READ_URL = '/api/notice-info/read';
    UNREAD_URL = '/api/notice-info/unread';

    constructor(protected http: HttpClient) {
        super(http);
        this.DETAIL_URL = '/api/notice-info/detail';
        this.LIST_URL = '/api/notice-info/list';
    }

    read(data): Observable<any> {
        return Observable.create();
        // return this.http.put(this.READ_URL, data, {observe: 'response'});
    }

    unreadCount(): Observable<any> {
        return this.http.get(this.UNREAD_URL, { observe: 'response'});
    }
}
