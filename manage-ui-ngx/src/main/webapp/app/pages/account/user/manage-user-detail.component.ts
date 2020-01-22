import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ManageUserService} from './manage-user.service';
import {AbstractManageDetailComponent} from '../../../shared/manage/abstract-manage-detail.component';
import {DateFormat} from '../../../shared/model/base-constants';
import {IManageUser} from './manage-user.model';

@Component({
    selector: 'ngx-manage-user-detail',
    templateUrl: './manage-user-detail.component.html'
})
export class ManageUserDetailComponent extends AbstractManageDetailComponent {

    setData(data: IManageUser) {
        this.manageUser = data;
    }
    dateTimeFormat = DateFormat.LONG_TIME;
    manageUser: IManageUser;

    constructor(private manageUserService: ManageUserService,
                protected route: ActivatedRoute) {
        super(manageUserService, route);
    }
}
