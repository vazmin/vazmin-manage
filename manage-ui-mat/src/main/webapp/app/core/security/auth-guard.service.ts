import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router,
  RouterStateSnapshot
} from '@angular/router';
import {StateStorageService} from '../auth/state-storage.service';
import {AccountService} from 'app/core/auth/account.service';
import {HTTP_METHOD} from 'app/app.constants';

/**
 *
 *
 */
@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate, CanActivateChild {


  constructor(private accountService: AccountService,
              private stateStorageService: StateStorageService,
              private router: Router) {
  }


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
    const isCommon = route.data['common'];
    const method = route.data['method'];
    console.log('AuthGuard#canActivate called. isAuthenticated: '
        + this.accountService.isAuthenticated()
        + ';access is authenticated: ' + isCommon
        + ';state url: ' + state.url);
    return this.checkLogin(this.processUrl(state.url), method, isCommon);
  }

  /**
   * 多级路径会调用多次，唯末级才进行验证
   * @param {ActivatedRouteSnapshot} route
   * @param {RouterStateSnapshot} state
   * @returns {Promise<boolean> | boolean}
   */
  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
    const isCommon = route.data['common'];
    const method = route.data['method'] ? route.data['method'] : HTTP_METHOD.GET;
    console.log('AuthGuard#canActivateChild called. isAuthenticated: '
      + this.accountService.isAuthenticated()
      + '; access is authenticated: ' + isCommon
      + '; state url: ' + state.url
      + '; method: ' + method);
    return route.firstChild == null ?
      this.checkLogin(this.processUrl(state.url), '#' + method, isCommon) : true;
  }

  checkLogin(url: string, method: string, isCommon: boolean): Promise<boolean> {
    // console.log("check url: " + url);
    const urlMethod = url + method;
    const accountService = this.accountService;
    return Promise.resolve(
      this.accountService.identity().toPromise().then((account) => {
        if (account) {
          if (isCommon) {
            return true;
          }
          return accountService.hasAnyAuthority(urlMethod);
        }
        this.stateStorageService.storeUrl(url);
        this.router.navigate(['/auth/login']);
        return false;
      }));
  }

  private processUrl(url: string): string {
    const baseUrl = url.split('?')[0];
    return baseUrl.replace('/pages', '/api');
  }
}
