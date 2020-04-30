import {ArrayDataSource, DataSource, SelectionModel} from '@angular/cdk/collections';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';
import {merge, Observable, of as observableOf} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {ManageUser} from 'app/core/auth/user-identity';


/**
 * Data source for the Table view. This class should
 * encapsulate all logic for fetching and manipulating the displayed data
 * (including sorting, pagination, and filtering).
 */
export class TableServerDataSource<T> extends DataSource<T> {
  data: T[] = [];
  paginator: MatPaginator;
  sort: MatSort;

  selection = new SelectionModel<ManageUser>(true, []);

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  constructor(protected http: HttpClient) {
    super();
  }

  /**
   * Connect this data source to the table. The table will only update when
   * the returned stream emits new items.
   * @returns A stream of the items to be rendered.
   */
  connect(): Observable<any> {
    // Combine everything that affects the rendered data into one update
    // stream for the data-table to consume.
    return merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.http.get<T[]>('/api/account/user',
            {params: this.buildParams()});
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.length;
          this.data = data;
          return data;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          // Catch if the GitHub API has reached its rate limit. Return empty data.
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      );
  }

  buildParams(): HttpParams {
    const httpParams = this.buildPageParams();
    return this.addSortParams(httpParams);
  }

  addSortParams(httpParams: HttpParams): HttpParams {
    if (this.sort.active) {
      return httpParams = httpParams.set('filter[sort]',
          this.sort.active + ' ' + this.sort.direction);
    }
    return httpParams;
  }

  buildPageParams(): HttpParams {
    let httpParams = new HttpParams();
    httpParams = httpParams.set('pageNumber', this.paginator.pageIndex.toString());
    httpParams = httpParams.set('pageSize', this.paginator.pageSize.toString());
    return httpParams;
  }

  /**
   *  Called when the table is being destroyed. Use this function, to clean up
   * any open connections or free any held resources that were set up during connect.
   */
  disconnect() {}



}

