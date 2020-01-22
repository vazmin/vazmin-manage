import {Pipe, PipeTransform} from '@angular/core';

@Pipe({ name: 'ngxParseDate' })
export class ParseDatePipe implements PipeTransform {
    //
    transform(value: string, type: number = 1): any {
        switch (type) {
            case 1:
                return ParseDatePipe.transformYMD(value);
        }
    }

    // 2019-03-11 to Date
    static transformYMD(value: string): Date {
        const da = value.split('-');
        return new Date(+da[0], +da[1] - 1, +da[2]);
    }
}
