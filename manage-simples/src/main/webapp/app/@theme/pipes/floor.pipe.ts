import {Pipe, PipeTransform} from '@angular/core';
import {floorFixed} from '../../shared/helpers';

@Pipe({name: 'ngxFloor'})
export class FloorPipe implements PipeTransform {

    /**
     * 1.119 => 1.11
     * @param input 输入
     * @param fixed 小数点后位数
     */
    transform(input: number, fixed: number = 2): number {
        return floorFixed(input, fixed);
    }
}
