import { Component, OnDestroy, OnInit } from '@angular/core';
import {NgxAlertService} from '../service/alert.service';


@Component({
    selector: 'ngx-alert',
    template: `
        <div class="alerts" role="alert">
            <div *ngFor="let alert of alerts" [ngClass]="{\'alert.position\': true, \'toast\': alert.toast}">
                <nb-alert *ngIf="alert && alert.type && alert.msg" [status]="alert.type" closable>
                    <div [innerHTML]="alert.msg"></div>
                </nb-alert>
            </div>
        </div>`,
})
export class NgxAlertComponent implements OnInit, OnDestroy {
    alerts: any[];

    constructor(private alertService: NgxAlertService) { }

    ngOnInit() {
        this.alerts = this.alertService.get();
    }

    ngOnDestroy() {
        this.alerts = [];
    }

}
