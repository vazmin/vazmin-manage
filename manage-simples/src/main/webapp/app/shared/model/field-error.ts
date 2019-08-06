export class FieldError {
    private _field: string;

    private _message: string;

    private _objectName: string;

    constructor(field: string, message: string, objectName) {
        this._field = field;
        this._message = message;
        this._objectName = objectName;
    }


    get field(): string {
        return this._field;
    }

    get message(): string {
        return this._message;
    }

    get objectName(): string {
        return this._objectName;
    }
}
