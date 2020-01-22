import {
    Component,
    OnDestroy,
    OnInit,
} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';
import {ManageRoleService} from './manage-role.service';
import {AbstractManageAuthorizeComponent} from '../abstract-manage-authorize.component';

@Component({
    selector: 'ngx-manage-role-authorize',
    templateUrl: './manage-role-authorize.component.html'
})
export class ManageRoleAuthorizeComponent extends AbstractManageAuthorizeComponent implements OnInit, OnDestroy {

    private subscription: Subscription;
    manageUser: any;

    constructor(protected manageRoleService: ManageRoleService,
                protected route: ActivatedRoute) {
        super(manageRoleService, route);
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
