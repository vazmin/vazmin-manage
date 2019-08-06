import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {ViewCell} from '../index';

@Component({
    selector: 'ngx-status-icon-view',
    template: `
      <a class="ng2-smart-action ng2-smart-action-delete-delete"
         href="javascript:void(0)" (click)="onClick()">
        <i class="icon {{ extData.toggleIcon[renderValue] }} {{extData.color}}"
           [ngClass]="{'text-success': (renderValue && !extData.color),
           'text-danger': !renderValue && !extData.color}">
        </i>
      </a>
    `,
})
export class ToggleIconViewComponent implements ViewCell, OnInit {
    renderValue: number;
    @Input() value: string | number;
    @Input() rowData: any;
    @Input() extData: any;

    @Output() save: EventEmitter<any> = new EventEmitter();

    ngOnInit() {
        this.renderValue = +this.value;
    }

    onClick() {
        if (!this.extData.disable) {
            this.renderValue = Math.abs(1 - this.renderValue);
        }
        this.save.emit(this.rowData);
    }


}
