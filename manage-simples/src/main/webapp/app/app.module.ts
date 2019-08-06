/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import {APP_BASE_HREF} from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {CoreModule} from './@core/core.module';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {ThemeModule} from './@theme/theme.module';
import {Ng2Webstorage} from 'ngx-webstorage';
import {
    MissingTranslationHandler,
    TranslateLoader,
    TranslateModule,
} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {JhiMissingTranslationHandler} from './shared/language/jhi-missing-translation.config';
import {SharedModule} from './shared/shared.module';
import {
    NbDatepickerModule,
    NbDialogModule,
    NbToastrModule
} from '@nebular/theme';
import {RouteReuseStrategy} from '@angular/router';
import {CustomRouteReuseStrategy} from './shared/service/custom-route-reuse-strategy';
import {ServiceWorkerModule} from '@angular/service-worker';
import {environment} from '../environments/environment';

export function translatePartialLoader(http: HttpClient) {
    return new TranslateHttpLoader(http, '/assets/i18n/', '.json');
}

export function missingTranslationHandler() {
    return new JhiMissingTranslationHandler();
}

@NgModule({
    declarations: [AppComponent],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        AppRoutingModule,
        NbDialogModule.forRoot(),
        Ng2Webstorage.forRoot({prefix: 'ngx', separator: '-'}),
        // SharedRoutingModule,
        SharedModule,
        ThemeModule.forRoot(),
        NbToastrModule.forRoot(),
        CoreModule.forRoot(),
        // NgxTreeModule.forRoot(),
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: (translatePartialLoader),
                deps: [HttpClient],
            },
            missingTranslationHandler: {
                provide: MissingTranslationHandler,
                useFactory: missingTranslationHandler,
                deps: [],
            },
        }),
        NbDatepickerModule.forRoot(),
        ServiceWorkerModule.register('/ngsw-worker.js', { enabled: environment.production })
    ],
    bootstrap: [AppComponent],
    providers: [
        {provide: APP_BASE_HREF, useValue: '/'},
        {provide: RouteReuseStrategy, useClass: CustomRouteReuseStrategy},
    ],
})
export class AppModule {
}
