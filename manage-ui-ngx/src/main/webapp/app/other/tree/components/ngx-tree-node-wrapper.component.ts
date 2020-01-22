import {Component, Input} from '@angular/core';
import {TreeNodeWrapperComponent} from 'angular-tree-component/dist/components/tree-node-wrapper.component';

@Component({
    selector: 'ngx-tree-node-wrapper',
    template: `
      <div class="node-wrapper" [style.padding-left]="node.getNodePadding()">
        <tree-node-expander [node]="node"></tree-node-expander>
        <ngx-tree-node-checkbox *ngIf="node.options.useCheckbox" [node]="node" [tid]="tid">
          <div class="node-content-wrapper"
               [class.node-content-wrapper-active]="node.isActive"
               [class.node-content-wrapper-focused]="node.isFocused"
               (click)="node.mouseAction('click', $event)"
               (dblclick)="node.mouseAction('dblClick', $event)"
               (contextmenu)="node.mouseAction('contextMenu', $event)"
               (treeDrop)="node.onDrop($event)"
               [treeAllowDrop]="node.allowDrop"
               [treeDrag]="node"
               [treeDragEnabled]="node.allowDrag()">
            <tree-node-content [node]="node" [index]="index"></tree-node-content>
          </div>
        </ngx-tree-node-checkbox>
      </div>
    `
})

export class NgxTreeNodeWrapperComponent extends TreeNodeWrapperComponent {
    @Input() tid: number;
}
