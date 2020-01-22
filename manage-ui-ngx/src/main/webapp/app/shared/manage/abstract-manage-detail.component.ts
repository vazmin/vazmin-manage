import {OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute, Router} from '@angular/router';
import {ManageServiceInterface} from './manage-service-interface';

/**
 * 详情管理组件抽象类
 */
export abstract class AbstractManageDetailComponent implements OnInit, OnDestroy {

    protected subscription: Subscription;
    submitting: boolean = false;
    protected constructor(protected manageService: ManageServiceInterface,
                          protected route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.subscription = this.route.queryParams.subscribe((params) => {
            params[this.getParamIdName()] ? this.load(params[this.getParamIdName()]) : this.setData({});
        });
    }

    getParamIdName() {
        return 'id';
    }

    load(id) {
        this.manageService.detail(id).subscribe(response => {
            this.setData(response.body);
        });
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

    /**
     * 载入完成会将数据返回
     */
    abstract setData(data);
}
