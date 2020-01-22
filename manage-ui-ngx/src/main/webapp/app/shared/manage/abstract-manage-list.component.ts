import {OnInit} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {StateStorageService} from '../../@core/auth/state-storage.service';
import {ServerDataSource} from '../../@theme/ng2-smart-table';
import {ActivatedRoute, Router} from '@angular/router';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {of} from 'rxjs/observable/of';
import {ManageServiceInterface} from './manage-service-interface';
import {HttpClient} from '@angular/common/http';
import {Principal} from '../../@core/auth/principal.service';
import {isFunction} from 'util';

/**
 * 管理组件（列表页） 抽象类
 */
export class AbstractManageListComponent implements OnInit {
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

    filterConf: any;

    protected searchText$ = new Subject<any>();

    selected: any[];

    source: ServerDataSource;

    searchField: string;

    queryFilter = {};

    spiltFlag = '_:_';

    /** 隐藏列 */
    hiddenDataSet: Array<string>;

    constructor(protected http: HttpClient,
                protected principal: Principal,
                protected manageService: ManageServiceInterface,
                protected route: ActivatedRoute,
                protected router: Router,
                protected stateStorageService: StateStorageService,
    ) {
    }

    init() {
        this.sourceConf.endPoint = this.manageConfig.endPoint;
        this.setQueryFilters();
        this.initPageConf();
        this.setData();
        this.settings.columns = this.manageConfig.columns;
        this.principal.hasPermissionForCURD(this.sourceConf.endPoint, this.settings);
        this.addCustomAction();
        this.source = new ServerDataSource(this.http, this.sourceConf,
            this.manageConfig.initData, this.filterConf);
        this.setFiltersList();
    }

    setData() {
    }

    ngOnInit(): void {
        this.init();
        this.searchText$.pipe(
            debounceTime(600),
            distinctUntilChanged(),
            switchMap(query => {
                if (query.value != null) {
                    if (isNaN(parseInt(query.value, 10))) {
                        query.value = encodeURIComponent(query.value);
                    }
                }
                this.source.setFilter(
                    [{
                        field: query.name,
                        search: query.value
                    }], false);
                return of(query);
            })
        ).subscribe(query => {
            this.onSearchAfter();
        });
        this.ngOnInitAfter();
    }

    ngOnInitAfter() {
    }

    onEdit(even) {
        this.navigate('./edit', even.data.id);
    }

    onDetail(even) {
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
            this.delete(this.getSelectedDeleteKey());
        }
    }

    getSelectedDeleteKey() {
        return this.selected.map(d => d.id).toString();
    }


    navigate(link: string, id?: any) {
        this.router.navigate([link],
            {
                relativeTo: this.route,
                queryParams: {id: id}
            }).then(() => this.storeState());
    }

    navigateWithParams(link: string, queryParams?: any) {
        this.router.navigate([link],
            {
                relativeTo: this.route,
                queryParams: queryParams
            }).then(() => this.storeState());
    }

    onSelect(event) {
        this.selected = event.selected;
    }


    onSearch(query: any, name?: string) {
        name && (this.searchField = name);
        this.searchText$.next({name: name, value: query});
    }

    onSearchAfter() {
    }

    changePageSize(size: number): void {
        this.settings.pager.perPage = size;
        this.source.setPerPage(size);
    }

    /** 保存页数和查询条件缓存*/
    storeState() {
        this.stateStorageService.storePreviousState(this.sourceConf.endPoint,
            {
                paging: this.source.getPaging(),
                filter: this.source.getFilter()
            });
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
            this.initCacheFilter(previousState);
            this.stateStorageService.resetPreviousState();
        }
    }

    /** 获取缓存的查询字段，初始化*/
    initCacheFilter(previousState) {
        this.filterConf = previousState.params.filter;
        this.filterConf.filters.forEach(v => {
            if (this.queryFilter.hasOwnProperty(v.field)) {
                if (isNaN(parseInt(v.search, 10))) {
                    this.queryFilter[v.field] = decodeURIComponent(v.search);
                } else {
                    this.queryFilter[v.field] = v.search;
                }
            }
        });
        this.setFilterAfter();
    }

    /** 子类设置需要查询的字段*/
    setQueryFilters() {
    }

    /**  用来处理日期 */
    setFilterAfter() {
    }

    setFiltersList() {
    }

    initSetting(sourceSettings = {}) {
        Object.assign(this.settings, sourceSettings);
    }

    /**
     * 自定义操作
     * @param event
     */
    onCustom(event) {
        if (typeof event.action.do === 'function') {
            event.action.do(event);
        } else {
            const queryParams = isFunction(event.action.getQueryParams) ?
                event.action.getQueryParams(event) : {'id': event.data.id};
            this.router.navigate([event.action.path],
                {relativeTo: this.route, queryParams: queryParams})
                .then(() => {
                });
        }
    }

    /**
     * 添加自定义操作
     */
    addCustomAction() {
        if (this.manageConfig.customActions && this.manageConfig.customActions.length > 0) {
            this.manageConfig.customActions.forEach((act) => {
                // 带路径且有权限
                if (act.path && this.principal.hasAllPermissionDirect([act.path])) {
                    this.settings.actions.custom.push(act);
                }
            });
        }
    }

    /**
     * 获取隐藏列
     */
    getHiddenDataSet() {
        return this.hiddenDataSet;
    }
}
