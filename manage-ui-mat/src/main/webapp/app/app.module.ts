import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

// import './vendor';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LocationStrategy, PathLocationStrategy} from '@angular/common';
import {CoreModule} from 'app/core/core.module';
import {AppSharedModule} from 'app/shared/shared.module';


@NgModule({
  declarations: [
    AppComponent,


  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AppSharedModule,
    CoreModule,
  ],
  providers: [
    {provide: LocationStrategy, useClass: PathLocationStrategy},

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
