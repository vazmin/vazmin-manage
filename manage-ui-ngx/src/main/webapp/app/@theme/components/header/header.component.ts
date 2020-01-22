import {Component, Input, OnDestroy, OnInit} from '@angular/core';

import {NbMenuService, NbSidebarService} from '@nebular/theme';
import {AnalyticsService} from '../../../@core/utils/analytics.service';
import {LayoutService} from '../../../@core/data/layout.service';
import {LoginService} from '../auth/login/login.service';
import {interval, Observable, Subscription} from 'rxjs';
import {Principal} from '../../../@core/auth/principal.service';
import {NoticeInfoService} from '../../../@core/data/notice-info.service';
import {filter, map, switchMap} from 'rxjs/operators';
import {Router} from '@angular/router';

@Component({
    selector: 'ngx-header',
    styleUrls: ['./header.component.scss'],
    templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit {

    @Input() position = 'normal';

    user: any;

    userMenu = [{title: '修改密码'}, {title: '退出'}];

    unreadCount;

    unreadPullingTime = 180000;

    constructor(private sidebarService: NbSidebarService,
                private menuService: NbMenuService,
                private principal: Principal,
                // private analyticsService: AnalyticsService,
                private loginService: LoginService,
                private layoutService: LayoutService,
                private noticeInfoService: NoticeInfoService,
                private router: Router) {

    }

    ngOnInit() {
        this.principal.getUserInfo().subscribe((user: any) => this.user = user);
        // this.noticeInfoService.unreadCount().subscribe((data) => this.unreadCount = data.body);
        // this.pullingUnread();

        this.menuService.onItemClick()
            .pipe(
                filter(({ tag }) => tag === 'user-menu'),
                map(({ item: { title } }) => title),
            )
            .subscribe(title => {
                if (title === '修改密码') {
                    this.router.navigateByUrl('/auth/reset-password');
                }
            });
    }

    toggleSidebar(): boolean {
        this.sidebarService.toggle(true, 'menu-sidebar');
        this.layoutService.changeLayoutSize();

        return false;
    }

    // toggleSettings(): boolean {
    //     this.sidebarService.toggle(false, 'settings-sidebar');
    //
    //     return false;
    // }

    toggleNotices() {
        this.sidebarService.toggle(false, 'notices-sidebar');
        return false;
    }

    goToHome() {
        this.menuService.navigateHome();
    }

    // pullingUnread() {
    //     interval(this.unreadPullingTime)
    //         .pipe(
    //             switchMap(() => this.noticeInfoService.unreadCount()
    //                 .pipe(map((data) => data.body))))
    //         .subscribe((data) => {
    //             this.unreadCount = data;
    //         });
    // }

    // startSearch() {
    //   this.analyticsService.trackEvent('startSearch');
    // }
}
