import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'ngx-status-card',
  styleUrls: ['./status-card.component.scss'],
  template: `
    <nb-card (click)=changeValue() [ngClass]="{'off': !on}">
      <div class="icon-container">
        <div class="icon {{ type }}">
          <ng-content></ng-content>
        </div>
      </div>

      <div class="details">
        <div class="title">{{ title }}</div>
        <!--<div class="status">{{ on ? 'ON' : 'OFF' }}</div>-->
      </div>
    </nb-card>
  `,
})
export class StatusCardComponent {

  @Input() title: string;
  @Input() type: string;
  @Input() on = false;
  @Input() ids;
  @Input() id;

  @Output() valueChange = new EventEmitter<any>();

  changeValue() {
    this.on = !this.on;
    this.change();
    this.valueChange.emit(this.ids);
  }

    change() {
    if (this.ids) {
      if (this.ids.includes(this.id)) {
        const index = this.ids.findIndex(v => v === this.id);
        this.ids.splice(index, 1);
      } else {
        this.ids.push(this.id);
      }
    } else {
      this.ids = [];
      this.ids.push(this.id);
    }
  }
}
