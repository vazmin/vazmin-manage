import {Component, Input, OnDestroy, OnInit} from '@angular/core';

@Component({
    template: `
      <ng2-smart-table
          [settings]="data.settings"
          [source]="data.source"
          (userRowSelect)="data.onSelect($event)">
      </ng2-smart-table>
    `
})
export class NgxTableComponent implements OnDestroy, OnInit {
    @Input() data: any;
    
    ngOnDestroy(): void {
    }

    ngOnInit(): void {
        console.log(this.data);
    }

}
