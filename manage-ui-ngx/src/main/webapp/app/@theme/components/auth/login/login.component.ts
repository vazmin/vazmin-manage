/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from './login.service';
import {AuthResult} from '../../../../@core/auth/auth-result';


@Component({
    selector: 'ngx-login',
    templateUrl: './login.component.html',
})
export class NgxLoginComponent {

    redirectDelay: number = 0;
    provider: string = '';

    errors: string[] = [];
    messages: string[] = [];
    user: any = {};
    submitted: boolean = false;
    passwordLength: any = {};

    constructor(protected service: LoginService,
                protected router: Router) {
        this.passwordLength = {
            max: 20,
            min: 8,
        };
    }

    login(): void {
        this.errors = this.messages = [];
        this.submitted = true;

        this.service.login(this.user)
            .then((result: AuthResult) => {
                this.submitted = false;
                console.log('...');
                if (result.isSuccess()) {
                    this.messages = result.getMessages();
                } else {
                    this.errors = result.getErrors();
                }
                const redirect = result.getRedirect();
                if (redirect) {
                    setTimeout(() => {
                        return this.router.navigateByUrl(redirect);
                    }, this.redirectDelay);
                }

            });
    }

}
