import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import {ManageServiceInterface} from './manage-service-interface';

@Injectable()
export class AbstractManageService implements ManageServiceInterface {

    constructor(protected http: HttpClient) {}

    CREATE_URL;
    UPDATE_URL;
    DELETE_URL;
    DETAIL_URL;
    LIST_URL;

    create(data): Observable<any> {
      return this.http.post(this.CREATE_URL, data, {observe: 'response'});
    }

    update(data): Observable<any> {
      return this.http.put(this.UPDATE_URL, data, {observe: 'response'});
    }

    detail(id: number): Observable<any> {
      return this.http.get(this.DETAIL_URL, {params: {id: id.toString()}, observe: 'response'});
    }

    delete(ids): Observable<any> {
        return this.http.delete(this.DELETE_URL, {params: {ids: ids}, observe: 'response'});
    }

    list(params?): Observable<any> {
        return this.http.get(this.LIST_URL, {params: params, observe: 'response'} );
    }

}
