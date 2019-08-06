import {CustomFormatViewComponent} from '../@theme/ng2-smart-table/custom/custom-format-view.component';
import {DateFormat} from '../shared/model/base-constants';
import {formatDateZh, getDescription} from '../shared/helpers';
import {ToggleIconViewComponent} from '../@theme/ng2-smart-table/custom/toggle-icon-view.component';
import {CustomColorViewComponent} from '../@theme/ng2-smart-table/custom/custom-color-view.component';
import {CustomFillRateViewComponent} from '../@theme/ng2-smart-table/custom/custom-fill-rate-view.component';

export function getLongDateTimeViewOption (title: string) {
    return getDateViewOption(title, DateFormat.LONG_TIME);
}

export function getShortDateTimeViewOption (title: string) {
  return getDateViewOption(title, DateFormat.SHORT_TIME);
}

export function getLongDateViewOption (title: string) {
    return getDateViewOption(title, DateFormat.DATE);
}

export function getDateViewOption (title: string, dateFormat: DateFormat) {
    return getFormatViewOption(title,
        (value) => {
            return formatDateZh(value * 1000, dateFormat);
        });
}


export function getDescriptionViewOption(title: string, array: any[]) {
    return getFormatViewOption(title,
        (value) => {
        return getDescription(value, array);
    });
}

export function getFormatViewOption(title: string, format: any, multiLine: boolean = false) {
    return  {
        title: title,
        type: 'custom',
        class: 'text-nowrap',
        renderComponent: CustomFormatViewComponent,
        extData: {
            format: format,
            multi: multiLine
        }
    };
}

export function getDescriptionColorViewOption(title: string, array: any[]) {
  return getFormatColorViewOption(title,
    (value) => {
      return getDescription(value, array);
    });
}

export function getFormatColorViewOption(title: string, format: any) {
  return {
    title: title,
    type: 'custom',
    class: 'text-nowrap',
    renderComponent: CustomColorViewComponent,
    extData: {
      format: format
    }
  };
}

export function getFillRateViewOption(title: string) {
    return {
        title: title,
        type: 'custom',
        class: 'text-nowrap',
        renderComponent: CustomFillRateViewComponent
    };
}

export function getToggleIconViewOption(title: string, extData: any, callback) {
    return {
        title: title,
        type: 'custom',
        class: 'ng2-smart-actions text-center',
        extData: extData,
        renderComponent: ToggleIconViewComponent,
        onComponentInitFunction: (instance) => {
            instance.save.subscribe(row => {
                callback(row);
            });
        }
    };
}

export function buildIdName(id, name) {
    return id + (name ? '_' + name : '');
}
