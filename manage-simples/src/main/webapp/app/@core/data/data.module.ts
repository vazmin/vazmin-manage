import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import {StateService} from './state.service';
import {LayoutService} from './layout.service';
import {NoticeInfoService} from './notice-info.service';


const SERVICES = [
  StateService,
  LayoutService,
    NoticeInfoService
];

@NgModule({
  imports: [
    CommonModule,
  ],
  providers: [
    ...SERVICES,
  ],
})
export class DataModule {
  static forRoot(): ModuleWithProviders {
    return <ModuleWithProviders>{
      ngModule: DataModule,
      providers: [
        ...SERVICES,
      ],
    };
  }
}
