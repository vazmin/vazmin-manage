import {Component, Input, OnInit} from '@angular/core';
import {SelectionModel} from '@angular/cdk/collections';
import {ManageUser} from 'app/core/auth/user-identity';
import {TableServerDataSource} from 'app/shared/manage/table-server-datasource';

@Component({
  selector: 'app-table-checkbox',
  templateUrl: './table-checkbox.component.html',
  styleUrls: ['./table-checkbox.component.scss']
})
export class TableCheckboxComponent implements OnInit {

  private _dataSource: TableServerDataSource<any>;

  _selection:SelectionModel<any>;

  @Input()
  set dataSource(ds: TableServerDataSource<any>) {
    this._dataSource = ds;
    this._selection = ds.selection;
  };

  get dataSource() : TableServerDataSource<any> {
    return this._dataSource;
  }

  constructor() {
  }

  ngOnInit(): void {
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this._selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this._selection.clear() :
      this.dataSource.data.forEach(row => this._selection.select(row));
  }
}
