import { Injector } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import {LoginService} from '../../@theme/components/auth/login/login.service';
import {StateStorageService} from '../../@core/auth/state-storage.service';

/**
 * 凭证失效拦截
 */
export class AuthExpiredInterceptor implements HttpInterceptor {

    constructor(
        private stateStorageService: StateStorageService,
        private injector: Injector
    ) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).do((event: HttpEvent<any>) => {}, (err: any) => {
            if (err instanceof HttpErrorResponse) {
                if (err.status === 401 && AuthExpiredInterceptor.ignoreUrls(err.url)) {
                    const destination = this.stateStorageService.getDestinationState();
                    if (destination !== null) {
                        const to = destination.destination;
                        const toParams = destination.params;
                        if (to.name === 'accessdenied') {
                            this.stateStorageService.storePreviousState(to.name, toParams);
                        }
                    } else {
                        this.stateStorageService.storeUrl(err.url);
                    }

                    const loginService: LoginService = this.injector.get(LoginService);
                    loginService.logout();

                }
            }
        });
    }

    static ignoreUrls(url: string): boolean {
        return url && !url.includes('/api/account') && !url.includes('/api/authentication');
    }
}
