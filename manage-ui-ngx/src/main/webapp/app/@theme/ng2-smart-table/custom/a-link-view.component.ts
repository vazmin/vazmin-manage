import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {ViewCell} from '../index';

@Component({
    selector: 'ngx-status-icon-view',
    template: `
      <a href="javascript:void(0)"
         class="text-link mr-2" (click)="onClick(data.type)" *ngFor="let data of extData;">
        {{data.title}} </a>
    `,
})
export class ALinkViewComponent implements ViewCell, OnInit {
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
