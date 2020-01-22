import {getTableSetting, getTableSourceConf} from '../table-config';
import {ServerDataSource} from '../../../@theme/ng2-smart-table';
import {HttpClient} from '@angular/common/http';

const userManageConfig = {
  endPoint : '/api/open/account-user',
  columns : {
      username: {
      title: '系统用户名（邮箱）',
      type: 'string'
    },
    name: {
      title: '姓名',
      type: 'string'
    }
  }
};

const userSetting = getTableSetting({
  selectMode: '',
  actions: {
    add: false,
    edit: false,
    detail: false,
    delete: false,
  },
  columns: userManageConfig.columns
});


const userSourceConf = getTableSourceConf({
  endPoint: userManageConfig.endPoint
});


export function getUserSetting(http: HttpClient, format: any, filter: any) {
  const userSource = new ServerDataSource(http, userSourceConf);
  userSource.setFilter(filter, false);
  return {
    context: {
      config: {
        title: '点击选择用户',
        setting: userSetting,
        source: userSource,
        onSelect: format,
        search: [{name: 'name', placeholder: '用户名称'}]
      }
    }
  };
}
