import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {GroupInfoService} from './group-info.service';
import {AbstractManageDetailComponent} from '../../../shared/manage/abstract-manage-detail.component';

@Component({
    selector: 'ngx-group-info-detail',
    templateUrl: './group-info-detail.component.html'
})
export class GroupInfoDetailComponent extends AbstractManageDetailComponent {

    groupInfo: any;

    constructor(private groupInfoService: GroupInfoService,
                protected route: ActivatedRoute) {
        super(groupInfoService, route);
    }

    setData(data) {
        this.groupInfo = data;
    }

}
