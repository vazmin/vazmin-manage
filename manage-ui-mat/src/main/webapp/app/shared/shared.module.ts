import {NgModule} from '@angular/core';
import {AppSharedLibsModule} from './shared-libs.module';
// import {HasAnyPermissionDirective} from 'app/core/auth/has-any-permission.directive';
// import {HasAllPermissionDirective} from 'app/core/auth/has-all-permission.directive';


@NgModule({
  imports: [AppSharedLibsModule],
  declarations: [
    // HasAnyPermissionDirective,
    // HasAllPermissionDirective

  ],
  entryComponents: [],
  exports: [
    AppSharedLibsModule,
    // HasAnyPermissionDirective
  ]
})
export class AppSharedModule {
}
