import { Pipe, PipeTransform } from '@angular/core';
import {getDescription} from '../../shared/helpers';

@Pipe({ name: 'getDescription' })
export class GetDescriptionByValuePipe implements PipeTransform {

    transform(input: number, array: any[]): string {
        return getDescription(input, array);
    }
}
