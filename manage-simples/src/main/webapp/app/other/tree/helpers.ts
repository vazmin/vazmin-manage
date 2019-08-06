import {TreeModel} from 'angular-tree-component';

export function initChecked (nodes, treeModel: TreeModel) {
    nodes.forEach(node => {
        if (node.children && node.children.length > 0) {
            initChecked(node.children, treeModel);
        } else {
            if (node.checked) {
                treeModel.getNodeById(node.id).setIsSelected(true);
            }
        }
    });
}
