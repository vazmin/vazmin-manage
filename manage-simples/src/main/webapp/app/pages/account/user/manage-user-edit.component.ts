import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ManageUserService} from './manage-user.service';
import {AbstractManageEditComponent} from '../../../shared/manage/abstract-manage-edit.component';
import {ManageRoleService} from '../role/manage-role.service';

@Component({
    selector: 'ngx-manage-user-edit',
    templateUrl: './manage-user-edit.component.html',
})
export class ManageUserEditComponent extends AbstractManageEditComponent {

    getData() {
        return this.manageUser;
    }

    setData(data: any) {
        this.manageUser = data;
        this.manageRoleService.list().subscribe((res) => {
            this.roleList = res.body;
            if (this.manageUser.id) {
                this.roleList.forEach((role) => {
                    role.checked = this.manageUser.roleIdSet.includes(role.id);
                });
            }
        });
        console.log(this.manageUser);
    }

    manageUser: any;
    roleList: any[];
    passwordLength= {
        max: 20,
        min: 8,
    };
    constructor(private manageUserService: ManageUserService,
                private manageRoleService: ManageRoleService,
                protected router: Router,
                protected route: ActivatedRoute) {
        super(manageUserService, router, route);
    }

    saveBefore() {
        if (this.roleList) {
            this.manageUser.roleIdSet =
                this.roleList
                    .filter((role) => role.checked)
                    .map((role) => role.id);
        }
    }

    saveNewBefore() {
        this.saveBefore();
    }

    changeStatus(value) {
        this.manageUser.status = value;
    }

    changeSendEmail(value) {
        this.manageUser.sendEmail = value;
    }

}
