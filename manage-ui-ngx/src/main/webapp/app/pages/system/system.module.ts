import {NgModule} from '@angular/core';
import {ThemeModule} from '../../@theme/theme.module';
import {routedComponents, SystemRoutingModule} from './system-routing.module';
import {CommandInfoService} from './command-info/command-info.service';
import {MenuInfoService} from './menu-info/menu-info.service';
import {ModuleInfoService} from './module-info/module-info.service';
import {CommandLogService} from './command-log/command-log.service';
import {SystemMessageService} from './system-message/system-message.service';
import {SystemNoticeLogService} from './system-notice-log/system-notice-log.service';


@NgModule({
    imports: [
        ThemeModule,
        SystemRoutingModule,
    ],
    declarations: [
        ...routedComponents
    ],
    providers: [
        CommandInfoService,
        MenuInfoService,
        ModuleInfoService,
        CommandLogService,
        SystemMessageService,
        SystemNoticeLogService
    ]
})
export class SystemModule {
}
