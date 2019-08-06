import { Type } from '@angular/core';

export class TableItem {
  constructor(public component: Type<any>, public data: any) {}
}
