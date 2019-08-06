import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {environment} from '../../../environments/environment';


@Injectable()
export class AccountService {

    constructor(private http: HttpClient) {
    }

    get(): Observable<HttpResponse<Account>> {
        return this.http.get<Account>(environment.SERVER_API_URL + 'api/authenticate', {observe: 'response'});
    }

    save(account: any): Observable<HttpResponse<any>> {
        return this.http.post(environment.SERVER_API_URL + 'api/account', account, {observe: 'response'});
    }

    changePassword(oldPass: string, newPass: string): Observable<HttpResponse<any>> {
        return this.http.post(environment.SERVER_API_URL + 'api/account/password',
            {key: oldPass, newPassword: newPass}, {observe: 'response'});
    }

    requestPassword(email: string): Observable<HttpResponse<any>> {
        return this.http.post(environment.SERVER_API_URL + 'api/account/reset-password/init',
            email, {observe: 'response'});
    }

    finishPassword(key: string, newPass: string): Observable<HttpResponse<any>> {
        return this.http.post(environment.SERVER_API_URL + 'api/account/reset-password/finish',
            {key: key, newPassword: newPass}, {observe: 'response'});
    }
}
