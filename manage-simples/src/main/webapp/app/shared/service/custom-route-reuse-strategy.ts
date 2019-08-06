import {
    ActivatedRouteSnapshot,
    DetachedRouteHandle,
    RouteReuseStrategy
} from '@angular/router';

/**
 * 自定义路由重用策略
 * 当前路由第一个小孩没有孩子，则不再重用路由
 * /pages/a/b/c b及b后不重用
 * /pages/a/b a及a后不重用
 * TODO: 暂时这样
 * ？？？ 如果第一个小孩没有孩子，第二的小孩有孩子，也不重用 ？？？
 */
export class CustomRouteReuseStrategy implements RouteReuseStrategy {
    retrieve(route: ActivatedRouteSnapshot): DetachedRouteHandle | null {
        return null;
    }

    shouldAttach(route: ActivatedRouteSnapshot): boolean {
        return false;
    }

    shouldDetach(route: ActivatedRouteSnapshot): boolean {
        return false;
    }

    shouldReuseRoute(future: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot): boolean {
        // if (future.routeConfig && curr.routeConfig) {
        //     console.log(future.routeConfig.path + ' <=> ' + curr.routeConfig.path);
        // }

        // if (curr && curr.routeConfig && curr.routeConfig.data) {
        //     if (curr.routeConfig.data.notReuse) {
        //         // 不重用路由
        //         return false
        //     }
        // }
        if (curr) {
            // console.log(future, curr);
            if (curr.children) {
                // console.log(future.routeConfig, curr.routeConfig, curr.children);
                if (curr.children.length === 0) {
                    return false;
                }
                if (curr.firstChild) {
                    // console.log(future.routeConfig, curr.routeConfig, curr.firstChild.children);
                    // 如果现在的路由快照第一个小孩没有孩子
                    if (curr.firstChild.children && curr.firstChild.children.length === 0) {
                        return false;
                    }
                }

            }
        }
        return future.routeConfig === curr.routeConfig;
    }

    store(route: ActivatedRouteSnapshot, handle: DetachedRouteHandle | null): void {
    }

}
