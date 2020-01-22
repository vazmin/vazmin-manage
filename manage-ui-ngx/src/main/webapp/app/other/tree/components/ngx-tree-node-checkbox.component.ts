import {Component, Input, ViewEncapsulation} from '@angular/core';
import {TreeNode, TreeNodeCheckboxComponent} from 'angular-tree-component';

/**
 * tree 自定义复选框
 */
@Component({
    selector: 'ngx-tree-node-checkbox',
    encapsulation: ViewEncapsulation.None,
    styles: [],
    template: `
      <ng-container *mobxAutorun="{dontDetach: true}">
        <div class="custom-control custom-checkbox" [class.checked-gt0]="node.isPartiallySelected">
            <input type="checkbox" class="custom-control-input" id="tree-cb-{{tid}}-{{node.position}}"
                   (click)="node.mouseAction('checkboxClick', $event)"
                   [value]="node.isSelected"
                   [checked]="node.isSelected"
                   [indeterminate]="node.isPartiallySelected"
            >
            <label class="custom-control-label" for="tree-cb-{{tid}}-{{node.position}}">
              <ng-content></ng-content>
            </label>
        </div>
      </ng-container>
    `
})
export class NgxTreeNodeCheckboxComponent extends TreeNodeCheckboxComponent {
    @Input() tid: number;
    @Input() node: TreeNode;
}
