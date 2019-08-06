import {Routes} from '@angular/router';
import {ManageRoleComponent} from './manage-role.component';
import {ManageRoleDetailComponent} from './manage-role-detail.component';
import {ManageRoleEditComponent} from './manage-role-edit.component';
import {ManageRoleAuthorizeComponent} from './manage-role-authorize.component';
import {InletComponent} from '../../../@theme/components/inlet.component';

export const manageRoleRoutes: Routes = [
    {
        path: 'role',
        component: InletComponent,
        children: [
            {
                path: '',
                component: ManageRoleComponent,
            }, {
                path: 'detail',
                component: ManageRoleDetailComponent
            }, {
                path: 'new',
                component: ManageRoleEditComponent
            }, {
                path: 'edit',
                component: ManageRoleEditComponent
            }, {
                path: 'authorize',
                component: ManageRoleAuthorizeComponent
            }
        ]
    },
];

export const routedManageRoleComponents = [
    ManageRoleComponent,
    ManageRoleDetailComponent,
    ManageRoleEditComponent,
    ManageRoleAuthorizeComponent,
];
