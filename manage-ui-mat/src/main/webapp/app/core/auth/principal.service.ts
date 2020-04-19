// /*
// import {Injectable} from '@angular/core';
// import {AccountService} from './account.service';
// import {deepCloneArray} from '../../shared/helpers';
// import {Observable, of, Subject} from 'rxjs';
// /!**
//  * 凭证 权限验证
//  *!/
// @Injectable()
// export class Principal {
//     /!** 用户身份信息 *!/
//     private userIdentity: any;
//     /!** 验证通过 *!/
//     private authenticated = false;
//     /!** 验证主题，用户身份信息 *!/
//     private authenticationState = new Subject<any>();
//
//     private menuVMSet: any;
//
//     constructor(private account: AccountService) {
//     }
//
//     authenticate(identity: any) {
//         this.userIdentity = identity;
//         this.authenticated = identity !== null;
//         this.authenticationState.next(this.userIdentity);
//     }
//
//     /!**
//      * 是否有权限
//      * @param permission api地址
//      * @return Promise<boolean> 一个承诺
//      *!/
//     hasAnyPermission(permission: string[]): Promise<boolean> {
//         return Promise.resolve(this.hasAnyPermissionDirect(permission));
//     }
//
//     /!**
//      * 判断api列表是否有权限
//      * @param permission api地址
//      *!/
//     hasAnyPermissionDirect(permission: string[]): boolean {
//         if (!this.authenticated || !this.userIdentity || !this.userIdentity.userMenu) {
//             return false;
//         }
//         for (let i = 0; i < permission.length; i++) {
//             if (this.hasURLDirect(permission[i], this.userIdentity.userMenu)) {
//                 return true;
//             }
//         }
//         return false;
//     }
//
//     /!**
//      * 是否有权限
//      * @param permission api地址
//      * @return Promise<boolean> 一个承诺
//      *!/
//     hasAllPermission(permission: string[]): Promise<boolean> {
//       return Promise.resolve(this.hasAllPermissionDirect(permission));
//     }
//
//     /!**
//      * 判断api列表是否有权限
//      * @param permission api地址
//      *!/
//     hasAllPermissionDirect(permission: string[]): boolean {
//       if (!this.authenticated || !this.userIdentity || !this.userIdentity.userMenu) {
//         return false;
//       }
//       for (let i = 0; i < permission.length; i++) {
//         if (!this.hasURLDirect(permission[i], this.userIdentity.userMenu)) {
//           return false;
//         }
//       }
//       return true;
//     }
//
//     /!**
//      *
//      * @param {string} url
//      * @returns {Promise<boolean>}
//      *!/
//     hasPermission(url: string): Promise<boolean> {
//         if (!this.authenticated) {
//             return Promise.resolve(false);
//         }
//         return this.identity().then((i) => {
//             return this.hasURL(url, i.userMenu);
//         }, () => {
//             return Promise.resolve(false);
//         });
//
//     }
//
//     /!**
//      * 凭证里是否包含url
//      * @param url
//      * @param mmcList 模块菜单
//      * @param replacePath
//      * @returns {Promise<boolean>}
//      *!/
//     hasURL(url, mmcList): Promise<boolean> {
//         return Promise.resolve(this.hasURLDirect(url, mmcList));
//     }
//
//     /!**
//      * 凭证里是否包含url, Menu 和 Module 取替换后的api
//      * @param url
//      * @param mmcList 模块菜单
//      * @returns {boolean}
//      *!/
//     hasURLDirect(url: string, mmcList): boolean {
//         if (mmcList) {
//             for (let i = 0; i < mmcList.length; i++) {
//                 if (+mmcList[i].type === ItemTypeEnum.COMMAND) {
//                     if (url === mmcList[i].link || url === this.replaceOneLevelPath(mmcList[i].link)) {
//                         return true;
//                     }
//                 } else {
//                     if (url === this.replaceOneLevelPath(mmcList[i].link)) {
//                         return true;
//                     }
//                 }
//                 if (this.hasURLDirect(url, mmcList[i].children)) {
//                     return true;
//                 }
//             }
//         }
//         return false;
//     }
//
//     /!**
//      * 编辑、详情、删除权限判断
//      * @param baseURL
//      * @param setting
//      *!/
//     hasPermissionForCURD(baseURL, setting): void {
//         if (setting.actions.add) {
//             setting.actions.add = this.hasURLDirectForCURD(baseURL, '/new');
//         }
//         if (setting.actions.edit) {
//             setting.actions.edit = this.hasURLDirectForCURD(baseURL, '/edit');
//         }
//         if (setting.actions.detail) {
//             setting.actions.detail = this.hasURLDirectForCURD(baseURL, '/detail');
//         }
//         if (setting.actions.delete) {
//             setting.actions.delete = this.hasURLDirectForCURD(baseURL, '/delete');
//         }
//     }
//
//     hasURLDirectForCURD(baseURL, suffix): boolean {
//         return this.hasURLDirect(baseURL + suffix, this.userIdentity.userMenu);
//     }
//
//     /!**
//      * 验证用户身份
//      * @param {boolean} force 强制认证
//      * @returns {Promise<any>}
//      *!/
//     identity(force?: boolean): Promise<any> {
//         if (force === true) {
//             this.userIdentity = undefined;
//         }
//
//         // check and see if we have retrieved the userIdentity data from the server.
//         // if we have, reuse it by immediately resolving
//         if (this.userIdentity) {
//             return Promise.resolve(this.userIdentity);
//         }
//
//         // retrieve the userIdentity data from the server, update the identity object, and then resolve.
//         return this.account.get().toPromise().then((response) => {
//             const principal = response.body;
//             if (principal) {
//                 this.userIdentity = principal;
//                 this.authenticated = true;
//             } else {
//                 this.userIdentity = null;
//                 this.authenticated = false;
//             }
//             this.authenticationState.next(this.userIdentity);
//             return this.userIdentity;
//         }).catch((err) => {
//             this.userIdentity = null;
//             this.authenticated = false;
//             this.authenticationState.next(this.userIdentity);
//             return null;
//         });
//     }
//
//     isAuthenticated(): boolean {
//         return this.authenticated;
//     }
//
//     isIdentityResolved(): boolean {
//         return this.userIdentity !== undefined;
//     }
//
//     getAuthenticationState(): Observable<any> {
//         return this.authenticationState.asObservable();
//     }
//
//     getImageUrl(): String {
//         return this.isIdentityResolved() ? this.userIdentity.imageUrl : null;
//     }
//
//     getUserInfo(): Observable<any> {
//         return this.isIdentityResolved() ? of(this.userIdentity.user) : null;
//     }
//
//     getMenuVMSet(): any {
//         const userMenu = deepCloneArray(this.userIdentity.userMenu);
//         if (this.isIdentityResolved()) {
//             return this.processMMCList(userMenu);
//         }
//         return null;
//     }
//
//     getUserRouter(): any {
//         const userMenu = deepCloneArray(this.userIdentity.userMenu);
//         return this.processMMCList(userMenu, null, false);
//     }
//
//     processMMCList(MMCInfoList, parent?, ignoreCommand: boolean = true) {
//         return MMCInfoList.map((mmcInfo, index) => {
//             mmcInfo.parent = parent;
//             switch (+mmcInfo.type) {
//                 case ItemTypeEnum.MENU:
//                     this.processMMCList(mmcInfo.children, mmcInfo, ignoreCommand);
//                     break;
//                 case ItemTypeEnum.MODULE:
//                     this.processModule(mmcInfo, index, ignoreCommand);
//                     break;
//             }
//             return mmcInfo;
//         });
//     }
//
//     /!**
//      * 处理module
//      * @param moduleInfo
//      * @param index
//      * @param ignoreCommand
//      *!/
//     processModule(moduleInfo, index: number = 1, ignoreCommand: boolean = true) {
//         if (ignoreCommand) {
//             moduleInfo.children = null;
//         } else {
//             for (const commandInfo of moduleInfo.children) {
//                 this.processModule(commandInfo);
//             }
//         }
//         moduleInfo.link = this.replaceOneLevelPath(moduleInfo.link);
//         // 是模块 且是第一个， 则补全父级菜单link
//         if (index === 0) {
//             moduleInfo.parent && this.processParent(moduleInfo.parent, moduleInfo);
//         }
//     }
//
//     /!**
//      * 添加父级link，逐级递减
//      * @param parentInfo
//      * @param childrenInfo
//      *!/
//     processParent(parentInfo, childrenInfo) {
//         parentInfo.link = childrenInfo.link.substring(0, childrenInfo.link.lastIndexOf('/'));
//         if (parentInfo.parent) {
//             this.processParent(parentInfo.parent, parentInfo);
//         }
//     }
//
//     replaceOneLevelPath(link) {
//         return link.replace('/api', '/pages');
//     }
//
//
// }
// */
