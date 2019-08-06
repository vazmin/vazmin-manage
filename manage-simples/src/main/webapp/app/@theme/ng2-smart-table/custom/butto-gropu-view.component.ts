import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {ViewCell} from '../index';

@Component({
    selector: 'ngx-status-icon-view',
    template: `
      <button class="btn btn-primary mr-2" (click)="onClick(data.type)" *ngFor="let data of extData;">
        {{data.title}} </button>
    `,
})
export class ButtonGroupViewComponent implements ViewCell, OnInit {
    @Input() value: string | number;
    @Input() rowData: any;
    @Input() extData: any;

    @Output() save: EventEmitter<any> = new EventEmitter();
    ngOnInit() {
    }
    //

    onClick(type) {
        const result = this.rowData;
        result.type = type;
        this.save.emit(result);
    }


}
