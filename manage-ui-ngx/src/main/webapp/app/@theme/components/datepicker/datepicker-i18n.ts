import { Injectable} from '@angular/core';
import {NgbDatepickerI18n, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {LANGUAGES} from '../../../shared/language/language.constants';

const I18N_VALUES = {
    'zh-cn': {
        weekdays: ['一', '二', '三', '四', '五', '六', '日'],
        months: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
    },
    // other languages you would support
};

// Define a service holding the language. You probably already have one if your app is i18ned. Or you could also
// use the Angular LOCALE_ID value

@Injectable()
export class CustomDatepickerI18n extends NgbDatepickerI18n {
    getDayAriaLabel(date: NgbDateStruct): string {
      return undefined;
    }

    constructor() {
        super();
    }

    getWeekdayShortName(weekday: number): string {
        return I18N_VALUES[LANGUAGES[0]].weekdays[weekday - 1];
    }
    getMonthShortName(month: number): string {
        return I18N_VALUES[LANGUAGES[0]].months[month - 1];
    }
    getMonthFullName(month: number): string {
        return this.getMonthShortName(month);
    }
}


