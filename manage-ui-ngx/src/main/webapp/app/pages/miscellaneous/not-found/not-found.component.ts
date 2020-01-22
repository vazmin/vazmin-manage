import { Component } from '@angular/core';
import {Location} from '@angular/common';

@Component({
  selector: 'ngx-not-found',
  styleUrls: ['./not-found.component.scss'],
  templateUrl: './not-found.component.html',
})
export class NotFoundComponent {

  constructor(private _location: Location) {
  }

  goToHome() {
    this._location.back();
  }
}
