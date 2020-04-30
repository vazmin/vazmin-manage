import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {SelectionModel} from '@angular/cdk/collections';
import {MatTable, MatTableDataSource} from '@angular/material/table';
import {MatListOption} from '@angular/material/list/selection-list';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {TableDataSource, TableItem} from 'app/foo/table/table-datasource';
import {TableServerDataSource} from 'app/shared/manage/table-server-datasource';
import {ManageUser} from 'app/core/auth/user-identity';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements AfterViewInit, OnInit {

  columns: string[] =
    ['select', 'username', 'name', 'security', 'status', 'createDate', 'star'];
  displayedColumns = Object.assign([], this.columns);
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<ManageUser>;
  dataSource: TableServerDataSource<ManageUser>;
  selection = new SelectionModel<ManageUser>(true, []);

  constructor(protected http: HttpClient) { }

  ngOnInit(): void {
    this.dataSource = new TableServerDataSource(this.http);
  }


  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }


  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: ManageUser): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row`;
  }

  viewColumnSelected(selectedColumn: string[]) {
    this.displayedColumns = Object.assign([], selectedColumn);
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }
}
