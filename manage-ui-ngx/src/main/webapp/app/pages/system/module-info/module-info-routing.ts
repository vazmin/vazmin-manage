import {Routes} from '@angular/router';
import {ModuleInfoComponent} from './module-info.component';
import {ModuleInfoDetailComponent} from './module-info-detail.component';
import {ModuleInfoInletComponent} from './module-info-inlet.component';

export const moduleInfoRoutes: Routes = [
    {
        path: 'module-info',
        component: ModuleInfoInletComponent,
        children: [
            {
                path: '',
                component: ModuleInfoComponent,
            }, {
                path: 'detail',
                component: ModuleInfoDetailComponent
            }
        ]
    },

];

export const routedModuleInfoComponents = [
    ModuleInfoInletComponent,
    ModuleInfoComponent,
    ModuleInfoDetailComponent,
];
