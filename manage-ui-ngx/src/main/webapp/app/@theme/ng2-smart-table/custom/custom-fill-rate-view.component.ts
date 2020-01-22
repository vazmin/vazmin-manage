import {Component, Input, OnInit} from '@angular/core';

@Component({
    styleUrls: ['./custom-fill-rate-view.component.scss'],
    template: `
        <span class="{{class}}">{{renderValue}}</span>
    `,
})
export class CustomFillRateViewComponent implements OnInit {
    renderValue: string;
    class: string;

    @Input() value: number;
    @Input() rowData: any;
    @Input() extData: any;

    ngOnInit() {
        this.renderValue = this.value + '%';
        if (this.value < 30) {
            this.class = 'text-danger';
        } else if (this.value < 50) {
            this.class = 'text-warning';
        } else if (this.value < 80) {
            this.class = 'text-info';
        } else {
            this.class = 'text-success';
        }
    }
}
