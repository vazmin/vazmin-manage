import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {CellModule} from './components/cell/cell.module';
import {FilterModule} from './components/filter/filter.module';
import {PagerModule} from './components/pager/pager.module';
import {TBodyModule} from './components/tbody/tbody.module';
import {THeadModule} from './components/thead/thead.module';

import {Ng2SmartTableComponent} from './ng2-smart-table.component';
import {NbCheckboxModule} from '@nebular/theme';
import {CustomViewModule} from './custom/custom-view.module';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,

        CellModule,
        FilterModule,
        PagerModule,
        TBodyModule,
        THeadModule,
        NbCheckboxModule,
        CustomViewModule
    ],
    declarations: [
        Ng2SmartTableComponent,
    ],
    exports: [
        Ng2SmartTableComponent,
        NbCheckboxModule
    ],
})
export class Ng2SmartTableModule {
}
