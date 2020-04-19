import {ChangeDetectorRef, Component, HostBinding, OnDestroy, OnInit} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
import {CurrentNodes, NavigationNode} from 'app/shared/components/navigation/navigation.model';
import {LocationService} from 'app/shared/location.service';
import {ScrollService} from 'app/shared/scroll.service';
import {NavigationService} from 'app/shared/components/navigation/navigation.service';

@Component({
  selector: 'app-root',
  template: '<router-outlet></router-outlet>',
})
export class AppComponent implements OnDestroy, OnInit {
  title = 'manage-ui-mat';



  constructor() {

  }

  ngOnDestroy(): void {

  }

  ngOnInit(): void {

  }


}
