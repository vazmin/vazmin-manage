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
import {MatButtonModule} from '@angular/material/button';
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
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {AuthGuard} from 'app/core/security/auth-guard.service';

const LAYOUT_COMPONENT = [
  FooterComponent,
  MainComponent,
  TopMenuComponent,
  AioNavMenuComponent,
  NavItemComponent,
  SimpleComponent,
  InletComponent,
  ViewColumnComponent
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
    MatMenuModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule
  ]
})
export class PagesModule {
}
