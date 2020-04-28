import {Pipe, PipeTransform} from '@angular/core';
import {floorFixed} from 'app/shared/helpers';

@Pipe({name: 'replace'})
export class ReplacePipe implements PipeTransform {

  transform(searchValue: string | undefined,
            toReplace: string, replaceValue: string, flag?: string): string | undefined {
    if (!searchValue || !toReplace || !replaceValue) {
      return searchValue;
    }
    const flagRegExp = flag ? new RegExp(toReplace, flag) : new RegExp(toReplace);
    return searchValue.replace(flagRegExp, replaceValue);
  }
}
