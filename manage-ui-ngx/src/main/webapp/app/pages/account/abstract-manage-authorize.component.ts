
import {ActivatedRoute} from '@angular/router';
import {
    ITreeOptions,
    TreeComponent,
    TreeModel,
    TreeNode
} from 'angular-tree-component';
import {initChecked} from '../../other/tree/helpers';
import {IManageAuthorizeService} from '../../shared/manage/manage-authorize-service-interface';
import {log} from 'util';

export class AbstractManageAuthorizeComponent {
    /** 用户权限 */
    userPrivilege: any;
    /** 用户权限id */
    privilegeSet: any;
    /** 用户id */
    id: any;
    /** Tree 选项 */
    options: ITreeOptions = {
        useCheckbox: true,
        allowDrag: false,
        allowDrop: false,
        // useVirtualScroll: true,
        // nodeHeight: 15
    };
    treeLoading = true;

    activeNode: any;
    constructor(protected service: IManageAuthorizeService,
                protected route: ActivatedRoute) {
    }

    /**
     * 展开树节点，根据数据勾选
     * @param tree
     */
    onTreeInit(tree: TreeComponent) {
        tree.treeModel.expandAll();
        this.treeLoading = false;
        // 勾选
        initChecked(tree.treeModel.nodes, tree.treeModel);
    }

    active(activeNode) {
        if (this.activeNode) {
            this.activeNode[0].active = false;
        }
        activeNode.active = true;
        this.activeNode = [activeNode];
    }

    updateData(e) {
        e.treeModel.expandAll();
        initChecked(e.treeModel.nodes, e.treeModel);
        // tree.sizeChanged()
    }

    /**
     * 权限勾选变更
     * @param event 复选框勾选事件
     */
    selectChangeTreeNode(event) {
        event.node.data.checked = true;
        this.setDataChecked(event.node);
        // console.log(event.node)
    }

    /**
     * 更新本地权限数据
     * @param node
     */
    setDataChecked(node: TreeNode) {
        node.data.checked = node.isSelected;
        if (node.parent) {
            this.setDataChecked(node.parent);
        } else if (node.children) {
            node.children[0].data.isSelected = node.isSelected;
            node.children[0].data.isPartiallySelected = node.isPartiallySelected;
        }
    }

    /**
     * 保存权限
     */
    savePrivilege() {
        this.addPrivilegeToSet(this.userPrivilege);
        this.service.authorizeSave(
            this.id, this.privilegeSet.toString())
            .subscribe((res) => {
                console.log(res);
                this.privilegeSet = [];
            });
    }

    /**
     * [[{...:{}},{}],[]]
     * @param array
     */
    addPrivilegeToSet(array) {
        array.forEach((arr) => {
            if (Array.isArray(arr)) {
                this.addPrivilegeToSet(arr);
            } else {
                if (arr.checked && !arr.isVirtual) {
                    this.privilegeSet.push(arr.id);
                }
                if (arr.children && arr.children.length > 0) {
                    this.addPrivilegeToSet(arr.children);
                }
            }
        });
    }

    /**
     * 载入用户权限
     * @param id
     */
    load(id) {
        this.service.authorize(id).subscribe(response => {
            // this.userPrivilege = response.body.map(v => [v]);
            const data =  this.processAccessRegItem(response.body);
            console.log(data);
            this.userPrivilege = [{
                name: '所有权限',
                children: data,
                isVirtual: true
            }];
            this.privilegeSet = [];
            if (data) {
                this.active(data[0]);
            }
        });

    }

    processAccessRegItem (data) {
        for (let i = 0; i < data.length; ++ i) {
            const item = data[i];
            if (item.accessReg) {
                data.splice(i, 1);
                i--;
            } else {
                if (item.children && item.children.length > 0) {
                    item.children = this.processAccessRegItem(item.children);
                    if (item.children.length <= 0) {
                        data.splice(i, 1);
                        i--;
                    } else {
                        const cl = item.children.filter((t) => t.checked).length;
                        if (cl > 0) {
                            item.isSelected = true;
                            item.isPartiallySelected = cl < item.children.length;
                        }
                    }
                }
            }
        }
        return data;
    }



    returnFalse() {
        return false;
    }
}
