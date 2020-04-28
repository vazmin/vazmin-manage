import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {PagesComponent} from 'app/pages/pages.component';
import {AuthGuard} from 'app/core/security/auth-guard.service';


const routes: Routes = [{
  path: '',
  component: PagesComponent,
  data: {
    common: true,
  },
  children: [
    {
      path: 'account',
      loadChildren: () => import('./account/account.module')
        .then(m => m.AccountModule),
    }
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagesRoutingModule {}
