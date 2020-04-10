import {ChangeDetectorRef, Component, HostBinding, OnDestroy, OnInit} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
import {CurrentNodes, NavigationNode} from 'app/shared/components/navigation/navigation.model';
import {LocationService} from 'app/shared/location.service';
import {ScrollService} from 'app/shared/scroll.service';
import {NavigationService} from 'app/shared/components/navigation/navigation.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnDestroy, OnInit {
  title = 'manage-ui-mat';

  mobileQuery: MediaQueryList;
  currentNodes: CurrentNodes = {};
  currentPath: string;
  // Disable all Angular animations for the initial render.
  @HostBinding('@.disabled')
  isStarting = true;
  isTransitioning = true;
  isFetching = false;
  isSideBySide = false;
  private isFetchingTimeout: any;
  private isSideNavDoc = false;

  private sideBySideWidth = 992;
  sideNavNodes: NavigationNode[];
  topMenuNodes: NavigationNode[];
  topMenuNarrowNodes: NavigationNode[];

  private _mobileQueryListener: () => void;

  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher,
              public locationService: LocationService,
              public scrollService: ScrollService,
              public navigationService: NavigationService) {
    this.mobileQuery = media.matchMedia('(max-width: '+ this.sideBySideWidth + 'px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.isSideBySide = !this.mobileQuery.matches;
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnDestroy(): void {
    // tslint:disable-next-line:deprecation
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  ngOnInit(): void {
    this.locationService.currentPath.subscribe(path => {
      if (path === this.currentPath) {
        // scroll only if on same page (most likely a change to the hash)
        this.scrollService.scroll();
      } else {
        // don't scroll; leave that to `onDocRendered`
        this.currentPath = path;

        // Start progress bar if doc not rendered within brief time
        clearTimeout(this.isFetchingTimeout);
        this.isFetchingTimeout = setTimeout(() => this.isFetching = true, 200);
      }
    });

    this.navigationService.currentNodes.subscribe(currentNodes => {
      this.currentNodes = currentNodes;
    });

    this.navigationService.navigationViews.subscribe(views => {
      // this.footerNodes = views['Footer'] || [];
      this.sideNavNodes = views['SideNav'] || [];
      this.topMenuNodes = views['TopBar'] || [];
      this.topMenuNarrowNodes = views['TopBarNarrow'] || this.topMenuNodes;
    });

  }

  get isOpened() { return this.isSideBySide }
  get mode() { return this.mobileQuery.matches ? 'over': 'side'; }

  updateHostClasses() {
  //   const mode = `mode-${this.deployment.mode}`;
  //   const sideNavOpen = `sidenav-${this.sidenav.opened ? 'open' : 'closed'}`;
  //   const pageClass = `page-${this.pageId}`;
  //   const folderClass = `folder-${this.folderId}`;
  //   const viewClasses = Object.keys(this.currentNodes).map(view => `view-${view}`).join(' ');
  //   const notificationClass = `aio-notification-${this.notification.showNotification}`;
  //   const notificationAnimatingClass = this.notificationAnimating ? 'aio-notification-animating' : '';
  //
  //   this.hostClasses = [
  //     mode,
  //     sideNavOpen,
  //     pageClass,
  //     folderClass,
  //     viewClasses,
  //     notificationClass,
  //     notificationAnimatingClass
  //   ].join(' ');
  }
}
