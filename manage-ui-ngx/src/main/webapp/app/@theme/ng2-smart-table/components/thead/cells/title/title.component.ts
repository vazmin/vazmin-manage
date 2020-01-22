import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {Subscription} from 'rxjs';

import {DataSource} from '../../../../lib/data-source/data-source';
import {Column} from '../../../../lib/data-set/column';

@Component({
  selector: 'ng2-smart-table-title',
  styleUrls: ['./title.component.scss'],
  template: `
    <a href="#" *ngIf="column.isSortable"
                (click)="_sort($event)"
                class="ng2-smart-sort-link sort"
                [ngClass]="currentDirection">
      {{ column.title }}
    </a>
    <span class="ng2-smart-sort" *ngIf="!column.isSortable">{{ column.title }}</span>
  `,
})
export class TitleComponent implements OnChanges {

  currentDirection = '';
  @Input() column: Column;
  @Input() source: DataSource;
  @Output() sort = new EventEmitter<any>();

  protected dataChangedSub: Subscription;

  ngOnChanges(changes: SimpleChanges) {
    if (changes.source) {
      if (!changes.source.firstChange) {
        this.dataChangedSub.unsubscribe();
      }
      this.dataChangedSub = this.source.onChanged().subscribe((dataChanges) => {
        const sortConf = this.source.getSort();

          this.currentDirection = '';
        sortConf.forEach((fieldConf: any) => {
            if (fieldConf['field'] === this.column.id) {
                this.currentDirection = fieldConf['direction'];
            }
        });
      });
    }
  }

  _sort(event: any) {
    event.preventDefault();
    this.changeSortDirection();
      let sort;
      if (this.source.getMultiSort()) {
          sort = this.source.getSort();
          let exist = false;
          let removeIndex;
          sort.forEach((fieldConf, index) => {
              if (fieldConf['field'] === this.column.id) {
                  exist = true;
                  if (this.currentDirection === '') {
                      removeIndex = index;
                  } else {
                      fieldConf['direction'] = this.currentDirection;
                      fieldConf['compare'] = this.column.getCompareFunction();
                  }
              }
          });
          if (removeIndex !== undefined) {
              sort.splice(removeIndex, 1);
          }
          if (!exist) {
              sort.push({
                  field: this.column.id,
                  direction: this.currentDirection,
                  compare: this.column.getCompareFunction()
              });
          }
      } else {
          sort = [{
              field: this.column.id,
              direction: this.currentDirection,
              compare: this.column.getCompareFunction()
          }];
      }
    this.source.setSort(sort);
    this.sort.emit(null);
  }

  changeSortDirection(): string {
    if (this.currentDirection) {
        if (this.currentDirection === 'asc') {
            this.currentDirection = 'desc';
        } else if (this.source.getMultiSort()) {
            this.currentDirection = '';
        } else {
            this.currentDirection = 'asc';
        }
    } else {
      this.currentDirection = this.column.sortDirection;
    }
    return this.currentDirection;
  }
}
