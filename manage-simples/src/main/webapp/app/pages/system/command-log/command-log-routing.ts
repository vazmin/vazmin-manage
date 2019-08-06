import {Routes} from '@angular/router';
import {CommandLogComponent} from './command-log.component';
import {CommandLogDetailComponent} from './command-log-detail.component';
import {CommandLogInletComponent} from './command-log-inlet.component';

export const commandLogRoutes: Routes = [
    {
        path: 'command-log',
        component: CommandLogInletComponent,
        children: [
            {
                path: '',
                component: CommandLogComponent,
            }, {
            path: 'detail',
            component: CommandLogDetailComponent
          }
        ]
    },

];

export const routedCommandLogComponents = [
    CommandLogInletComponent,
    CommandLogComponent,
    CommandLogDetailComponent,
];
