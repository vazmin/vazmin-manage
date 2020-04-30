import {NgModule} from '@angular/core';
import {AppSharedLibsModule} from './shared-libs.module';
import {AuthComponent} from 'app/shared/auth/auth.component';
import {AuthBlockComponent} from 'app/shared/auth/auth-block/auth-block.component';
import {LoginComponent} from 'app/shared/auth/login/login.component';
import {RouterModule} from '@angular/router';
import {ReplacePipe} from 'app/shared/pipes/replace.pipe';


@NgModule({
  imports: [
    RouterModule,
    AppSharedLibsModule,
  ],
  declarations: [
    AuthComponent,
    AuthBlockComponent,
    LoginComponent,
    ReplacePipe
  ],
  entryComponents: [],
  exports: [
    AppSharedLibsModule,
    ReplacePipe
  ]
})
export class AppSharedModule {
}
