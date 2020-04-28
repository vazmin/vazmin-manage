import {NgModule} from '@angular/core';
import {AppSharedLibsModule} from './shared-libs.module';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatTooltipModule} from '@angular/material/tooltip';
import {AuthComponent} from 'app/shared/auth/auth.component';
import {AuthBlockComponent} from 'app/shared/auth/auth-block/auth-block.component';
import {LoginComponent} from 'app/shared/auth/login/login.component';
import {RouterModule} from '@angular/router';
import {ReplacePipe} from 'app/shared/pipes/replace.pipe';


@NgModule({
  imports: [
    RouterModule,
    AppSharedLibsModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatTooltipModule
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
