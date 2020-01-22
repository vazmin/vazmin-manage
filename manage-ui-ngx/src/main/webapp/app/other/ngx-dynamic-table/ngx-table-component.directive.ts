import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
    selector: '[ngxTableComponent]',
})
export class NgxTableComponentDirective {
    constructor(public viewContainerRef: ViewContainerRef) { }
}
