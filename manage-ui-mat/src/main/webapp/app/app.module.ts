import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

// import './vendor';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule} from '@angular/material/icon';
import { SimpleComponent } from './layout/simple/simple.component';
import { FooterComponent } from './layout/footer/footer.component';
import { MainComponent } from './layout/main/main.component';
import { TopMenuComponent } from './layout/top-menu/top-menu.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {HttpClientModule} from '@angular/common/http';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {CdkAccordionModule} from '@angular/cdk/accordion';
import {AioNavMenuComponent} from 'app/shared/components/nav-menu/nav-menu.component';
import {NavItemComponent} from 'app/shared/components/nav-item/nav-item.component';
import {LocationStrategy, PathLocationStrategy} from '@angular/common';
import {LocationService} from 'app/shared/location.service';
import {ScrollService} from 'app/shared/scroll.service';
import {NavigationService} from 'app/shared/components/navigation/navigation.service';
import {ThemePickerModule} from 'app/shared/components/theme-picker';
import { PagesModule } from './pages/pages.module';
import {MatCheckboxModule} from '@angular/material/checkbox';

@NgModule({
    declarations: [
        AppComponent,
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatIconModule,
        MatToolbarModule,
        MatButtonModule,
        MatSidenavModule,
        MatCheckboxModule,
        CdkAccordionModule,
        ThemePickerModule,
        PagesModule
    ],
    providers: [
        {provide: LocationStrategy, useClass: PathLocationStrategy},
        LocationService,
        NavigationService,
        ScrollService,
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
