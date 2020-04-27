import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';

import {Observable, of, Subject} from 'rxjs';
import {environment} from '../../../environments/environment';
import {catchError, shareReplay, tap} from 'rxjs/operators';
import {SessionStorageService} from 'ngx-webstorage';

@Injectable({ providedIn: 'root' })
export class AccountService {

  private userIdentity: any | null;
  private authenticated = false;
  private authenticationState = new Subject<any>();
  private accountCache$: Observable<any> | null;

  constructor(private sessionStorage: SessionStorageService, private http: HttpClient) {}

  fetch(): Observable<HttpResponse<any>> {
    return this.http.get<any>(environment.SERVER_API_URL + 'api/authenticate', {observe: 'response'});
  }

  save(account: any): Observable<HttpResponse<any>> {
    return this.http.post(environment.SERVER_API_URL + 'api/account', account, {observe: 'response'});
  }

  changePassword(oldPass: string, newPass: string): Observable<HttpResponse<any>> {
    return this.http.post(environment.SERVER_API_URL + 'api/account/user/password',
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

  identity(force?: boolean): Observable<any> {
    if (force || !this.authenticated) {
      this.accountCache$ = null;
    }

    if (!this.accountCache$) {
      this.accountCache$ = this.fetch().pipe(
        catchError(() => {
          return of(null);
        }),
        tap(account => {
          if (account) {
            this.userIdentity = account;
            this.authenticated = true;
          } else {
            this.userIdentity = null;
            this.authenticated = false;
          }
          this.authenticationState.next(this.userIdentity);
        }),
        shareReplay()
      );
    }
    return this.accountCache$;
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }

  isIdentityResolved(): boolean {
    return this.userIdentity !== undefined;
  }

  getAuthenticationState(): Observable<any> {
    return this.authenticationState.asObservable();
  }
}
