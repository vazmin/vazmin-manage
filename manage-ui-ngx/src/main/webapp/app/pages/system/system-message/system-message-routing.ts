import {Routes} from '@angular/router';
import {SystemMessageComponent} from './system-message.component';
import {SystemMessageDetailComponent} from './system-message-detail.component';
import {SystemMessageInletComponent} from './system-message-inlet.component';

export const systemMessageRoutes: Routes = [
    {
        path: 'system-message',
        component: SystemMessageInletComponent,
        children: [
            {
                path: '',
                component: SystemMessageComponent,
            }, {
                path: 'detail',
                component: SystemMessageDetailComponent
            }
        ]
    },

];

export const routedSystemMessageComponents = [
    SystemMessageInletComponent,
    SystemMessageComponent,
    SystemMessageDetailComponent,
];
