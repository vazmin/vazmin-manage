import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class ColdOpenInfoService  {

    constructor(protected http: HttpClient) {

    }

    /**
     * 获取阿里巴巴应用列表
     */
    getAliAppList(): Observable<any> {
        return this.http.get('/api/open/cold/ali-app/list', { observe: 'response'});
    }

    /**
     * 获取项目列表
     */
    getProjectList(): Observable<any> {
        return this.http.get('/api/open/cold/project/list', { observe: 'response'});
    }

}
