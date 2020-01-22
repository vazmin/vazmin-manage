import {NgModule} from '@angular/core';

import {PagesComponent} from './pages.component';
// import {DashboardModule} from './dashboard/dashboard.module';
// import {ECommerceModule} from './e-commerce/e-commerce.module';
import {PagesRoutingModule} from './pages-routing.module';
import {ThemeModule} from '../@theme/theme.module';
import {MiscellaneousModule} from './miscellaneous/miscellaneous.module';
import {TranslateModule} from '@ngx-translate/core';
import {SharedLibsModule} from '../shared/shared-libs.module';
import {SharedCommonModule} from '../shared/shared-common.module';
// import {TaskInfoModule} from './task-info/task-info.module';

const PAGES_COMPONENTS = [
    PagesComponent,
];

@NgModule({
    imports: [
        PagesRoutingModule,
        ThemeModule,
        // ECommerceModule,
        MiscellaneousModule,
        SharedCommonModule,
        SharedLibsModule,
        TranslateModule,
    ],
    declarations: [
        ...PAGES_COMPONENTS,
    ],
})
export class PagesModule {
}
