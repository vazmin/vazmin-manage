import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';

import { SERVER_API_URL } from 'app/app.constants';
const token = 'authenticationToken';
@Injectable({ providedIn: 'root' })
export class AuthServerProvider {

  constructor(private http: HttpClient, private $localStorage: LocalStorageService, private $sessionStorage: SessionStorageService) {}

  getToken() {
    return this.$localStorage.retrieve(token) || this.$sessionStorage.retrieve(token);
  }

  login(credentials: LoginCredentials): Observable<any> {

    return this.http.post(SERVER_API_URL + 'api/authenticate',
      credentials, { observe: 'response' })
      .pipe(map((resp) => {
        const bearerToken = resp.headers.get('Authorization');
        if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
          const jwt = bearerToken.slice(7, bearerToken.length);
          this.storeAuthenticationToken(jwt, credentials.rememberMe);
          return jwt;
        }
        return '';
      }));
  }

  storeAuthenticationToken(jwt: string, rememberMe: boolean) {
    if (rememberMe) {
      this.$localStorage.store(token, jwt);
    } else {
      this.$sessionStorage.store(token, jwt);
    }
  }

  logout(): Observable<any> {
    return new Observable(observer => {
      this.$localStorage.clear(token);
      this.$sessionStorage.clear(token);
      observer.complete();
    });
  }
}

export interface LoginCredentials {
  username: string,
  password: string,
  rememberMe: boolean
}