import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';
import {SystemMessageService} from './system-message.service';
import {DateFormat} from '../../../shared/model/base-constants';

@Component({
    selector: 'ngx-system-message-detail',
    templateUrl: './system-message-detail.component.html'
})
export class SystemMessageDetailComponent implements OnInit, OnDestroy {

    private subscription: Subscription;
    systemMessage: any;

    dateFormat = DateFormat.LONG_TIME;

    constructor(private systemMessageService: SystemMessageService,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.subscription = this.route.queryParams.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.systemMessageService.detail(id).subscribe(response => {
            this.systemMessage = response.body;
        });
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

}
