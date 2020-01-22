/**
 * Extending object that entered in first argument.
 *
 * Returns extended object or false if have no target object or incorrect type.
 *
 * If you wish to clone source object (without modify it), just use empty new
 * object as first argument, like this:
 *   deepExtend({}, yourObj_1, [yourObj_N]);
 */
import {formatDate} from '@angular/common';
import {DateFormat} from './model/base-constants';

export const deepExtend = function (...objects: any[]): any {
  if (arguments.length < 1 || typeof arguments[0] !== 'object') {
    return false;
  }

  if (arguments.length < 2) {
    return arguments[0];
  }

  const target = arguments[0];

  // convert arguments to array and cut off target object
  const args = Array.prototype.slice.call(arguments, 1);

  let val, src;

  args.forEach(function (obj: any) {
    // skip argument if it is array or isn't object
    if (typeof obj !== 'object' || Array.isArray(obj)) {
      return;
    }

    Object.keys(obj).forEach(function (key) {
      src = target[key]; // source value
      val = obj[key]; // new value

      // recursion prevention
      if (val === target) {
        return;

        /**
         * if new value isn't object then just overwrite by new value
         * instead of extending.
         */
      } else if (typeof val !== 'object' || val === null) {
        target[key] = val;

        return;

        // just clone arrays (and recursive clone objects inside)
      } else if (Array.isArray(val)) {
        target[key] = deepCloneArray(val);

        return;

        // custom cloning and overwrite for specific objects
      } else if (isSpecificValue(val)) {
        target[key] = cloneSpecificValue(val);

        return;

        // overwrite by new value if source isn't object or array
      } else if (typeof src !== 'object' || src === null || Array.isArray(src)) {
        target[key] = deepExtend({}, val);

        return;

        // source value and new value is objects both, extending...
      } else {
        target[key] = deepExtend(src, val);

        return;
      }
    });
  });

  return target;
};

function isSpecificValue(val: any) {
  return (
      val instanceof Date
      || val instanceof RegExp
  );
}

function cloneSpecificValue(val: any): any {
  if (val instanceof Date) {
    return new Date(val.getTime());
  } else if (val instanceof RegExp) {
    return new RegExp(val);
  } else {
    throw new Error('cloneSpecificValue: Unexpected situation');
  }
}

/**
 * Recursive cloning array.
 */
export function deepCloneArray(arr: any[]): any {
  const clone: any[] = [];
  arr.forEach(function (item: any, index: any) {
    if (typeof item === 'object' && item !== null) {
      if (Array.isArray(item)) {
        clone[index] = deepCloneArray(item);
      } else if (isSpecificValue(item)) {
        clone[index] = cloneSpecificValue(item);
      } else {
        clone[index] = deepExtend({}, item);
      }
    } else {
      clone[index] = item;
    }
  });

  return clone;
}

// getDeepFromObject({result: {data: 1}}, 'result.data', 2); // returns 1
export function getDeepFromObject(object = {}, name: string, defaultValue?: any) {
  const keys = name.split('.');
  // clone the object
  let level = deepExtend({}, object || {});
  keys.forEach((k) => {
    if (level && typeof level[k] !== 'undefined') {
      level = level[k];
    } else {
      level = undefined;
    }
  });

  return typeof level === 'undefined' ? defaultValue : level;
}

export function urlBase64Decode(str: string): string {
  let output = str.replace(/-/g, '+').replace(/_/g, '/');
  switch (output.length % 4) {
    case 0: { break; }
    case 2: { output += '=='; break; }
    case 3: { output += '='; break; }
    default: {
      throw new Error('Illegal base64url string!');
    }
  }
  return b64DecodeUnicode(output);
}

export function b64decode(str: string): string {
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
  let output: string = '';

  str = String(str).replace(/=+$/, '');

  if (str.length % 4 === 1) {
    throw new Error(`'atob' failed: The string to be decoded is not correctly encoded.`);
  }

  for (
    // initialize result and counters
    let bc: number = 0, bs: any, buffer: any, idx: number = 0;
    // get next character
    buffer = str.charAt(idx++);
    // character found in table? initialize bit storage and add its ascii value;
    ~buffer && (bs = bc % 4 ? bs * 64 + buffer : buffer,
      // and if not first of each 4 characters,
      // convert the first 8 bits to one ascii character
    bc++ % 4) ? output += String.fromCharCode(255 & bs >> (-2 * bc & 6)) : 0
  ) {
    // try to find character in table (0-63, not found => -1)
    buffer = chars.indexOf(buffer);
  }
  return output;
}

// https://developer.mozilla.org/en/docs/Web/API/WindowBase64/Base64_encoding_and_decoding#The_Unicode_Problem
export function b64DecodeUnicode(str: any) {
  return decodeURIComponent(Array.prototype.map.call(b64decode(str), (c: any) => {
    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));
}

/**
 * 格式化日期
 * @param value
 * @param format
 */
export function formatDateZh(value: string | number | Date, format: string): string {
    return formatDate(value, format, 'zh-Hans');
}

/**
 * 获取描述
 * {value: 1, description: '描述'}
 * return description
 * @param input 1
 * @param array [{value: 1, description: '描述'}]
 */
export function getDescription(input: number, array: any[]) {
    return array ? array.filter(o => o.value === input).pop().description : input;
}

/**
 * 深度克隆
 * @param obj
 */
export function deepClone (obj) {
    const clone = Object.assign({}, obj);
    Object.keys(clone).forEach(
        key => (clone[key] = typeof obj[key] === 'object' ? deepClone(obj[key]) : obj[key])
    );
    return Array.isArray(obj) ? (clone.length = obj.length) && Array.from(clone) : clone;
}

/**
 * 抹零
 * @param input 输入
 * @param fixed 小数点后位数
 */
export function floorFixed(input: number, fixed: number = 2): number {
    if (input || input === 0) {
        const cof = Math.pow(10, fixed);
        return Math.floor(input * cof) / cof;
    }
    return input;
}

function getField(name: String, defaultValue = ''): String {
    if (name === 'rangeDateStart') {
        return 'beginDate';
    } else if (name === 'rangeDateEnd') {
        return 'endDate';
    }
    return defaultValue;
}

export function rangeDateFilter(rv): Array<any> {
    const fA = [];
    if (rv.start) {
        fA.push({
            field: getField('rangeDateStart'),
            search: formatDateZh(rv.start, DateFormat.DATE)
        });
    }
    if (rv.end) {
        fA.push({
            field: getField('rangeDateEnd'),
            search: formatDateZh(rv.end, DateFormat.DATE)
        });
    }
    return fA;
}

export function rangeDateCleanFilter(): Array<any> {
    return [{
        field: getField('rangeDateStart'),
        search: null
    }, {
        field: getField('rangeDateEnd'),
        search: null
    }];
}

export function getFirstDayOfMonth(date = new Date()): Date {
    date.setDate(1);
    return date;
}
