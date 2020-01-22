import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AbstractManageService} from '../../../shared/manage/abstract-manage.service';

@Injectable()
export class GroupInfoService extends AbstractManageService {

    constructor(protected http: HttpClient) {
        super(http);
        this.CREATE_URL = '/api/account/group-info/new';
        this.UPDATE_URL = '/api/account/group-info/edit';
        this.DETAIL_URL = '/api/account/group-info/detail';
        this.DELETE_URL = '/api/account/group-info/delete';
    }

}
