
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Ng2SmartTableModule} from '../../@theme/ng2-smart-table';
import {ThemeModule} from '../../@theme/theme.module';
import {NgxTableComponent} from './ngx-table.component';
import {NgxTableComponentDirective} from './ngx-table-component.directive';
import {NgxDynamicTableComponent} from './ngx-dynamic-table.component';
import {Ng2SmartTableComponent} from '../../@theme/ng2-smart-table/ng2-smart-table.component';


const COMPONENTS = [
    NgxTableComponent,
    NgxTableComponentDirective,
    NgxDynamicTableComponent
];

@NgModule({
    imports: [
        CommonModule,
        Ng2SmartTableModule,
        ThemeModule
    ],
    declarations: [
        ...COMPONENTS
    ],
    exports: [
        ...COMPONENTS
    ],
    entryComponents: [
        Ng2SmartTableComponent,
        NgxTableComponent
    ]
})
export class NgxDynamicTableModule {

}
