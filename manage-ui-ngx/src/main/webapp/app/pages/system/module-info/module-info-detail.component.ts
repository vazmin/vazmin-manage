import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';
import {ModuleInfoService} from './module-info.service';

@Component({
    selector: 'ngx-module-info-detail',
    templateUrl: './module-info-detail.component.html'
})
export class ModuleInfoDetailComponent implements OnInit, OnDestroy {

    private subscription: Subscription;
    moduleInfo: any;

    constructor(private moduleInfoService: ModuleInfoService,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.subscription = this.route.queryParams.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.moduleInfoService.detail(id).subscribe(response => {
            this.moduleInfo = response.body;
        });
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

}
