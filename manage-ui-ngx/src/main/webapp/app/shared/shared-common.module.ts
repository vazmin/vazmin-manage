import {NgModule, LOCALE_ID, Injector} from '@angular/core';
import {Title} from '@angular/platform-browser';
import {DatePipe, registerLocaleData} from '@angular/common';
import locale from '@angular/common/locales/zh-Hans';

import {SharedLibsModule} from './shared-libs.module';
import {FindLanguageFromKeyPipe} from './language/find-language-from-key.pipe';
import {
  TranslateModule
} from '@ngx-translate/core';
import {ILanguageService} from './language/language.service';
import {
  NgxTranslateDirective
} from './language/ngx-translate.directive';
import {LanguageHelper} from './language/language.helper';
import {HasAnyPermissionDirective} from '../@core/auth/has-any-permission.directive';
import {NgxEventManager} from './service/event-manager.service';
import {ToasterService} from 'angular2-toaster';
import {AuthExpiredInterceptor} from '../blocks/interceptor/auth-expired.interceptor';
import {NotificationInterceptor} from '../blocks/interceptor/notification.interceptor';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {ErrorHandlerInterceptor} from '../blocks/interceptor/errorhandler.interceptor';
import {StateStorageService} from '../@core/auth/state-storage.service';
import {NgxAlertComponent} from './alert/alert.component';
import {NgxAlertErrorComponent} from './alert/alert-error.component';
import {NgxAlertService} from './service/alert.service';
import {NgxConfigService} from './config.service';
import {NgxModuleConfig} from './config';
import {HasAllPermissionDirective} from '../@core/auth/has-all-permission.directive';


@NgModule({
    imports: [
        SharedLibsModule,
        TranslateModule,
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        NgxTranslateDirective,
        HasAnyPermissionDirective,
        HasAllPermissionDirective,
        NgxAlertComponent,
        NgxAlertErrorComponent
    ],
    providers: [
        ILanguageService,
        LanguageHelper,
        Title,
        NgxAlertService,
        NgxModuleConfig,
        NgxConfigService,
        {
            provide: LOCALE_ID,
            useValue: 'zh-Hans'
        },
        NgxEventManager,
        ToasterService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [
                StateStorageService,
                Injector
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [
                NgxEventManager
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        }
    ],
    exports: [
        TranslateModule,
        SharedLibsModule,
        FindLanguageFromKeyPipe,
        NgxTranslateDirective,
        HasAnyPermissionDirective,
        HasAllPermissionDirective,
        NgxAlertComponent,
        NgxAlertErrorComponent,
    ]
})
export class SharedCommonModule {
    constructor() {
        registerLocaleData(locale);
    }
}
