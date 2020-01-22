import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {AccountComponent} from './account.component';
import {AuthGuard} from '../../@core/security/auth-guard.service';
import {manageUserRoutes, routedManageUserComponents} from './user/manage-user-routing';
import {
    manageRoleRoutes,
    routedManageRoleComponents
} from './role/manage-role-routing';
import {
    groupInfoRoutes,
    routedGroupInfoComponents
} from './group-info/group-info-routing';

const routes: Routes = [{
    path: '',
    component: AccountComponent,
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
        ...manageUserRoutes,
        ...manageRoleRoutes,
        ...groupInfoRoutes,
    ],
}];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
    ],
    exports: [
        RouterModule,
    ],
})
export class AccountRoutingModule {

}

export const routedComponents = [
    AccountComponent,
    ...routedManageUserComponents,
    ...routedManageRoleComponents,
    ...routedGroupInfoComponents,
];
