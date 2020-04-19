import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UserRoutes} from 'app/pages/account/user/user-routing';
import {InletComponent} from 'app/shared/components/inlet.component';


const routes: Routes = [
  {
    path: '',
    component: InletComponent,
    children: [
      ...UserRoutes
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountRoutingModule { }
