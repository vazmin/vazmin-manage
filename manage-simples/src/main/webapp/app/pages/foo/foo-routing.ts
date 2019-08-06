import {Routes} from '@angular/router';
import {FooComponent} from './foo.component';

export const fooRoutes: Routes = [
    {
        path: '',
        component: FooComponent,
    },
];

export const routedTaskInfoComponents = [
    FooComponent,
];
