import {Routes} from '@angular/router';
import {ManageUserComponent} from './manage-user.component';
import {ManageUserDetailComponent} from './manage-user-detail.component';
import {ManageUserEditComponent} from './manage-user-edit.component';
import {ManageUserAuthorizeComponent} from './manage-user-authorize.component';
import {InletComponent} from '../../../@theme/components/inlet.component';

export const manageUserRoutes: Routes = [
    {
        path: 'user',
        component: InletComponent,
        children: [
            {
                path: '',
                component: ManageUserComponent,
            }, {
                path: 'detail',
                component: ManageUserDetailComponent
            }, {
                path: 'new',
                component: ManageUserEditComponent
            }, {
                path: 'edit',
                component: ManageUserEditComponent
            }, {
                path: 'authorize',
                component: ManageUserAuthorizeComponent
            }
        ]
    },
];

export const routedManageUserComponents = [
    ManageUserComponent,
    ManageUserDetailComponent,
    ManageUserEditComponent,
    ManageUserAuthorizeComponent,
];
