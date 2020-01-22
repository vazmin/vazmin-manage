/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import {Component, OnDestroy, OnInit} from '@angular/core';
import {
    ActivatedRoute, ActivatedRouteSnapshot, ActivationEnd,
    ChildActivationEnd,
    NavigationEnd,
    Router
} from '@angular/router';
import {filter} from 'rxjs/operators';
import {Observable} from 'rxjs';
import { Location } from '@angular/common';
// import { takeWhile } from 'rxjs/operators/takeWhile';
// import {NbAuthService} from "@nebular/auth";

@Component({
    selector: 'ngx-auth',
    styleUrls: ['./auth.component.scss'],
    template: `
      <nb-layout>
        <nb-layout-column>
          <nb-card>
            <nb-card-header *ngIf="showCardHeader">
              <nav class="navigation">
                <a href="#" (click)="back()" class="link back-link" aria-label="Back">
                  <i class="ion-md-arrow-back"></i>
                </a>
              </nav>
            </nb-card-header>
            <nb-card-body>
              <div class="flex-centered col-xl-4 col-lg-6 col-md-8 col-sm-12">
                <router-outlet></router-outlet>
              </div>
            </nb-card-body>
          </nb-card>
        </nb-layout-column>
      </nb-layout>
    `,
})
export class NgxAuthComponent implements OnDestroy, OnInit {

    private alive = true;

    subscription: any;

    authenticated: boolean = false;
    token: string = '';
    showCardHeader: boolean = false;
    childActivationEnd: Observable<ChildActivationEnd>;

    constructor(private route: ActivatedRoute, private router: Router,
                protected location: Location) {
        this.childActivationEnd = this.router.events
            .pipe(filter(e => e instanceof ChildActivationEnd)) as Observable<ChildActivationEnd>;
    }


    ngOnDestroy(): void {
        this.alive = false;
    }

    ngOnInit(): void {
        this.setShowCardHeaderBySnapshot(this.route.snapshot);
        this.childActivationEnd.subscribe((value: ChildActivationEnd) => {
            this.setShowCardHeaderBySnapshot(value.snapshot);
        });
    }

    setShowCardHeaderBySnapshot(ars: ActivatedRouteSnapshot) {
        this.showCardHeader = this.getShowCardHeaderBySnapshot(ars);
    }

    /**
     * 从路由配置取data
     * @param ars
     */
    getShowCardHeaderBySnapshot(ars: ActivatedRouteSnapshot) {
        if (ars.children.length === 0) {
            return ars.data.showCardHeader;
        } else {
            return this.getShowCardHeaderBySnapshot(ars.firstChild);
        }
    }

    back() {
        this.location.back();
        return false;
    }
}
