import {NgModule} from '@angular/core';
import {ThemeModule} from '../../@theme/theme.module';
import {routedTaskInfoComponents} from './foo-routing';
import {FooRoutingModule} from './foo-routing.module';



@NgModule({
    imports: [
        ThemeModule,
        FooRoutingModule,
    ],
    entryComponents: [
    ],
    declarations: [
        ...routedTaskInfoComponents,
    ],
    providers: [
    ]
})
export class FooModule {
}
