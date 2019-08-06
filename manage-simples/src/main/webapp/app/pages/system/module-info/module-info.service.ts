import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AbstractManageService} from '../../../shared/manage/abstract-manage.service';

@Injectable()
export class ModuleInfoService extends AbstractManageService {

    constructor(protected http: HttpClient) {
        super(http);
        this.CREATE_URL = '/api/system/module-info/new';
        this.DETAIL_URL = '/api/system/module-info/detail';
    }

}
