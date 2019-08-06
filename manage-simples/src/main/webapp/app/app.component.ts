/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import {Component, NgZone, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {
    NbGlobalPhysicalPosition,
    NbMenuService,
    NbToastrService
} from '@nebular/theme';
import {LoginService} from './@theme/components/auth/login/login.service';
import {SwUpdate} from '@angular/service-worker';
import {interval} from 'rxjs';
// import { AnalyticsService } from './@core/utils/analytics.service';

@Component({
  selector: 'ngx-app',
  template: '<router-outlet></router-outlet>',
})
export class AppComponent implements OnInit {

  constructor(translate: TranslateService,
              menuService: NbMenuService,
              loginService: LoginService,
              private toastrService: NbToastrService,
              private swUpdate: SwUpdate,
              private ngZone: NgZone) {
      // this language will be used as a fallback when a translation isn't found in the current language
      translate.setDefaultLang('zh-cn');

      // the lang to use, if the lang isn't available, it will use the current loader to get them
      translate.use('zh-cn');

      menuService.onItemClick()
          .subscribe((event) => {
              if (event.item.title !== '退出') {
                  return;
              }
              loginService.logout();
          });
  }

  ngOnInit(): void {

      if (this.swUpdate.isEnabled) {
          this.swUpdate.checkForUpdate().then(() => {});
          this.swUpdate.available.subscribe(event => {
              console.log('current version is', event.current);
              console.log('available version is', event.available);
              this.toastrService.info('有可用更新，请停止操作并耐心等待，稍后自动重启！', '准备更新',
                  {position: NbGlobalPhysicalPosition.TOP_RIGHT});
              setTimeout(() => document.location.reload(), 1000);
          });
          this.swUpdate.activated.subscribe(event => {
              console.log('old version was', event.previous);
              console.log('new version is', event.current);
          });

          this.ngZone.runOutsideAngular(() => {
              interval(6 * 60 * 60).subscribe(() => {
                  this.ngZone.run(() => this.swUpdate.checkForUpdate().then(() => {}));
              });
          });
      }
  }
}
