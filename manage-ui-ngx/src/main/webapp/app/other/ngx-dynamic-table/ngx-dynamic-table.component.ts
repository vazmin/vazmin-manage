import {
    Component,
    Input,
    ViewChild,
    ComponentFactoryResolver,
    OnDestroy, OnChanges, SimpleChanges, OnInit
} from '@angular/core';
import {NgxTableComponentDirective} from './ngx-table-component.directive';
import {TableItem} from './table-item';



@Component({
    selector: 'ngx-dynamic-table',
    template: `
      <ng-template ngxTableComponent></ng-template>
    `
})
export class NgxDynamicTableComponent implements OnDestroy, OnChanges, OnInit {
    @Input() item: TableItem;
    @ViewChild(NgxTableComponentDirective) dynamicSelect: NgxTableComponentDirective;
    interval: any;

    constructor(private componentFactoryResolver: ComponentFactoryResolver) {
    }

    ngOnDestroy() {
        clearInterval(this.interval);
    }

    loadComponent() {
        const selectItem = this.item;
        console.log(this.item);
        const componentFactory =
            this.componentFactoryResolver.resolveComponentFactory(selectItem.component);
        const viewContainerRef = this.dynamicSelect.viewContainerRef;
        viewContainerRef.clear();
        const componentRef = viewContainerRef.createComponent(componentFactory);
        (<TableItem>componentRef.instance).data = selectItem.data;
    }

    ngOnChanges(changes: SimpleChanges): void {

    }

    ngOnInit(): void {
        this.loadComponent();
    }
}
