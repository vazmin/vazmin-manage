import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    Inject
} from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../../../@core/auth/account.service';
import {catchError, map} from 'rxjs/operators';
import {of as observableOf} from 'rxjs';
import {AuthResult} from '../../../../@core/auth/auth-result';
import {NgxAlertService} from '../../../../shared/service/alert.service';
// import { NB_AUTH_OPTIONS } from '../../auth.options';
// import { getDeepFromObject } from '../../helpers';
//
// import { NbAuthService } from '../../services/auth.service';
// import { NbAuthResult } from '../../services/auth-result';

@Component({
    selector: 'ngx-request-password-page',
    styleUrls: ['./request-password.component.scss'],
    templateUrl: './request-password.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NgxRequestPasswordComponent {

    redirectDelay: number = 10000;

    submitted = false;
    user: any = {};
    constructor(
        protected alterService: NgxAlertService,
        protected accountService: AccountService,
        protected cd: ChangeDetectorRef,
        protected router: Router) {
    }

    requestPass(): void {
        this.submitted = true;
        this.accountService.requestPassword(this.user.email)
            .subscribe({
                next: () => {
                    this.submitted = false;
                    this.alterService.success('邮件已发送！');
                    setTimeout(() => {
                        return this.router.navigateByUrl('/auth/login');
                    }, this.redirectDelay);
                    this.cd.detectChanges();
                },

                error: () => {
                    this.submitted = false;
                    this.cd.detectChanges();
                }
            });
        //
        // this.service.requestPassword(this.strategy, this.user).subscribe((result: NbAuthResult) => {
        //   this.submitted = false;
        //   if (result.isSuccess()) {
        //     this.messages = result.getMessages();
        //   } else {
        //     this.errors = result.getErrors();
        //   }
        //
        //   const redirect = result.getRedirect();
        //   if (redirect) {
        //     setTimeout(() => {
        //       return this.router.navigateByUrl(redirect);
        //     }, this.redirectDelay);
        //   }
        //   this.cd.detectChanges();
        // });
    }

    // getConfigValue(key: string): any {
    //   return getDeepFromObject(this.options, key, null);
    // }

}
