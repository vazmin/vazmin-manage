import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';
import {CommandLogService} from './command-log.service';

@Component({
    selector: 'ngx-command-log-detail',
    templateUrl: './command-log-detail.component.html'
})
export class CommandLogDetailComponent implements OnInit, OnDestroy {

    private subscription: Subscription;
    commandLog: any;

    constructor(private commandLogService: CommandLogService,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.subscription = this.route.queryParams.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.commandLogService.detail(id).subscribe(response => {
            this.commandLog = response.body;
        })
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

}
