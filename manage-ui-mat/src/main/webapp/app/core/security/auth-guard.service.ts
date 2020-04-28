import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router,
  RouterStateSnapshot
} from '@angular/router';
import {StateStorageService} from '../auth/state-storage.service';
import {AccountService} from 'app/core/auth/account.service';
import {HTTP_METHOD} from 'app/app.constants';
import {LoginService} from 'app/shared/auth/login/login.service';
import {Logger} from 'app/shared/logger.service';

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
              private logger: Logger,
              private router: Router) {
  }


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
    const isCommon = route.data['common'];
    const method = route.data['method'];

    this.logger.log('canActivate called',
      ', isAuthenticated: ' + this.accountService.isAuthenticated()
        ,', isCommon: ' + isCommon
        , ', state url: ' + state.url);
    return this.checkLogin(this.processUrl(state.url), method, isCommon);
  }

  /**
   * 唯末级才进行验证
   * @param {ActivatedRouteSnapshot} route
   * @param {RouterStateSnapshot} state
   * @returns {Promise<boolean> | boolean}
   */
  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
    const isCommon = route.data['common'];
    const method = route.data['method'] ? route.data['method'] : HTTP_METHOD.GET;
    this.logger.log('canActivateChild called',
      ', isAuthenticated: ' + this.accountService.isAuthenticated()
      ,', isCommon: ' + isCommon
      , ', state url: ' + state.url);
    if (route.firstChild != null) return true;
    return this.checkLogin(this.processUrl(state.url), '#' + method, isCommon);
  }

  checkLogin(url: string, method: string, isCommon: boolean): Promise<boolean> {
    const urlMethod = url + method;
    const accountService = this.accountService;
    return Promise.resolve(
      this.accountService.identity().toPromise().then((account) => {
        if (account) {
          if (isCommon) {
            return true;
          }
          const allow = accountService.hasAnyAuthority(urlMethod);
          this.logger.log(urlMethod, allow);
          return allow;
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
