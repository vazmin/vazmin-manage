import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import {IManageAuthorizeService} from '../../../shared/manage/manage-authorize-service-interface';
import {AbstractManageService} from '../../../shared/manage/abstract-manage.service';

@Injectable()
export class ManageUserService extends AbstractManageService implements IManageAuthorizeService {

    constructor(protected http: HttpClient) {
        super(http);
        this.CREATE_URL = '/api/account/user/new';
        this.UPDATE_URL = '/api/account/user/edit';
        this.DETAIL_URL = '/api/account/user/detail';
        this.DELETE_URL = '/api/account/user/delete';
    }

    authorize(id: number): Observable<any> {
        return this.http.get('/api/account/user/authorize',
            {params: {id: id.toString()}, observe: 'response'});
    }

    authorizeSave(id: number, privilege: string): Observable<any> {
        return this.http.post('/api/account/user/savePrivilege',
            {id: id.toString(), privilege: privilege}, {observe: 'response'});
    }
}
