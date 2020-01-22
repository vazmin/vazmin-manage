import {Routes} from '@angular/router';
import {CommandInfoComponent} from './command-info.component';
import {CommandInfoDetailComponent} from './command-info-detail.component';
import {CommandInfoInletComponent} from './command-info-inlet.component';

export const commandInfoRoutes: Routes = [
    {
        path: 'command-info',
        component: CommandInfoInletComponent,
        children: [
            {
                path: '',
                component: CommandInfoComponent,
            }, {
                path: 'detail',
                component: CommandInfoDetailComponent
            }
        ]
    },

];

export const routedCommandInfoComponents = [
    CommandInfoInletComponent,
    CommandInfoComponent,
    CommandInfoDetailComponent,
];
