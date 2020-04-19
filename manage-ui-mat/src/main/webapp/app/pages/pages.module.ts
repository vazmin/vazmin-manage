import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PagesRoutingModule} from './pages-routing.module';
import {AccountModule} from './account/account.module';
import {UserComponent} from './account/user/user.component';
import {PagesComponent} from './pages.component';
import {SimpleComponent} from 'app/layout/simple/simple.component';
import {FooterComponent} from 'app/layout/footer/footer.component';
import {MainComponent} from 'app/layout/main/main.component';
import {TopMenuComponent} from 'app/layout/top-menu/top-menu.component';
import {CdkAccordionModule} from '@angular/cdk/accordion';
import {ThemePickerModule} from 'app/shared/components/theme-picker';
import {HttpClientModule} from '@angular/common/http';
import {MatIconModule} from '@angular/material/icon';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {AppSharedModule} from 'app/shared/shared.module';
import {AioNavMenuComponent} from 'app/shared/components/nav-menu/nav-menu.component';
import {NavItemComponent} from 'app/shared/components/nav-item/nav-item.component';
import {InletComponent} from 'app/shared/components/inlet.component';
import {MatTableModule} from '@angular/material/table';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatPaginatorModule} from '@angular/material/paginator';

const LAYOUT_COMPONENT = [
  FooterComponent,
  MainComponent,
  TopMenuComponent,
  AioNavMenuComponent,
  NavItemComponent,
  SimpleComponent,
  InletComponent
];

@NgModule({
  declarations: [UserComponent, PagesComponent, ...LAYOUT_COMPONENT],
  imports: [
    AppSharedModule,
    PagesRoutingModule,

    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    MatTableModule,
    MatPaginatorModule,
    MatCheckboxModule,
    CdkAccordionModule,
    ThemePickerModule,
    AccountModule
  ]
})
export class PagesModule {
}
