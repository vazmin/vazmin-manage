import {Component, Injector} from '@angular/core';
import {NoticeInfoService} from '../../../@core/data/notice-info.service';

@Component({
    selector: 'ngx-notice-infinite-list',
    styleUrls: ['./notice-infinite-list.component.scss'],
    template: `
          <nb-list class="own-scroll"
              nbInfiniteList
              [threshold]="10"
              (bottomThreshold)="loadNext()">
            <nb-list-item *ngFor="let notice of notices">
              <nb-card>
                <nb-card-header>
                  {{notice.title}}
                </nb-card-header>
                <nb-card-body>
                  
                  <div [ngSwitch]="notice.type">
                    <div *ngSwitchCase="1 || 2">
                      <h4>{{notice.text.name}}</h4>
                      <p>{{notice.text.remark}}</p>
                      <p>创建人:{{ notice.text.owner && notice.text.owner.username }}</p>
                      <p>执行人:{{ notice.text.executor && notice.text.executor.username }}</p>
                      <p>开始时间:{{ notice.text.startTime * 1000 | date }}</p>
                      <p>开始时间:{{ notice.text.endTime * 1000 | date }}</p>
                    </div>
                    <div *ngSwitchDefault>
                      {{notice.text}}
                    </div>
                  </div>
                </nb-card-body>
                <nb-card-footer>
                  <button class="btn btn-danger btn-xs" (click)="read(notice)"
                          *ngIf="notice.readStatus === 0">标记为已读</button>
                  <button class="btn btn-success btn-xs btn-disabled" disabled="disabled"
                          *ngIf="notice.readStatus === 1">已读</button>
                </nb-card-footer>
              </nb-card>
            </nb-list-item>
            <nb-list-item *ngFor="let _ of placeholders">
              ...
            </nb-list-item>
            <nb-list-item class="text-center">
              没有更多数据
            </nb-list-item>
          </nb-list>
    `,
})
export class NoticeInfiniteListComponent {

    notices = [];
    placeholders = [];
    pageSize = 10;
    pageToLoadNext = 1;
    loading = false;
    noMore = false;

    constructor(private _injector: Injector) {

    }

    /**
     * 任务通知，加载数据
     * * *************************************************************
     * 名称: xxx
     * 备注： xxx
     * 开始日期：xxx
     * 结束日期：xxx
     * 创建人： xxx
     * 执行人： xxx
     * *************************************************************
     */
    loadNext() {

        if (this.loading || this.noMore) {
            return;
        }
        this.loading = true;
        this.placeholders = new Array(this.pageSize);
        // const noticeInfoService = this._injector.get(NoticeInfoService);
        // noticeInfoService.list({
        //     pageNumber: this.pageToLoadNext,
        //     pageSize: this.pageSize,
        //     'filter[fullordering]': 'a.id DESC'
        // }).subscribe((response) => {
        //
        //     const total = response.headers.get('x-total-count');
        //     const data = response.body;
        //     data.forEach(t => {
        //         if (t.type === 1) {
        //             t.text = JSON.parse(t.text);
        //         }
        //     });
        //     this.placeholders = [];
        //     this.notices.push(...data);
        //     this.loading = false;
        //     this.nowIsNoMore(total);
        //     this.pageToLoadNext++;
        // });
    }

    read(notice) {
        const noticeInfoService = this._injector.get(NoticeInfoService);
        noticeInfoService.read({id: notice.id}).subscribe(() => {
            notice.readStatus = 1;
        });
    }
    
    nowIsNoMore(total: number) {
        this.noMore = this.pageSize * this.pageToLoadNext > total;
    }
}
