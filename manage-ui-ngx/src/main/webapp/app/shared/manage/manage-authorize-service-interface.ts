import {Observable} from 'rxjs';

export interface IManageAuthorizeService {
    authorize(id: number): Observable<any>;
    authorizeSave(id: number, privilege: string): Observable<any>;
}
