import {Observable} from 'rxjs';

/**
 * 管理平台增删改查 service
 */
export interface ManageServiceInterface {
    CREATE_URL: string;
    UPDATE_URL: string;
    DETAIL_URL: string;
    DELETE_URL: string;

    create(model: any): Observable<any>;
    update(model: any): Observable<any>;
    detail(id: number): Observable<any>;
    delete(ids: any): Observable<any>;

}
