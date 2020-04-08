import { NgModule } from '@angular/core';
import { MatSharedLibsModule } from './shared-libs.module';
import {HasAnyPermissionDirective} from 'app/core/auth/has-any-permission.directive';


@NgModule({
  imports: [MatSharedLibsModule],
  declarations: [HasAnyPermissionDirective],
  entryComponents: [],
  exports: [
    MatSharedLibsModule,
    HasAnyPermissionDirective
  ]
})
export class MatSharedModule {}
