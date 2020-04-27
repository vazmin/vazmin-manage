import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AuthComponent} from 'app/shared/auth/auth.component';
import {AuthBlockComponent} from 'app/shared/auth/auth-block/auth-block.component';
import {LoginComponent} from 'app/shared/auth/login/login.component';
import {LoginService} from 'app/shared/auth/login/login.service';
import {AuthServerProvider} from 'app/core/auth/auth-jwt.service';
import {StateStorageService} from 'app/core/auth/state-storage.service';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatTooltipModule} from '@angular/material/tooltip';
import {ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {NgxWebstorageModule} from 'ngx-webstorage';
import {AccountService} from 'app/core/auth/account.service';
import {AuthInterceptor} from 'app/blocks/interceptor/auth.interceptor';
import {HTTP_INTERCEPTORS} from '@angular/common/http';



@NgModule({
  declarations: [

  ],
  imports: [
    CommonModule,
    NgxWebstorageModule.forRoot({ prefix: 'app', separator: '-' }),

  ],
  providers: [
    LoginService,
    AuthServerProvider,
    StateStorageService,
    AccountService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
  ]
})
export class CoreModule { }
