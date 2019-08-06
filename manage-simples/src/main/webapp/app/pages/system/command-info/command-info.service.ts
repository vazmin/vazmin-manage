import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AbstractManageService} from '../../../shared/manage/abstract-manage.service';

@Injectable()
export class CommandInfoService extends AbstractManageService {

    constructor(protected http: HttpClient) {
        super(http);
        this.CREATE_URL = '/api/system/command-info/new';
        this.DETAIL_URL = '/api/system/command-info/detail';
    }

}
