import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';
import {CommandInfoService} from './command-info.service';

@Component({
    selector: 'ngx-command-info-detail',
    templateUrl: './command-info-detail.component.html'
})
export class CommandInfoDetailComponent implements OnInit, OnDestroy {

    private subscription: Subscription;
    commandInfo: any;

    constructor(private commandInfoService: CommandInfoService,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.subscription = this.route.queryParams.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.commandInfoService.detail(id).subscribe(response => {
            this.commandInfo = response.body;
        })
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

}
