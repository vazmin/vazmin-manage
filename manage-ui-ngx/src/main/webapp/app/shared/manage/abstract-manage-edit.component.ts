import {OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, ActivatedRouteSnapshot, Router} from '@angular/router';
import {ManageServiceInterface} from './manage-service-interface';
import {AbstractManageDetailComponent} from './abstract-manage-detail.component';

/**
 * 编辑组件 抽象类
 */
export abstract class AbstractManageEditComponent extends AbstractManageDetailComponent implements OnInit, OnDestroy {

    protected constructor(protected manageService: ManageServiceInterface,
                          protected router: Router,
                          protected route: ActivatedRoute) {
        super(manageService, route);
    }

    saveBefore() {}

    /**
     * 保存
     * 新增或更新
     */
    save() {
        this.submitting = true;
        this.saveBefore();
        const data = this.getData();
        if (data.id) {
          this.manageService.update(data).subscribe(res => this.submitting = false);
        } else {
          this.manageService.create(data).subscribe((response) => {
            this.router.navigate(['../detail'],
              {relativeTo: this.route, queryParams: {id: response.body.id}}).then(() => {
            });
          });
        }
    }

    /**
     * 保存新增
     * @param editForm
     * @param params 路由参数
     * @param form
     */
    saveAndNew(params?: any) {
        this.saveNewBefore();
        const scb = (response) => {
            this.router.navigate(['../new'],
                {relativeTo: this.route, queryParams: params}).then(() => {
            });
        };
        const data = this.getData();
        if (data.id) {
            this.manageService.update(data).subscribe(scb);
        } else {
            this.manageService.create(data).subscribe(scb);
        }
    }

    /**
     * 保存新增前操作
     */
    saveNewBefore() {}

    abstract getData();

}
