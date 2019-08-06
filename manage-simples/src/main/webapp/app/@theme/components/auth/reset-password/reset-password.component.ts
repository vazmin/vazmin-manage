/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    Inject, OnInit
} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountService} from '../../../../@core/auth/account.service';
import {PasswordStrengthMeterService} from './password-strength-meter.service';
import {NgxAlertService} from '../../../../shared/service/alert.service';
// import { NB_AUTH_OPTIONS } from '../../auth.options';
// import { getDeepFromObject } from '../../helpers';
//
// import { NbAuthService } from '../../services/auth.service';
// import { NbAuthResult } from '../../services/auth-result';
interface KeyPassword {
    key: string;
    password: string;
    confirmPassword: string;
}

@Component({
    selector: 'ngx-reset-password-page',
    styleUrls: ['./reset-password.component.scss'],
    templateUrl: './reset-password.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NgxResetPasswordComponent implements OnInit {

    redirectDelay: number = 0;
    showMessages: any = {};

    submitted = false;
    errors: string[] = [];
    messages: string[] = [];
    user: any = {};

    keyPassword: KeyPassword;

    confirmPassword: string = '';

    isReset: boolean = false;

    passwordLength = {
        min: 8,
        max: 20
    };
    strengthScore = 0;
    scoreWithFeedback: any;

    progressBar = {
        score: 0,
        status: 'danger'
    };

    constructor(
        protected passwordStrengthMeterService: PasswordStrengthMeterService,
        protected accountService: AccountService,
        protected alterService: NgxAlertService,
        protected cd: ChangeDetectorRef,
        protected router: Router,
        protected route: ActivatedRoute) {

    }

    savePassword() {
        if (this.isReset) {
            this.accountService.finishPassword(this.keyPassword.key, this.keyPassword.password)
                .subscribe({
                    next: () => {
                        // this.ref.close();
                        this.alterService.success('密码已重置');
                        this.cd.detectChanges();
                    }, error: this.handleError.bind(this)
                });
        } else {
            this.accountService.changePassword(this.keyPassword.key, this.keyPassword.password)
                .subscribe({
                    next: () => {
                        this.alterService.success('密码已更新');
                        this.cd.detectChanges();
                    }, error: this.handleError.bind(this)
                });
        }
    }

    ngOnInit(): void {
        this.keyPassword = {
            key: '',
            password: '',
            confirmPassword: ''
        };
        this.route.queryParams.subscribe((params) => {
            if (params['key']) {
                this.isReset = true;
                this.keyPassword.key = params['key'];
            }
        });
        this.confirmPassword = '';
    }

    checkPassword() {
        this.scoreWithFeedback = this.keyPassword.password ?
            this.passwordStrengthMeterService.scoreWithFeedback(this.keyPassword.password) : {};
        this.strengthScore = this.scoreWithFeedback.score;
        this.setProgressBar(this.strengthScore * 25);
    }

    setProgressBar(score) {
        this.progressBar.score = score;
        this.progressBar.status = this.getPbStatus(score);
    }

    getPbStatus(score) {
        if (score <= 25) {
            return 'danger';
        } else if (score <= 50) {
            return 'warning';
        } else if (score <= 75) {
            return 'info';
        } else {
            return 'success';
        }
    }

    handleError() {
        this.submitted = false;
        this.cd.detectChanges();
    }
}
