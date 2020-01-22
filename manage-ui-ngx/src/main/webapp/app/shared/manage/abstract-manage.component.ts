import {OnInit} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {StateStorageService} from '../../@core/auth/state-storage.service';
import {ServerDataSource} from '../../@theme/ng2-smart-table';
import {ActivatedRoute, Router} from '@angular/router';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {of} from 'rxjs/observable/of';
import {ManageServiceInterface} from './manage-service-interface';
import {Principal} from '../../@core/auth/principal.service';

/**
 * 管理组件（列表页） 抽象类
 */
export class AbstractManageComponent implements OnInit {
    manageConfig;
    settings = {
        selectMode: 'multi',
        mode: 'external',
        hideSubHeader: true,
        actions: {
            position: 'right',
            add: true,
            edit: true,
            detail: true,
            delete: true,
            columnTitle: '操作',
            custom: [],
        },
        add: {
            addButtonContent: '<i class="nb-plus"></i>',
            createButtonContent: '<i class="nb-checkmark"></i>',
            cancelButtonContent: '<i class="nb-close"></i>',
        },
        edit: {
            editButtonContent: '<i class="icon ion-md-create"></i>',
            saveButtonContent: '<i class="nb-checkmark"></i>',
            cancelButtonContent: '<i class="nb-close"></i>',
        },
        delete: {
            deleteButtonContent: '<i class="icon ion-md-trash"></i>',
            confirmDelete: true,
        },
        detail: {
            detailButtonContent: '<i class="icon ion-md-eye"></i>',
        },
        columns: {},
        pager: {
            page: 1,
            perPage: 20,
            perPages: [
                10, 20, 50, 100
            ]
        },
        noDataMessage: '未找到记录!',
        getHiddenDataSet: this.getHiddenDataSet.bind(this)
    };
    sourceConf = {
        pagerPageKey: 'pageNumber',
        pagerLimitKey: 'pageSize',
        sortFieldKey: 'fullordering',
        endPoint: '/'
    };

    protected searchText$ = new Subject<string>();

    selected: any[];

    source: ServerDataSource;
    searchField: string;

    filterList: any[];

    /** 隐藏列 */
    hiddenDataSet: Array<string>;

    constructor(
                protected manageService: ManageServiceInterface,
                protected route: ActivatedRoute,
                protected router: Router,
                protected stateStorageService: StateStorageService,
                private principal?: Principal) {
    }

    ngOnInit(): void {
        if (this.principal) {
          const actions = this.manageConfig['actions'];
          if (actions) {
            for (let index = actions.custom.length - 1; index >= 0; index--) {
              let hasAllPermission = actions.custom[index].hasAllPermission;
              let hasAnyPermission = actions.custom[index].hasAnyPermission;

              if (hasAllPermission === undefined && hasAnyPermission === undefined) {
                continue;
              }

              if (hasAllPermission !== undefined) {
                hasAllPermission = typeof hasAllPermission === 'string' ?
                  [ <string> hasAllPermission ] : <string[]> hasAllPermission;
                const result = this.principal.hasAllPermissionDirect(hasAllPermission);
                if (!result) {
                  actions.custom.splice(index, 1);
                }
              }

              if (hasAnyPermission !== undefined) {
                hasAnyPermission = typeof hasAnyPermission === 'string' ?
                  [ <string> hasAnyPermission ] : <string[]> hasAnyPermission;
                const result = this.principal.hasAnyPermissionDirect(hasAnyPermission);
                if (!result) {
                  actions.custom.splice(index, 1);
                }
              }
            }
          }
        }
        this.searchText$.pipe(
            debounceTime(600),
            distinctUntilChanged(),
            switchMap(query => {
                this.source.setFilter(
                    [{
                        field: this.getSearchField(),
                        search: query
                    }], false);
                return of(query);
            })
        ).subscribe(query => {
            this.onSearchAfter();
        });
        this.ngOnInitAfter();
    }

    ngOnInitAfter() {}

    onNew() {
        this.navigateAndStore('./new');
    }

    onEdit(even) {
        // this.router.navigate(['./edit', {id: even.data.id}],
        //     {relativeTo: this.route}).then(() => this.storeState());
        this.navigate('./edit', even.data.id);
    }

    onDetail(even) {
        // this.router.navigate(['./detail', {id: even.data.id}],
        //     {relativeTo: this.route}).then(() => this.storeState());
        this.navigate('./detail', even.data.id);
    }

    onDelete(event) {
        if (window.confirm('你确定删除吗?')) {
            this.delete(event.data.id);
        }
    }

    delete(ids) {
        this.manageService.delete(ids).subscribe((res) => {
            this.source.refresh();
            this.selected = null;
        });
    }

    onDeleteForSelect() {
        if (!this.selected || this.selected.length <= 0) {
            alert('请选择记录！');
            return;
        }
        if (window.confirm('你确定批量删除吗?')) {
            this.delete(this.selected.map(d => d.id).toString());
        }
    }


    navigate(link: string, id?: any) {
        this.router.navigate([link],
            {relativeTo: this.route, queryParams: {id: id}}).then(() => this.storeState());
    }

    navigateAndStore(link: string, queryParams?: any) {
        this.router.navigate([link],
            {relativeTo: this.route, queryParams: queryParams}).then(() => this.storeState());
    }

    onSelect(event) {
        this.selected = event.selected;
    }


    onSearch(query: string, name?: string) {
      name && (this.searchField = name);
      this.searchText$.next(query);
    }
    
    onSearchAfter() {}

    changePageSize(size: number): void {
        this.settings.pager.perPage = size;
        this.source.setPerPage(size);
    }

    /** 保存页数和查询条件缓存*/
    storeState() {
        this.stateStorageService.storePreviousState(this.sourceConf.endPoint,
            {paging: this.source.getPaging(), filter: this.source.getFilter()});
    }

    getSearchField() {
        return this.searchField;
    }

    /**
     * 设置页码和查询条件
     */
    initPageConf() {
        const previousState = this.stateStorageService.getPreviousState();
        if (previousState && previousState.name === this.sourceConf.endPoint) {
            this.settings.pager.perPage = previousState.params.paging.perPage;
            this.settings.pager.page = previousState.params.paging.page;
        }
    }

    /** 获取缓存的查询字段，初始化*/
    getFilterConf() {
        const state = this.stateStorageService.getPreviousState();
        if (state && state.name === this.sourceConf.endPoint) {
            this.setFilterList();
            const filters = state.params.filter;
            filters.filters.forEach( v => {
                this.filterList.forEach( f => {
                    if (v.field === f.key) {
                        f.value = v.search;
                    }
                });
            });
            this.setFilterAfter();
            this.stateStorageService.resetPreviousState();
            return filters;
        }
    }


    /** 子类设置需要查询的字段*/
    setFilterList() {}

    /**  用来处理日期 */
    setFilterAfter() {}

    initSetting(sourceSettings = {}) {
        Object.assign(this.settings, sourceSettings);
    }

    /**
     * 获取隐藏列
     */
    getHiddenDataSet() {
        return this.hiddenDataSet;
    }

}
