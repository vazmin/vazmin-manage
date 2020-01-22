import {FieldError} from './field-error';

export class Problem {
    private _type: string;

    private _title: string;

    private _status: number;

    private _path: string;

    private _message: string;

    private _fieldErrors: FieldError[];


    constructor(type: string, title: string, status: number, path: string, message: string, fieldErrors: FieldError[]) {
        this._type = type;
        this._title = title;
        this._status = status;
        this._path = path;
        this._message = message;
        this._fieldErrors = fieldErrors;
    }


    get type(): string {
        return this._type;
    }

    get title(): string {
        return this._title;
    }

    get status(): number {
        return this._status;
    }

    get path(): string {
        return this._path;
    }

    get message(): string {
        return this._message;
    }

    get fieldErrors(): FieldError[] {
        return this._fieldErrors;
    }
}
