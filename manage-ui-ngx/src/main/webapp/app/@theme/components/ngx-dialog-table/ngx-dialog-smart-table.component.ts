
import {
    Component,
    Input,
    OnInit,
} from '@angular/core';
import { NbDialogRef } from '@nebular/theme';
import {of, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {ServerDataSource} from '../../ng2-smart-table';


@Component({
    selector: 'ngx-dialog-table',
    template: `
    <nb-card>
      <nb-card-header>{{ config.title }}</nb-card-header>
      <nb-card-body class="p0">
        <div class="form-group">
          <div class="input-group">
            <input #searchInput class="form-control " type="text" *ngFor="let search of config.search"
                   placeholder="根据{{search.placeholder}}搜索" (keyup)="onSearch(searchInput.value, search.name)">
          </div>

          <ng-select *ngFor="let select of config.select" [items]="select.items"
                     bindLabel="{{ select.bindLabel }}"
                     bindValue="{{ select.bindValue }}"
                     placeholder="请选择{{ select.placeholder }}"
                     name="{{ select.name }}"
                     [(ngModel)]="queryFilter[select.name]"
                     (change)="onSearch(queryFilter[select.name], select.name)">
          </ng-select>
        </div>
        
        <ng2-smart-table
            [settings]="setting"
            [source]="dataSource"
            (userRowSelect)="onSelect($event)">
        </ng2-smart-table>

      </nb-card-body>
      <nb-card-footer>
        <button class="btn btn-primary mr-2" (click)="onSelect()" *ngIf="isMultiSelect">确认</button>
        <button class="btn btn-danger" (click)="dismiss()">关闭</button>
      </nb-card-footer>
    </nb-card>
  `,
})
export class NgxDialogSmartTableComponent implements  OnInit {

    @Input() config: any;

    setting: any;

    dataSource: ServerDataSource;

    searchField: string;

    isMultiSelect: boolean;

    selectedRows: any[];

    queryFilter: any[];

    protected searchText$ = new Subject<string>();


    constructor(protected ref: NbDialogRef<NgxDialogSmartTableComponent>) {
    }

    dismiss() {
        this.ref.close();
    }

    ngOnInit(): void {
        this.setting = this.config.setting;
        this.dataSource = this.config.source;
        this.isMultiSelect = this.setting.selectMode === 'multi';
        this.queryFilter = [];
        this.config.select && this.config.select.forEach((s) => {
            if (s.items.length <= 0 && s.svc) {
                s.svc.listAll().subscribe((res) => {
                    s.items = res.body;
                });
            }
        });
        this.searchText$.pipe(
            debounceTime(600),
            distinctUntilChanged(),
            switchMap(query => {
                this.dataSource.setFilter(
                    [{
                        field: this.searchField,
                        search: query
                    }], false);
                return of(query);
            })
        ).subscribe(query => {});
    }

    onSelect(row?: any) {
        if (!this.isMultiSelect) {
            this.config.onSelect(row);
            this.dismiss();
        } else {
            if (!row) {
                this.config.onSelect(this.selectedRows);
                this.dismiss();
            } else {
                this.selectedRows = row.selected;
            }
        }

    }

    onSearch(query: string, name?: string) {
        name && (this.searchField = name);
        this.searchText$.next(query);
    }
}

