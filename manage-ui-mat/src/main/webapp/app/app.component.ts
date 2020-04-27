import {Component, OnDestroy, OnInit} from '@angular/core';


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
