import {Routes} from '@angular/router';
import {GroupInfoComponent} from './group-info.component';
import {GroupInfoDetailComponent} from './group-info-detail.component';
import {GroupInfoEditComponent} from './group-info-edit.component';
import {GroupInfoInletComponent} from './group-info-inlet.component';

export const groupInfoRoutes: Routes = [
    {
        path: 'group-info',
        component: GroupInfoInletComponent,
        children: [
            {
                path: '',
                component: GroupInfoComponent,
            }, {
                path: 'detail',
                component: GroupInfoDetailComponent
            }, {
                path: 'new',
                component: GroupInfoEditComponent
            }, {
                path: 'edit',
                component: GroupInfoEditComponent
            }
        ]
    },

];

export const routedGroupInfoComponents = [
    GroupInfoComponent,
    GroupInfoDetailComponent,
    GroupInfoEditComponent,
    GroupInfoInletComponent
];
