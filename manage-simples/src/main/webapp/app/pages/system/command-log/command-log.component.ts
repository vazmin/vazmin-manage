import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Principal} from '../../../@core/auth/principal.service';
import {StateStorageService} from '../../../@core/auth/state-storage.service';
import {CommandLogService} from './command-log.service';
import {getLongDateTimeViewOption} from '../../helpers';
import {AbstractManageListComponent} from '../../../shared/manage/abstract-manage-list.component';

@Component({
    selector: 'ngx-command-log',
    templateUrl: './command-log.component.html',
    styleUrls: ['./command-log.component.scss'],
//    providers: [{provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}]
})
export class CommandLogComponent extends AbstractManageListComponent {

    manageConfig = {
        endPoint : '/api/system/command-log',
        columns : {
            // id: {
            //     title: '记录id',
            //     type: 'string'
            // },
            // userId: {
            //     title: '用户id',
            //     type: 'string'
            // },
            name: {
                title: '姓名',
                type: 'string'
            },
            // commandId: {
            //     title: '命令id',
            //     type: 'string'
            // },
            commandName: {
                title: '命令名称',
                type: 'string'
            },
            moduleName: {
                title: '模块名称',
                type: 'string'
            },
            requestPath: {
                title: '请求路径',
                type: 'string'
            },
            // parameters: {
            //     title: '请求参数',
            //     type: 'string'
            // },
            // userIp: {
            //     title: '用户IP',
            //     type: 'string'
            // },
            resultStr: {
                title: '操作是否成功',
                type: 'string',
                width: '1px'
            },
            resultMessage: {
                title: '操作提示信息',
                type: 'string'
            },
            actionTime: getLongDateTimeViewOption('操作时间'),
        },
        initData: {
            sortConf: [{
                direction: 'desc',
                field: 'actionTime',
            }]
        }
    };


    constructor(http: HttpClient,
                principal: Principal,
                protected route: ActivatedRoute,
                protected router: Router,
                private commandLogService: CommandLogService,
                protected stateStorageService: StateStorageService) {
        super(http, principal, commandLogService, route, router, stateStorageService);
        this.init();
    }


    setQueryFilters() {
        this.queryFilter['name'] = null;
    }
}
