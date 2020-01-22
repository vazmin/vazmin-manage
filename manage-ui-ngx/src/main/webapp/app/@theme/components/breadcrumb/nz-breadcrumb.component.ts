import {
    Component,
    Injector,
    Input,
    OnDestroy,
    OnInit,
    TemplateRef
} from '@angular/core';
import {
    ActivatedRoute,
    NavigationEnd,
    Params,
    PRIMARY_OUTLET,
    Router
} from '@angular/router';
import {Subject} from 'rxjs';
import {filter, takeUntil} from 'rxjs/operators';
import {Principal} from '../../../@core/auth/principal.service';
import {getItemByLink} from '../../helpers';


const ROUTE_DATA_BREADCRUMB = 'breadcrumb';

export interface BreadcrumbOption {
    label: string;
    params: Params;
    url: string;
    disabled: boolean
}

/**
 * 面包屑导航。
 * 类型为菜单则不是链接
 */
@Component({
    selector: 'ngx-breadcrumb',
    preserveWhitespaces: false,
    templateUrl: './nz-breadcrumb.component.html',
    host: {
        '[class.ngx-breadcrumb]': 'true'
    },
})
export class NzBreadCrumbComponent implements OnInit, OnDestroy {
    private _separator: string | TemplateRef<void> = '/';
    private $destroy = new Subject();
    isTemplateRef = false;

    @Input() nzAutoGenerate = false;

    @Input()
    set nzSeparator(value: string | TemplateRef<void>) {
        this._separator = value;
        this.isTemplateRef = value instanceof TemplateRef;
    }

    get nzSeparator(): string | TemplateRef<void> {
        return this._separator;
    }

    breadcrumbs: BreadcrumbOption[] = [];

    nbMenuItem: any[];

    getBreadcrumbs(route: ActivatedRoute, url: string = '', breadcrumbs: BreadcrumbOption[] = []): BreadcrumbOption[] {
        const children: ActivatedRoute[] = route.children;
        if (children.length === 0) {
            return breadcrumbs; // If there's no sub root, then stop the recurse and returns the generated breadcrumbs.
        }
        for (const child of children) {
            if (child.outlet === PRIMARY_OUTLET) {
                // Only parse components in primary router-outlet
                // (in another word, router-outlet without a specific name).
                // Parse this layer and generate a breadcrumb item.
                const routeURL: string = child.snapshot.url.map(segment => segment.path).join('/');
                if (!routeURL) {
                    return this.getBreadcrumbs(child, url, breadcrumbs);
                }
                const nextUrl = url + `/${routeURL}`;
                const item = getItemByLink(nextUrl, this.nbMenuItem);
                // If have data, go to generate a breadcrumb for it.
                if (item || child.snapshot.data.hasOwnProperty(ROUTE_DATA_BREADCRUMB)) {
                    const breadcrumb: BreadcrumbOption = {
                        label: (item && item.title) || child.snapshot.data[ROUTE_DATA_BREADCRUMB] || 'Breadcrumb',
                        params: child.snapshot.params,
                        url: nextUrl,
                        disabled: item && item.type === 0
                    };
                    breadcrumbs.push(breadcrumb);
                }
                return this.getBreadcrumbs(child, nextUrl, breadcrumbs);
            }
        }
    }

    navigate(url: string, e: MouseEvent): void {
        e.preventDefault(); // Stop browsers' default navigation behavior.
        try {
            const router = this._injector.get(Router);
            router.navigateByUrl(url);
        } catch (e) {
            console.error(e);
        }
    }

    constructor(private _injector: Injector) {
    }

    ngOnInit(): void {
        if (this.nzAutoGenerate) {
            try {
                const activatedRoute = this._injector.get(ActivatedRoute);
                const router = this._injector.get(Router);
                const principal = this._injector.get(Principal);
                this.nbMenuItem = principal.getUserRouter();
                this.breadcrumbs = this.getBreadcrumbs(activatedRoute.root);
                router.events.pipe(filter(e => e instanceof NavigationEnd),
                    takeUntil(this.$destroy)).subscribe(() => {
                        // Build the breadcrumb tree from root route.
                        this.breadcrumbs = this.getBreadcrumbs(activatedRoute.root);
                });
            } catch (e) {
                throw new Error('You should import RouterModule if you want to use NzAutoGenerate');
            }
        }
    }

    ngOnDestroy(): void {
        this.$destroy.next();
        this.$destroy.complete();
    }

}
