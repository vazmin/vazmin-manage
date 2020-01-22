import {Routes} from '@angular/router';
import {MenuInfoComponent} from './menu-info.component';
import {MenuInfoDetailComponent} from './menu-info-detail.component';
import {MenuInfoInletComponent} from './menu-info-inlet.component';

export const menuInfoRoutes: Routes = [
    {
        path: 'menu-info',
        component: MenuInfoInletComponent,
        children: [
            {
                path: '',
                component: MenuInfoComponent,
            }, {
                path: 'detail',
                component: MenuInfoDetailComponent
            }
        ]
    },

];

export const routedMenuInfoComponents = [
    MenuInfoInletComponent,
    MenuInfoComponent,
    MenuInfoDetailComponent,
];
