import { NgxAuthComponent } from './components';
import { NgxLoginComponent } from './components';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NgxRequestPasswordComponent} from './components/auth/request-password/request-password.component';
import {NgxResetPasswordComponent} from './components/auth/reset-password/reset-password.component';

export const authRoutes: Routes = [
    {
        path: 'auth',
        component: NgxAuthComponent,
        children: [
            {
                path: '',
                component: NgxLoginComponent,
            },
            {
                path: 'login',
                component: NgxLoginComponent,
            },
            {
                path: 'request-password',
                component: NgxRequestPasswordComponent,
                data: {
                    showCardHeader: true
                }
            },
            {
                path: 'reset-password',
                component: NgxResetPasswordComponent,
                data: {
                    showCardHeader: true
                }
            }
        ],
    },
];
@NgModule({
    imports: [
        RouterModule.forChild(authRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class AuthRoutingModule { }
