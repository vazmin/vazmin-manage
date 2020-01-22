import { ModuleWithProviders, NgModule, Optional, SkipSelf } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { NbAuthModule, NbDummyAuthStrategy } from '@nebular/auth';
// import { NbSecurityModule, NbRoleProvider } from '@nebular/security';
// import { of as observableOf } from 'rxjs';

import { throwIfAlreadyLoaded } from './module-import-guard';
import { DataModule } from './data/data.module';
import { AnalyticsService } from './utils/analytics.service';
import {AuthGuard} from './security/auth-guard.service';
import {StateStorageService} from './auth/state-storage.service';
import {Principal} from './auth/principal.service';
import {AuthSessionService} from './auth/auth-session.service';
import {AccountService} from './auth/account.service';
import {SharedCommonModule} from '../shared/shared-common.module';
import {ColdOpenInfoService} from './data/cold-open-info.service';

const AUTH_PROVIDERS = [
    AccountService,
    AuthSessionService,
    Principal,
    StateStorageService,
    AuthGuard
];

const NB_CORE_PROVIDERS = [
    ...AUTH_PROVIDERS,
    ...DataModule.forRoot().providers,
    // AnalyticsService,
    ColdOpenInfoService,
];

@NgModule({
  imports: [
      SharedCommonModule,
  ],
  exports: [
    // NbAuthModule,
  ],
  declarations: [],
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    throwIfAlreadyLoaded(parentModule, 'CoreModule');
  }

  static forRoot(): ModuleWithProviders {
    return <ModuleWithProviders>{
      ngModule: CoreModule,
      providers: [
        ...NB_CORE_PROVIDERS,
      ],
    };
  }
}
