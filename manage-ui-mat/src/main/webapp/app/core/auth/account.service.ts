import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable} from 'rxjs';

@Injectable()
export class AccountService {

    constructor(private http: HttpClient) {
    }

    get(): Observable<HttpResponse<Account>> {
        return this.http.get<Account>(SERVER_API_URL + 'api/authenticate', {observe: 'response'});
    }

    save(account: any): Observable<HttpResponse<any>> {
        return this.http.post(SERVER_API_URL + 'api/account', account, {observe: 'response'});
    }

    changePassword(oldPass: string, newPass: string): Observable<HttpResponse<any>> {
        return this.http.post(SERVER_API_URL + 'api/account/user/password',
            {key: oldPass, newPassword: newPass}, {observe: 'response'});
    }

    requestPassword(email: string): Observable<HttpResponse<any>> {
        return this.http.post(SERVER_API_URL + 'api/account/reset-password/init',
            email, {observe: 'response'});
    }

    finishPassword(key: string, newPass: string): Observable<HttpResponse<any>> {
        return this.http.post(SERVER_API_URL + 'api/account/reset-password/finish',
            {key: key, newPassword: newPass}, {observe: 'response'});
    }
}
