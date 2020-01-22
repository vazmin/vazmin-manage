import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { Principal } from './principal.service';

/**
 * @whatItDoes Conditionally includes an HTML element if current user has any
 * of the authorities passed as the `expression`.
 *
 * @howToUse
 * ```
 *     <some-element *hasAnyAuthority="'ROLE_ADMIN'">...</some-element>
 *
 *     <some-element *hasAnyAuthority="['ROLE_ADMIN', 'ROLE_USER']">...</some-element>
 * ```
 */
/* tslint:disable */
@Directive({
    selector: '[hasAllPermission]'
})
/* tslint:enable */
export class HasAllPermissionDirective {

    private permission: string[];

    // private replace: boolean;

    constructor(private principal: Principal,
                private templateRef: TemplateRef<any>,
                private viewContainerRef: ViewContainerRef) {
    }

    @Input()
    set hasAllPermission(value: string|string[]) {
        this.permission = typeof value === 'string' ? [ <string> value ] : <string[]> value;
        this.updateView();
        // Get notified each time authentication state changes.
        this.principal.getAuthenticationState().subscribe((identity) => this.updateView());
    }

    private updateView(): void {
        this.principal.hasAllPermission(this.permission).then((result) => {
            this.viewContainerRef.clear();
            if (result) {
                this.viewContainerRef.createEmbeddedView(this.templateRef);
            }
        });
    }
}
