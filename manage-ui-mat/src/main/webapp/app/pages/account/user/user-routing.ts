import {Routes} from '@angular/router';
import {InletComponent} from 'app/components/inlet.component';
import {UserComponent} from 'app/pages/account/user/user.component';

export const UserRoutes: Routes = [
  {
    path: 'user',
    component: InletComponent,
    children: [
      {
        path: '',
        component: UserComponent
      }
    ]
  }
];