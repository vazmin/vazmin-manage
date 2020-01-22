import { Component } from '@angular/core';

import { NzBreadCrumbComponent } from './nz-breadcrumb.component';

@Component({
  selector           : 'ngx-breadcrumb-item',
  preserveWhitespaces: false,
    styleUrls: ['./ngx-breadcrumb.component.scss'],
  template           : `
    <span class="ngx-breadcrumb-link">
      <ng-content></ng-content>
    </span>
    <span class="ngx-breadcrumb-separator">
      <ng-container *ngIf="nzBreadCrumbComponent.isTemplateRef; else stringTemplate">
        <ng-template [ngTemplateOutlet]="nzBreadCrumbComponent.nzSeparator"></ng-template>
      </ng-container>
      <ng-template #stringTemplate>
         {{ nzBreadCrumbComponent.nzSeparator }}
      </ng-template>
    </span>`,
})
export class NzBreadCrumbItemComponent {
  constructor(public nzBreadCrumbComponent: NzBreadCrumbComponent) {
  }

}
