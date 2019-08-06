import {Routes} from '@angular/router';
import {SystemNoticeLogComponent} from './system-notice-log.component';
import {SystemNoticeLogDetailComponent} from './system-notice-log-detail.component';
import {SystemNoticeLogInletComponent} from './system-notice-log-inlet.component';

export const systemNoticeLogRoutes: Routes = [
    {
        path: 'system-notice-log',
        component: SystemNoticeLogInletComponent,
        children: [
            {
                path: '',
                component: SystemNoticeLogComponent,
            }, {
                path: 'detail',
                component: SystemNoticeLogDetailComponent
            },
        ]
    },

];

export const routedSystemNoticeLogComponents = [
    SystemNoticeLogInletComponent,
    SystemNoticeLogComponent,
    SystemNoticeLogDetailComponent,
];
