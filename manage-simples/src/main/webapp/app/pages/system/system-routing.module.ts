import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {SystemComponent} from './system.component';
import {AuthGuard} from '../../@core/security/auth-guard.service';
import {commandInfoRoutes, routedCommandInfoComponents} from './command-info/command-info-routing';
import {menuInfoRoutes, routedMenuInfoComponents} from './menu-info/menu-info-routing';
import {moduleInfoRoutes, routedModuleInfoComponents} from './module-info/module-info-routing';
import {commandLogRoutes, routedCommandLogComponents} from './command-log/command-log-routing';
import {systemMessageRoutes, routedSystemMessageComponents} from './system-message/system-message-routing';
import {
    routedSystemNoticeLogComponents,
    systemNoticeLogRoutes
} from './system-notice-log/system-notice-log-routing';

const routes: Routes = [{
    path: '',
    component: SystemComponent,
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
        ...commandInfoRoutes,
        ...menuInfoRoutes,
        ...moduleInfoRoutes,
        ...commandLogRoutes,
        ...systemMessageRoutes,
        ...systemNoticeLogRoutes
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
export class SystemRoutingModule {

}

export const routedComponents = [
    SystemComponent,
    ...routedCommandInfoComponents,
    ...routedMenuInfoComponents,
    ...routedModuleInfoComponents,
    ...routedCommandLogComponents,
    ...routedSystemMessageComponents,
    ...routedSystemNoticeLogComponents
];
