import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';

import {PagesComponent} from './pages.component';
import {NotFoundComponent} from './miscellaneous/not-found/not-found.component';
import {AuthGuard} from '../@core/security/auth-guard.service';

const routes: Routes = [{
    path: '',
    component: PagesComponent,
    canActivate: [AuthGuard],
    data: {
        accessIsAuthenticated: true,
    },
    children: [{
        path: 'foo',
        loadChildren: './foo/foo.module#FooModule',
    }, {
        path: 'account',
        loadChildren: './account/account.module#AccountModule',
    }, {
        path: 'system',
        loadChildren: './system/system.module#SystemModule',
    },
      {
        path: '**',
        component: NotFoundComponent,
        data: {
            accessIsAuthenticated: true,
        }
    }],
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})


export class PagesRoutingModule {
}
