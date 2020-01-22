import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {GroupInfoService} from './group-info.service';
import {AbstractManageEditComponent} from '../../../shared/manage/abstract-manage-edit.component';

@Component({
    selector: 'ngx-group-info-edit',
    templateUrl: './group-info-edit.component.html',
})
export class GroupInfoEditComponent extends AbstractManageEditComponent {

    groupInfo: any;

    constructor(private groupInfoService: GroupInfoService,
                protected router: Router,
                protected route: ActivatedRoute) {
        super(groupInfoService, router, route);
    }

    setData(data) {
        this.groupInfo = data;
    }

    getData() {
        return this.groupInfo;
    }

}
