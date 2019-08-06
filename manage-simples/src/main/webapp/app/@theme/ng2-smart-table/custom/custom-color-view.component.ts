import {Component, Input, OnInit} from '@angular/core';

@Component({
  template: `
    <span class="{{class}}">{{renderValue}}</span>
  `,
})
export class CustomColorViewComponent implements OnInit {
  renderValue: string | number;
  class: string;

  @Input() value: number | string;
  @Input() rowData: any;
  @Input() extData: any;

  ngOnInit() {
    this.renderValue = this.value;
    this.class = this.renderValue === '1' ? 'text-success' : 'text-danger';
    if (this.extData.format &&
      typeof this.extData.format === 'function') {
      this.renderValue = this.extData.format(this.value);
    }
  }
}
