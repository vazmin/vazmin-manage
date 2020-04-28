import {Routes} from '@angular/router';
import {InletComponent} from 'app/components/inlet.component';
import {UserComponent} from 'app/pages/account/user/user.component';
import {HTTP_METHOD} from 'app/app.constants';
import {AuthGuard} from 'app/core/security/auth-guard.service';



export const UserRoutes: Routes = [
  {
    path: 'user',
    component: InletComponent,

    children: [
      {
        path: '',
        component: UserComponent
      },
      {
        path: 'edit',
        component: UserComponent,
        data: {
          method: HTTP_METHOD.PUT,
        }
      },
      {
        path: 'create',
        component: UserComponent,
        data: {
          method: HTTP_METHOD.POST,
        }
      },
    ]
  }
];
