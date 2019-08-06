import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ManageRoleService} from './manage-role.service';
import {AbstractManageDetailComponent} from '../../../shared/manage/abstract-manage-detail.component';

@Component({
    selector: 'ngx-manage-role-detail',
    templateUrl: './manage-role-detail.component.html'
})
export class ManageRoleDetailComponent extends AbstractManageDetailComponent {


    manageRole: any;

    constructor(protected manageRoleService: ManageRoleService,
                protected route: ActivatedRoute) {
        super(manageRoleService, route);
    }

    setData(data) {
        this.manageRole = data;
    }

}
