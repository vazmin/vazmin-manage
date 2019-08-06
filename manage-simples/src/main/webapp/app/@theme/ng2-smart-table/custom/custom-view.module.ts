import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ToggleIconViewComponent} from './toggle-icon-view.component';
import {CustomFormatViewComponent} from './custom-format-view.component';
import {ButtonGroupViewComponent} from './butto-gropu-view.component';
import {NbPopoverModule} from '@nebular/theme';
import {CustomColorViewComponent} from './custom-color-view.component';
import {CustomFillRateViewComponent} from './custom-fill-rate-view.component';


const VIEW_COMPONENTS = [
    CustomFormatViewComponent,
    ToggleIconViewComponent,
  ButtonGroupViewComponent,
  CustomColorViewComponent,
    CustomFillRateViewComponent
];

@NgModule({
    imports: [
        CommonModule,
        NbPopoverModule,
    ],
    declarations: [
        ...VIEW_COMPONENTS,
    ],
    entryComponents: [
        ...VIEW_COMPONENTS
    ],
    exports: [
        ...VIEW_COMPONENTS,
    ],
})
export class CustomViewModule {
}
