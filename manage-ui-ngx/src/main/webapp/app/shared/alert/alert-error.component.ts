import { Component, OnDestroy } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

import { Subscription } from 'rxjs/Subscription';
import {NgxEventManager} from '../service/event-manager.service';
import {NgxAlertService} from '../service/alert.service';

@Component({
    selector: 'ngx-alert-error',
    template: `
        <div class="alerts" role="alert">
            <div *ngFor="let alert of alerts"  [ngClass]="{'alert.position': true, 'toast': alert.toast}">
                <nb-alert *ngIf="alert && alert.type && alert.msg" [status]="alert.type" (close)="alert.close(alerts)">
                    <div><strong>警告</strong></div>
                    <div class="pre-line" [innerHTML]="alert.msg"></div>
                </nb-alert>
            </div>
        </div>`
})
export class NgxAlertErrorComponent implements OnDestroy {

    alerts: any[];
    cleanHttpErrorListener: Subscription;
    // tslint:disable-next-line: no-unused-variable
    constructor(private alertService: NgxAlertService,
                private eventManager: NgxEventManager,
                private translateService: TranslateService) {
        this.alerts = [];

        this.cleanHttpErrorListener = eventManager.subscribe('NgxApp.httpError', (response) => {
            let i;
            const httpErrorResponse = response.content;
            switch (httpErrorResponse.status) {
                // connection refused, server not reachable
                case 0:
                    this.addErrorAlert('Server not reachable', 'error.server.not.reachable');
                    break;

                case 400:
                    const arr = httpErrorResponse.headers.keys();
                    let errorHeader = null;
                    let entityKey = null;
                    arr.forEach((entry) => {
                        if (entry.endsWith('app-error')) {
                            errorHeader = httpErrorResponse.headers.get(entry);
                        } else if (entry.endsWith('app-params')) {
                            entityKey = httpErrorResponse.headers.get(entry);
                        }
                    });
                    if (errorHeader) {
                        this.addErrorAlert(errorHeader, errorHeader, JSON.parse(entityKey));
                        // this.addErrorAlert(errorHeader, errorHeader, { entityName });
                        // const entityName = translateService.instant('global.menu.entities.' + errorHeader);
                    } else if (httpErrorResponse.error !== '' && httpErrorResponse.error.fieldErrors) {
                        const fieldErrors = httpErrorResponse.error.fieldErrors;
                        for (i = 0; i < fieldErrors.length; i++) {
                            const fieldError = fieldErrors[i];
                            // convert 'something[14].other[4].id'
                          // to 'something[].other[].id' so translations can be written to it
                            const convertedField = fieldError.field.replace(/\[\d*\]/g, '[]');
                            const fieldName = translateService.instant('NgxApp.' +
                                fieldError.objectName + '.' + convertedField);
                            this.addErrorAlert(
                                'Error on field "' + fieldName + '"', 'error.' + fieldError.message, { fieldName });
                        }
                    } else if (httpErrorResponse.error !== '' && httpErrorResponse.error.message) {
                        this.addErrorAlert(httpErrorResponse.error.message,
                          httpErrorResponse.error.message, httpErrorResponse.error.params);
                    } else if (httpErrorResponse.error.constructor === Blob) {
                        const reader = new FileReader();
                        reader.addEventListener('loadend', (res) => {
                            console.log(res);
                            // @ts-ignore
                            const r = JSON.parse(res.target.result);
                            this.addErrorAlert(r.message);
                        });
                        reader.readAsText(httpErrorResponse.error, 'UTF-8');
                    } else if (httpErrorResponse.error.title) {
                        this.addErrorAlert(httpErrorResponse.error.title);
                    } else {
                        this.addErrorAlert(httpErrorResponse.error);
                    }
                    break;

                case 404:
                    this.addErrorAlert('Not found', 'error.url.not.found');
                    break;

                default:
                    if (httpErrorResponse.error !== '' && httpErrorResponse.error.message) {
                        this.addErrorAlert(httpErrorResponse.error.message);
                    } else if (httpErrorResponse.error.title) {
                        this.addErrorAlert(httpErrorResponse.error.title);
                    } else {
                        this.addErrorAlert(httpErrorResponse.error);
                    }
            }
        });
    }

    ngOnDestroy() {
        if (this.cleanHttpErrorListener !== undefined && this.cleanHttpErrorListener !== null) {
            this.eventManager.destroy(this.cleanHttpErrorListener);
            this.alerts = [];
        }
    }

    addErrorAlert(message, key?, data?) {
        key = (key && key !== null) ? key : message;
        this.alerts.push(
            this.alertService.addAlert(
                {
                    type: 'danger',
                    msg: key,
                    params: data,
                    timeout: 15000,
                    toast: this.alertService.isToast(),
                    scoped: true
                },
                this.alerts
            )
        );
    }
}
