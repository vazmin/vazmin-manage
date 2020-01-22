import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router,
    RouterStateSnapshot
} from '@angular/router';
import {Principal} from '../auth/principal.service';
import {StateStorageService} from '../auth/state-storage.service';

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {


    constructor(private principal: Principal,
                private stateStorageService: StateStorageService,
                private router: Router) {
    }


    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {

        const accessIsAuthenticated = route.data['accessIsAuthenticated'];
        // console.log('AuthGuard#canActivate called. isAuthenticated: '
        //     + this.principal.isAuthenticated()
        //     + ';access is authenticated: ' + accessIsAuthenticated
        //     + ';state url: ' + state.url);
        return this.checkLogin(state.url.split('?')[0], accessIsAuthenticated);
    }
    /**
     * 多级路径会调用多次，唯末级才进行验证
     * @param {ActivatedRouteSnapshot} route
     * @param {RouterStateSnapshot} state
     * @returns {Promise<boolean> | boolean}
     */
    canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
        // console.log('AuthGuard#canActivateChild called. isAuthenticated: ' + this.principal.isAuthenticated());
        const accessIsAuthenticated = route.data['accessIsAuthenticated'];
        // return route.firstChild == null ? this.checkLogin(state.url, accessIsAuthenticated) : true;
        return route.firstChild == null ? this.checkLogin(state.url.split('?')[0], accessIsAuthenticated) : true;
    }

    checkLogin(url: string, accessIsAuthenticated: boolean): Promise<boolean> {
        const principal = this.principal;
        return Promise.resolve(principal.identity().then((principalInfo) => {

            // if (!authorities || authorities.length === 0) {
            //     return true;
            // }

            if (principalInfo) {
                if (accessIsAuthenticated) {
                    return true;
                }
                return principal.hasPermission(url).then((res) => {
                    // console.log('hasPermission: ' + res);
                    return res;
                });
                // return true;
            }

            this.stateStorageService.storeUrl(url);
            this.router.navigate(['/auth/login'])
            //     .then(() => {
            //     // only show the login dialog, if the user hasn't logged in yet
            //
            // })
            ;
            return false;
        }));
    }
}
