import { Component, Input } from '@angular/core';

import { Grid } from '../../../lib/grid';
import { DataSource } from '../../../lib/data-source/data-source';

/**
 * Overridden
 */
@Component({
  selector: '[ng2-st-checkbox-select-all]',
  template: `
      <!--<input type="checkbox" [ngModel]="isAllSelected">-->
      <div class="ng2-smart-title">
          <div class="custom-control custom-checkbox select-all-cb">
              <input type="checkbox" class="custom-control-input" [ngModel]="isAllSelected">
              <label class="custom-control-label" for="b-checkbox">&nbsp;</label>
          </div>
      </div>
  `,
})
export class CheckboxSelectAllComponent {

  @Input() grid: Grid;
  @Input() source: DataSource;
  @Input() isAllSelected: boolean;
}
