import { NgModule } from '@angular/core';
import { JhbarSharedLibsModule } from './shared-libs.module';


@NgModule({
  imports: [JhbarSharedLibsModule],
  declarations: [HasAnyAuthorityDirective],
  entryComponents: [],
  exports: [
    JhbarSharedLibsModule,
    HasAnyAuthorityDirective
  ]
})
export class MatSharedModule {}
