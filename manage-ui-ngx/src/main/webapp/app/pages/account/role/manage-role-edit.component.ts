import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ManageRoleService} from './manage-role.service';
import {AbstractManageEditComponent} from '../../../shared/manage/abstract-manage-edit.component';

@Component({
    selector: 'ngx-manage-role-edit',
    templateUrl: './manage-role-edit.component.html',
})
export class ManageRoleEditComponent extends AbstractManageEditComponent {


    manageRole: any;

    constructor(private manageRoleService: ManageRoleService,
                protected router: Router,
                protected route: ActivatedRoute) {
        super(manageRoleService, router, route);
    }

    changeStatus(value) {
        this.manageRole.status = value;
    }

    isManager(value) {
        this.manageRole.manager = value;
    }

    setData(data) {
        this.manageRole = data;
    }

    getData() {
        return this.manageRole;
    }

}
