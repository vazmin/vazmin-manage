import {NgModule} from '@angular/core';

import {PagesRoutingModule} from './pages-routing.module';
import {UserComponent} from './account/user/user.component';
import {PagesComponent} from './pages.component';
import {SimpleComponent} from 'app/layout/simple/simple.component';
import {FooterComponent} from 'app/layout/footer/footer.component';
import {MainComponent} from 'app/layout/main/main.component';
import {TopMenuComponent} from 'app/layout/top-menu/top-menu.component';
import {CdkAccordionModule} from '@angular/cdk/accordion';
import {ThemePickerModule} from 'app/components/theme-picker';
import {MatIconModule} from '@angular/material/icon';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {AppSharedModule} from 'app/shared/shared.module';
import {AioNavMenuComponent} from 'app/components/nav-menu/nav-menu.component';
import {NavItemComponent} from 'app/components/nav-item/nav-item.component';
import {InletComponent} from 'app/components/inlet.component';
import {MatTableModule} from '@angular/material/table';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatMenuModule} from '@angular/material/menu';
import {MatCardModule} from '@angular/material/card';
import {ViewColumnComponent} from 'app/components/view-column/view-column.component';
import {MatSortModule} from '@angular/material/sort';
import {CoreModule} from 'app/core/core.module';
import {TableCheckboxComponent} from 'app/components/table-checkbox/table-checkbox.component';

const LAYOUT_COMPONENT = [
  FooterComponent,
  MainComponent,
  TopMenuComponent,
  AioNavMenuComponent,
  NavItemComponent,
  SimpleComponent,
  InletComponent,
  ViewColumnComponent,
  TableCheckboxComponent,
];

@NgModule({
  declarations: [UserComponent, PagesComponent, ...LAYOUT_COMPONENT],
  imports: [
    AppSharedModule,
    PagesRoutingModule,
    CoreModule,
    MatIconModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatTableModule,
    MatPaginatorModule,
    MatCheckboxModule,
    CdkAccordionModule,
    ThemePickerModule,
    MatMenuModule,
    MatCardModule,
    MatSortModule
  ]
})
export class PagesModule {
}
