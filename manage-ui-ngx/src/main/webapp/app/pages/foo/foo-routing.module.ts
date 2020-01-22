import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {AuthGuard} from '../../@core/security/auth-guard.service';
import {InletComponent} from '../../@theme/components/inlet.component';
import {fooRoutes} from './foo-routing';

const routes: Routes = [{
    path: '',
    component: InletComponent,
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
        ...fooRoutes
    ]
}];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
    ],
    exports: [
        RouterModule,
    ],
})
export class FooRoutingModule {

}

