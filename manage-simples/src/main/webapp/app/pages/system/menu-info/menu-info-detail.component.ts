import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';
import {MenuInfoService} from './menu-info.service';

@Component({
    selector: 'ngx-menu-info-detail',
    templateUrl: './menu-info-detail.component.html'
})
export class MenuInfoDetailComponent implements OnInit, OnDestroy {

    private subscription: Subscription;
    menuInfo: any;

    constructor(private menuInfoService: MenuInfoService,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.subscription = this.route.queryParams.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.menuInfoService.detail(id).subscribe(response => {
            this.menuInfo = response.body;
        })
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

}
