import {ExtraOptions, RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';

import {authRoutes} from './@theme/auth-routing.module';

const routes: Routes = [
    {path: 'pages', loadChildren: './pages/pages.module#PagesModule'},
    ...authRoutes,
    {path: '', redirectTo: 'pages', pathMatch: 'full'},
    {path: '**', redirectTo: 'pages'},
];

const config: ExtraOptions = {
    useHash: true,
    onSameUrlNavigation: 'reload'
    // enableTracing: true
};

@NgModule({
    imports: [RouterModule.forRoot(routes, config)],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
