import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

import {LocalDataSource} from '../local/local.data-source';
import {ServerSourceConf} from './server-source.conf';
import {getDeepFromObject} from '../../helpers';

import {map} from 'rxjs/operators';

export class ServerDataSource extends LocalDataSource {

    protected conf: ServerSourceConf;

    protected lastRequestCount: number = 0;

    protected tableAS = 'a';

    protected parseData: any;

    protected multiSort = false;

    constructor(protected http: HttpClient, conf: ServerSourceConf | {} = {}, initData: any = {}, filterConf?) {
        super();
        if (initData.sortConf) {
            this.setSort(initData.sortConf, false);
        }
        if (initData.multiSort) {
            this.multiSort = initData.multiSort;
        }
        if (initData.parseData) {
            this.parseData = initData.parseData;
        }
        if (filterConf) {
            this.filterConf = filterConf;
        }
        this.conf = new ServerSourceConf(conf);

        if (!this.conf.endPoint) {
            throw new Error('At least endPoint must be specified as a configuration of the server data source.');
        }
    }

    getMultiSort(): boolean {
        return this.multiSort;
    }

    count(): number {
        return this.lastRequestCount;
    }

    /**
     * 1 1; 2 10; 3 20
     * @returns {number}
     */
    first(): number {
        return this.count() > 0 ?
            (this.pagingConf.page - 1) * this.pagingConf.perPage + 1 : 0;
    }

    second(): number {
        return this.pagingConf.page * this.pagingConf.perPage < this.count() ?
            this.pagingConf.page * this.pagingConf.perPage : this.count();
    }

    getElements(): Promise<any> {
        return this.requestElements()
            .pipe(map(res => {
                this.lastRequestCount = this.extractTotalFromResponse(res);
                this.data = this.extractDataFromResponse(res);
                if (this.parseData) {
                    this.data = this.parseData(this.data);
                }
                return this.data;
            })).toPromise();
    }

    setPerPage(pageSize: number) {
        this.getPaging().perPage = pageSize;
        const page = this.getPaging().page;

        if (pageSize * this.getPaging().page < this.count()) {
            const maxPage = Math.ceil(this.count() / pageSize);
            this.setPaging(page > maxPage ? maxPage : page, pageSize, true);
        } else {
            this.setPaging(page, pageSize, true);
        }
    }

    /**
     * Extracts array of data from server response
     * @param res
     * @returns {any}
     */
    protected extractDataFromResponse(res: any): Array<any> {
        const rawData = res.body;
        const data = !!this.conf.dataKey ? getDeepFromObject(rawData, this.conf.dataKey, []) : rawData;

        if (data instanceof Array) {
            return data;
        }

        throw new Error(`Data must be an array.
    Please check that data extracted from the server response by the key '${this.conf.dataKey}' exists and is array.`);
    }

    /**
     * Extracts total rows count from the server response
     * Looks for the count in the heders first, then in the response body
     * @param res
     * @returns {any}
     */
    protected extractTotalFromResponse(res: any): number {
        if (res.headers.has(this.conf.totalKey)) {
            return +res.headers.get(this.conf.totalKey);
        } else {
            const rawData = res.body;
            return getDeepFromObject(rawData, this.conf.totalKey, 0);
        }
    }

    /**
     *
     */
    protected requestElements(): Observable<HttpResponse<any>> {
        return this.http.get<any>(this.conf.endPoint + '/list',
            {params: this.createRequestOptions(), observe: 'response'});
    }

    /**
     * 创建参数
     */
    protected createRequestOptions(): HttpParams {
        let httpParams = new HttpParams();

        httpParams = this.addSortRequestParams(httpParams);
        httpParams = this.addFilterRequestParams(httpParams);
        return this.addPagerRequestParams(httpParams);
    }

    /**
     * 获取数据源筛选参数
     */
    public getFilterRequestParams(): HttpParams {
        return this.addFilterRequestParams(new HttpParams());
    }

    /**
     * 获取数据源筛选参数
     */
    public getFilterAndSortRequestParams(): HttpParams {
        let httpParams = new HttpParams();
        httpParams = this.addSortRequestParams(httpParams);
        httpParams = this.addFilterRequestParams(httpParams);
        return httpParams;
    }

    /**
     * 添加排序参数
     * @param httpParams
     */
    protected addSortRequestParams(httpParams: HttpParams): HttpParams {
        if (this.sortConf) {
            let value = '';
            this.sortConf.forEach((fieldConf, index) => {
                if (index > 0) {
                    value += ', ';
                }
                value += this.tableAS + '.' + ServerDataSource.camelCase(fieldConf.field)
                    + ' ' + fieldConf.direction.toUpperCase();
            });
            httpParams = httpParams.set('filter[' + this.conf.sortFieldKey + ']', value);
        }

        return httpParams;
    }

    static camelCase(name): string {
        return name.replace(/([A-Z])/g, '_$1').toLowerCase();
    }

    /**
     * 添加筛选参数
     * @param httpParams
     */
    protected addFilterRequestParams(httpParams: HttpParams): HttpParams {
        if (this.filterConf.filters) {
            this.filterConf.filters.forEach((fieldConf: any) => {
                if (fieldConf['search'] || fieldConf['search'] === 0) {
                    httpParams = fieldConf['noMap'] ?
                        httpParams.set(fieldConf['field'] , fieldConf['search']) :
                        httpParams.set('filter[' + fieldConf['field'] + ']', fieldConf['search']);
                }
            });
        }

        return httpParams;
    }

    /**
     * 添加页码
     * @param httpParams
     */
    protected addPagerRequestParams(httpParams: HttpParams): HttpParams {

        if (this.pagingConf && this.pagingConf['page'] && this.pagingConf['perPage']) {
            httpParams = httpParams.set(this.conf.pagerPageKey, this.pagingConf['page']);
            httpParams = httpParams.set(this.conf.pagerLimitKey, this.pagingConf['perPage']);
        }

        return httpParams;
    }
}
