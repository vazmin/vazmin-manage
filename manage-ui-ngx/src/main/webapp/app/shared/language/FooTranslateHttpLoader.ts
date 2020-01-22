import {TranslateLoader} from '@ngx-translate/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';

export class FooTranslateHttpLoader implements TranslateLoader {
    private http;
    prefix: string;
    suffix: string;
    constructor(http: HttpClient, prefix?: string, suffix?: string) {
        if (prefix === void 0) { prefix = '/assets/i18n/'; }
        if (suffix === void 0) { suffix = '.json'; }
        this.http = http;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    getTranslation(lang: string): Observable<any> {
        return this.http.get('' + this.prefix + lang + this.suffix);
    }

}
