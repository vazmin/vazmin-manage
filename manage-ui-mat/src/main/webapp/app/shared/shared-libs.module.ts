import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  exports: [
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
  ]
})
export class AppSharedLibsModule {}
