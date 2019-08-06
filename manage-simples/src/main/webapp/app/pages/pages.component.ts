import {Component} from '@angular/core';

import {Principal} from '../@core/auth/principal.service';

@Component({
    selector: 'ngx-pages',
    template: `
      <ngx-sample-layout>
        <nb-menu [items]="menu"></nb-menu>
        <router-outlet></router-outlet>
      </ngx-sample-layout>
    `,
})
export class PagesComponent {

    menu = [];

    constructor(private principal: Principal) {
        // this.principal.getMenuVMSet().forEach((m) => this.menu.push(m));
        this.menu = this.principal.getMenuVMSet();
    }
}
