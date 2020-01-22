import {Component, Input, ChangeDetectionStrategy} from '@angular/core';

import {Cell} from '../../../lib/data-set/cell';

@Component({
    selector: 'table-cell-view-mode',
    changeDetection: ChangeDetectionStrategy.OnPush,
    template: `
      <div [ngSwitch]="cell.getColumn().type">
        <custom-view-component *ngSwitchCase="'custom'"
                               [cell]="cell"></custom-view-component>
        <div *ngSwitchCase="'html'" [innerHTML]="cell.getValue()"></div>
        <div *ngSwitchDefault>
          <span *ngIf="!tooLong()">{{ cell.getValue() }}</span>
          <span *ngIf="tooLong()" 
                nbPopover="{{cell.getValue()}}" nbPopoverMode="hover">{{ cell.getValue() }}</span>
        </div>
      </div>
    `,
})
export class ViewCellComponent {

    @Input() cell: Cell;

    tooLong() {
        const v = this.cell.getValue();
        return v && v.length && v.length > 50;
    }

}
