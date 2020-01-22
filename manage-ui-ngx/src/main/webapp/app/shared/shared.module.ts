import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { NbLayoutModule, NbCardModule, NbCheckboxModule } from '@nebular/theme';
import {NbAuthBlockComponent, NbAuthModule} from '@nebular/auth';
import {SharedLibsModule} from './shared-libs.module';
import {SharedCommonModule} from './shared-common.module';

@NgModule({
    imports: [
        NbLayoutModule,
        NbCardModule,
        NbCheckboxModule,
        SharedLibsModule,
        SharedCommonModule,
        NbAuthModule
    ],
    declarations: [
    ],
    providers: [
    ],
    exports: [
        SharedCommonModule,
        NbAuthBlockComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SharedModule {
}
