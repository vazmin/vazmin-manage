import {ModuleWithProviders, NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {NgSelectModule} from '@ng-select/ng-select';

import {
    NbActionsModule,
    NbCardModule,
    NbLayoutModule,
    NbMenuModule,
    NbRouteTabsetModule,
    NbSearchModule,
    NbSidebarModule,
    NbTabsetModule,
    NbThemeModule,
    NbUserModule,
    NbCheckboxModule,
    NbPopoverModule,
    NbContextMenuModule,
    NbProgressBarModule,
    NbInputModule,
    NbDatepickerModule,
    NbListModule,
    NbDialogModule,
    NbSpinnerModule,
    NbAccordionModule,
    NbAlertModule,
    NbToastrService,
    NbToastrModule,
    NbTooltipModule,

} from '@nebular/theme';

import {
    FooterComponent,
    HeaderComponent,
    SearchInputComponent,
    SwitcherComponent,
    LayoutDirectionSwitcherComponent,
    NgxAuthComponent,
    NgxAuthBlockComponent,
    NgxLoginComponent, NgxDialogSmartTableComponent,
    StatusCardComponent,
    ProjectConfigComponent,
} from './components';
import {
    CapitalizePipe,
    PluralPipe,
    RoundPipe,
    TimingPipe,
    NumberWithCommasPipe,
} from './pipes';
import {
    OneColumnLayoutComponent,
    SampleLayoutComponent,
} from './layouts';
import {DEFAULT_THEME} from './styles/theme.default';
import {LoginService} from './components/auth/login/login.service';
import {AuthRoutingModule} from './auth-routing.module';
import {SharedCommonModule} from '../shared/shared-common.module';
import {GetDescriptionByValuePipe} from './pipes/get-description-by-value.pipe';
import {NzBreadCrumbModule} from './components/breadcrumb/nz-breadcrumb.module';
import {NoticeInfiniteListComponent} from './components/notices/notice-infinite-list.component';
import {Ng2SmartTableModule} from './ng2-smart-table';
import {ALinkViewComponent} from './ng2-smart-table/custom/a-link-view.component';
import {ParseDatePipe} from './pipes/parse-date.pipe';
import {FloorPipe} from './pipes/floor.pipe';
import {GetShortDateTimePipe} from './pipes/get-short-date-time.pipe';
import {NgxRequestPasswordComponent} from './components/auth/request-password/request-password.component';
import {NgxResetPasswordComponent} from './components/auth/reset-password/reset-password.component';
import {PasswordStrengthMeterService} from './components/auth/reset-password/password-strength-meter.service';
import {InletComponent} from './components/inlet.component';

const BASE_MODULES = [ReactiveFormsModule, SharedCommonModule];

const NB_MODULES = [
    NbCardModule,
    NbLayoutModule,
    NbTabsetModule,
    NbRouteTabsetModule,
    NbMenuModule,
    NbUserModule,
    NbActionsModule,
    NbSearchModule,
    NbSidebarModule,
    NbCheckboxModule,
    NbPopoverModule,
    NbContextMenuModule,
    NbInputModule,
    NbSpinnerModule,
    NgbModule,
    // NbSecurityModule, // *nbIsGranted directive,
    NbProgressBarModule,
    AuthRoutingModule,
    NgSelectModule,
    NzBreadCrumbModule,
    NbDatepickerModule,
    NbListModule,
    Ng2SmartTableModule,
    NbAccordionModule,
    NbAlertModule,
    NbTooltipModule
];

const COMPONENTS = [
    ProjectConfigComponent,
    StatusCardComponent,
    SwitcherComponent,
    LayoutDirectionSwitcherComponent,
    HeaderComponent,
    FooterComponent,
    SearchInputComponent,
    OneColumnLayoutComponent,
    SampleLayoutComponent,
    NoticeInfiniteListComponent,
    // NgxDialogTableComponent,
    NgxDialogSmartTableComponent,
    ALinkViewComponent,
    InletComponent
    // NgxDynamicTableComponent,
    // NgxTableComponent
];

const ENTRY_COMPONENTS = [
    // ThemeSwitcherListComponent,
    NgxDialogSmartTableComponent,
    ALinkViewComponent,
];

const PIPES = [
    CapitalizePipe,
    PluralPipe,
    RoundPipe,
    FloorPipe,
    TimingPipe,
    NumberWithCommasPipe,
    GetDescriptionByValuePipe,
    ParseDatePipe,
    GetShortDateTimePipe
];

const NB_THEME_PROVIDERS = [
    ...NbThemeModule.forRoot(
        {
            name: 'default',
        },
        [DEFAULT_THEME],
    ).providers,
    ...NbSidebarModule.forRoot().providers,
    ...NbMenuModule.forRoot().providers,
    LoginService,
    PasswordStrengthMeterService
];

const NGX_AUTH_COMPONENTS = [
    NgxAuthComponent,
    NgxAuthBlockComponent,
    NgxLoginComponent,
    NgxRequestPasswordComponent,
    NgxResetPasswordComponent,
];


@NgModule({
    imports: [...BASE_MODULES, ...NB_MODULES],
    exports: [...BASE_MODULES, ...NB_MODULES, ...COMPONENTS, ...PIPES, ...NGX_AUTH_COMPONENTS],
    declarations: [...COMPONENTS, ...PIPES, ...NGX_AUTH_COMPONENTS],
    entryComponents: [...ENTRY_COMPONENTS],
    providers: []
})
export class ThemeModule {
    static forRoot(): ModuleWithProviders {
        return <ModuleWithProviders>{
            ngModule: ThemeModule,
            providers: [...NB_THEME_PROVIDERS],
        };
    }
}
