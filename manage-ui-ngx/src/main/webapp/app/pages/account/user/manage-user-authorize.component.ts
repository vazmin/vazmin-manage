import {
    Component,
    OnDestroy,
    OnInit,
} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';
import {ManageUserService} from './manage-user.service';
import {AbstractManageAuthorizeComponent} from '../abstract-manage-authorize.component';

@Component({
    selector: 'ngx-manage-user-authorize',
    templateUrl: './manage-user-authorize.component.html',
    styleUrls: ['./manage-user-authorize.component.scss']
})
export class ManageUserAuthorizeComponent extends AbstractManageAuthorizeComponent implements OnInit, OnDestroy {

    private subscription: Subscription;
    manageUser: any;

    constructor(protected manageUserService: ManageUserService,
                protected route: ActivatedRoute) {
        super(manageUserService, route);
    }

    ngOnInit(): void {
        this.subscription = this.route.queryParams.subscribe((params) => {
            this.id = params['id'];
            this.load(params['id']);
        });
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

}
