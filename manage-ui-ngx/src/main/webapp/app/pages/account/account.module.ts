import {NgModule} from '@angular/core';
import {ThemeModule} from '../../@theme/theme.module';
import {routedComponents, AccountRoutingModule} from './account-routing.module';
import {Ng2SmartTableModule} from '../../@theme/ng2-smart-table';
import {ManageUserService} from './user/manage-user.service';
import {ManageRoleService} from './role/manage-role.service';
import {TreeModule} from 'angular-tree-component';
import {NgxTreeModule} from '../../other/tree/ngx-tree.module';
import {GroupInfoService} from './group-info/group-info.service';

@NgModule({
    imports: [
        ThemeModule,
        AccountRoutingModule,
        Ng2SmartTableModule,
        NgxTreeModule,
        TreeModule,
    ],
    declarations: [
        // NgxTreeNodeCheckboxComponent,
        ...routedComponents
    ],
    providers: [
        ManageUserService,
        ManageRoleService,
        GroupInfoService,
    ]
})
export class AccountModule {
}
