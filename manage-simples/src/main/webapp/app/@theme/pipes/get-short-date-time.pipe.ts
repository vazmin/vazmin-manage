import {Pipe, PipeTransform} from '@angular/core';
import {formatDateZh} from '../../shared/helpers';
import {DateFormat} from '../../shared/model/base-constants';

@Pipe({name: 'getShortDateTime'})
export class GetShortDateTimePipe implements PipeTransform {

  transform(time: number): Object {
    return formatDateZh(time * 1000, DateFormat.SHORT_TIME);
  }

}
