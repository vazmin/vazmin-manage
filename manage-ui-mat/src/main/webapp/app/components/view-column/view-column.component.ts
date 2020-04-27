import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import {
  MatListOption,
  MatSelectionList, MatSelectionListChange
} from '@angular/material/list/selection-list';

@Component({
  selector: 'app-view-column',
  templateUrl: './view-column.component.html',
  styleUrls: ['./view-column.component.scss']
})
export class ViewColumnComponent implements OnInit {

  delimiter = ',';

  @Input()
  columns: string[];

  @Output()
  onSelected = new EventEmitter();

  @ViewChild("selectionList") selection: MatSelectionList;

  checked: boolean = false;

  indeterminate: boolean = false;

  labelAllSelected = 'Selected all';

  constructor() {
  }

  ngOnInit(): void {
  }

  viewColumn(options: MatListOption[]) {
    const selectedColumns =
      options
        .map(o => String(o.value))
        .sort()
        .map(o => o.split(this.delimiter)[1]);
    this.onSelected.emit(Object.assign([], selectedColumns));
  }

  selectionChange() {
    const selectedLength = this.getSelectedLength();
    this.checked = selectedLength == this.columns.length;
    this.indeterminate = !this.checked && selectedLength > 0;
    this.setLabelAllSelected();
  }

  allSelectionChange(checked: boolean) {
    checked ? this.selection.selectAll() : this.selection.deselectAll();
    this.selectionChange();
  }

  setLabelAllSelected() {
    const selectedLength = this.getSelectedLength();
    this.labelAllSelected = this.checked ? 'All ' + selectedLength + ' selected' :
      (this.indeterminate ?
        selectedLength + ' of ' + this.columns.length + ' selected' :
        'Select all');
  }

  getSelectedLength(): number {
    return this.selection.selectedOptions.selected.length;
  }
}
