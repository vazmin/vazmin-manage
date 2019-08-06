
import {NgModule} from '@angular/core';
import {NgxTreeNodeCheckboxComponent} from './components/ngx-tree-node-checkbox.component';
import { MobxAngularModule } from 'mobx-angular';
import {TreeModule} from 'angular-tree-component';
import {CommonModule} from '@angular/common';
import {NgxTreeNodeWrapperComponent} from './components/ngx-tree-node-wrapper.component';

const COMPONENTS = [
    NgxTreeNodeCheckboxComponent,
    NgxTreeNodeWrapperComponent,
];

@NgModule({
    imports: [
        CommonModule,
        MobxAngularModule,
        TreeModule
    ],
    declarations: [
        ...COMPONENTS
    ],
    exports: [
        ...COMPONENTS
    ]
})
export class NgxTreeModule {

}
