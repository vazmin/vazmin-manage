import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

// import './vendor';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule} from '@angular/material/icon';
import {HttpClientModule} from '@angular/common/http';
import {LocationStrategy, PathLocationStrategy} from '@angular/common';
import {LocationService} from 'app/shared/location.service';
import {ScrollService} from 'app/shared/scroll.service';
import {NavigationService} from 'app/components/navigation/navigation.service';
import {AuthComponent} from 'app/shared/auth/auth.component';
import {AuthBlockComponent} from 'app/shared/auth/auth-block/auth-block.component';
import {LoginComponent} from 'app/shared/auth/login/login.component';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatTooltipModule} from '@angular/material/tooltip';
import {CoreModule} from 'app/core/core.module';
import {AppSharedModule} from 'app/shared/shared.module';
import {NgxWebstorageModule} from 'ngx-webstorage';
import {AuthGuard} from 'app/core/security/auth-guard.service';
import {Logger} from 'app/shared/logger.service';


@NgModule({
  declarations: [
    AppComponent,

  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AppSharedModule,
    CoreModule,
  ],
  providers: [
    {provide: LocationStrategy, useClass: PathLocationStrategy},
    LocationService,
    NavigationService,
    ScrollService,
    Logger
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
