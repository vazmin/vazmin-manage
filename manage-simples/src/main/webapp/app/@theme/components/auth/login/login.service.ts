import { Injectable } from '@angular/core';
import {Principal} from '../../../../@core/auth/principal.service';
import {AuthSessionService} from '../../../../@core/auth/auth-session.service';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {AuthResult} from '../../../../@core/auth/auth-result';

@Injectable()
export class LoginService {

    constructor(private principal: Principal,
                private authSessionService: AuthSessionService,
                // private accountService: AccountService,
                // private stateStorageService: StateStorageService,
                private router: Router) {
    }

    login(credentials, callback?) {
        const cb = callback || function () {
        };

        return new Promise((resolve, reject) => {
            this.authSessionService.login(credentials).subscribe((data: AuthResult) => {
                if (data.isSuccess()) {
                    this.principal.identity(true).then((account) => {
                        resolve(data);
                    });
                } else {
                    resolve(data);
                }

                return cb(data);
            }, (err) => {
                // this.logout();
                reject(err);
                return cb(err);
            });
        });
    }

    logout(logoutSubscription?: Subscription) {

        this.authSessionService.logout().subscribe(() => {
            this.principal.authenticate(null);
            this.router.navigate(['/auth/login']).then(() => {
                // do nothing
                if (logoutSubscription) {
                    logoutSubscription.unsubscribe();
                }
            });
        });

    }

}
