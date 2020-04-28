import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';

import {Observable, of, ReplaySubject, Subject} from 'rxjs';
import {environment} from '../../../environments/environment';
import {catchError, shareReplay, tap} from 'rxjs/operators';
import {SessionStorageService} from 'ngx-webstorage';
import {UserIdentity} from 'app/core/auth/user-identity';
import {Router} from '@angular/router';
import {StateStorageService} from 'app/core/auth/state-storage.service';
import {NavigationNode} from 'app/components/navigation/navigation.model';

@Injectable({ providedIn: 'root' })
export class AccountService {

  private userIdentity: UserIdentity | null = null;
  private authenticationState = new ReplaySubject<UserIdentity | null>(1);
  private accountCache$?: Observable<UserIdentity | null>;

  constructor(
    private stateStorageService: StateStorageService,
    private router: Router,
    private sessionStorage: SessionStorageService,
    private http: HttpClient) {}

  fetch(): Observable<UserIdentity> {
    return this.http.get<any>(environment.SERVER_API_URL + 'api/authenticate');
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

  authenticate(identity: UserIdentity | null): void {
    this.userIdentity = identity;
    this.authenticationState.next(this.userIdentity);
  }

  hasAnyAuthority(authorities: string[] | string): boolean {
    if (!this.userIdentity || !this.userIdentity.userMenu) {
      return false;
    }
    if (!Array.isArray(authorities)) {
      authorities = [authorities];
    }
    return this.userIdentity.authorities.some((authority: string) => authorities.includes(authority));
  }

  identity(force?: boolean): Observable<UserIdentity | null> {
    if (!this.accountCache$ || force || !this.isAuthenticated()) {
      this.accountCache$ = this.fetch().pipe(
        catchError(() => {
          return of(null);
        }),
        tap((account: UserIdentity | null) => {
          this.authenticate(account);

          if (account) {
            account.authorities = this.processAuthorities(account.userMenu);
            this.navigateToStoredUrl();
            console.log(account);
          }
        }),
        shareReplay()
      );
    }
    return this.accountCache$;
  }



  isIdentityResolved(): boolean {
    return this.userIdentity !== undefined;
  }


  processAuthorities(nodes: NavigationNode[]): string[]{
    let authorities: string[] = [];
    for (let i = 0; i < nodes.length; i++) {
      const node = nodes[i];
      if (node.children) {
        authorities = authorities.concat(this.processAuthorities(node.children));
      } else {
        node.url && authorities.push(node.url + '#' + node.method);
      }
    }
    return authorities;
  }

  isAuthenticated(): boolean {
    return this.userIdentity !== null;
  }

  getAuthenticationState(): Observable<UserIdentity | null> {
    return this.authenticationState.asObservable();
  }

  getMenuNodes(): NavigationNode[] {
    return this.userIdentity != null ? this.userIdentity.userMenu : [];
  }

  private navigateToStoredUrl(): void {
    // previousState can be set in the authExpiredInterceptor and in the userRouteAccessService
    // if login is successful, go to stored previousState and clear previousState
    const previousUrl = this.stateStorageService.getUrl();
    if (previousUrl) {
      this.stateStorageService.clearUrl();
      this.router.navigateByUrl(previousUrl);
    }
  }

}
