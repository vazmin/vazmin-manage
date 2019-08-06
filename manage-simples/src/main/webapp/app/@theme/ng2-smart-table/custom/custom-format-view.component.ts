import {Component, Input, OnInit} from '@angular/core';

@Component({
    template: `        
      <!--<span nbPopover="{{renderValue}}" nbPopoverMode="hover" *ngIf="tooLong()">{{renderValue}}</span>-->
      <span >{{renderValue}}</span>
        <span *ngFor="let rv of renderValueList">{{rv}}<br/></span>
    `,
})
export class CustomFormatViewComponent implements OnInit {
    renderValue: string | number;
    renderValueList: string[] | number[];
    @Input() value: number | string;
    @Input() rowData: any;
    @Input() extData: any;

    ngOnInit() {
        if (this.extData.format &&
            typeof this.extData.format === 'function') {
            if (!this.extData.multi) {
                if (this.value !== '') {
                    this.renderValue = this.extData.format(this.value, this.rowData);
                }
            } else {
                this.renderValueList = this.extData.format(this.value, this.rowData);
            }
        } else  {
            this.renderValue = this.value;
        }
    }

    tooLong() {
        return this.renderValue && this.renderValue.toString().length > 50;
    }
}
