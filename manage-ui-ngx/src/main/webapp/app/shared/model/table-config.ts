import {deepClone} from '../helpers';

export const TABLE_SETTINGS = {
    selectMode: 'multi',
    mode: 'external',
    hideSubHeader: true,
    actions: {
        add: true,
        edit: true,
        detail: true,
        delete: true,
        columnTitle: '操作',
    },
    add: {
        addButtonContent: '<i class="nb-plus"></i>',
        createButtonContent: '<i class="nb-checkmark"></i>',
        cancelButtonContent: '<i class="nb-close"></i>',
    },
    edit: {
        editButtonContent: '<i class="icon ion-md-create"></i>',
        saveButtonContent: '<i class="nb-checkmark"></i>',
        cancelButtonContent: '<i class="nb-close"></i>',
    },
    delete: {
        deleteButtonContent: '<i class="icon ion-md-trash"></i>',
        confirmDelete: true,
    },
    detail: {
        detailButtonContent: '<i class="icon ion-md-eye"></i>',
    },
    columns: {},
    pager: {
        page: 1,
        perPage: 10,
        perPages: [
            5, 10, 20, 50
        ]
    },
    noDataMessage: '未找到记录!'
};
export const TABLE_CONF = {
    pagerPageKey: 'pageNumber',
    pagerLimitKey: 'pageSize',
    sortFieldKey: 'fullordering',
    endPoint: '/'
};

/**
 * 获取表格配置，传入对象将覆盖默认值
 * @param setting 自定义配置
 */
export function getTableSetting(setting = {}) {
    return Object.assign(deepClone(TABLE_SETTINGS), setting);
}

/**
 * 获取表数据源配置，传入对象将覆盖默认值
 * @param conf 自定义配置
 */
export function getTableSourceConf(conf = {}) {
    return Object.assign(deepClone(TABLE_CONF), conf);
}
