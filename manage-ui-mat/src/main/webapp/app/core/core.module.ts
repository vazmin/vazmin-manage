import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LoginService} from 'app/shared/auth/login/login.service';
import {AuthServerProvider} from 'app/core/auth/auth-jwt.service';
import {StateStorageService} from 'app/core/auth/state-storage.service';
import {NgxWebstorageModule} from 'ngx-webstorage';
import {AccountService} from 'app/core/auth/account.service';
import {AuthInterceptor} from 'app/blocks/interceptor/auth.interceptor';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {LocationService} from 'app/shared/location.service';
import {NavigationService} from 'app/components/navigation/navigation.service';
import {ScrollService} from 'app/shared/scroll.service';
import {Logger} from 'app/shared/logger.service';



@NgModule({
  declarations: [

  ],
  imports: [
    CommonModule,
    NgxWebstorageModule.forRoot({prefix: 'app', separator: '-'}),

  ],
  providers: [
    LocationService,
    ScrollService,
    Logger,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
  ]
})
export class CoreModule { }
