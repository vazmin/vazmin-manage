import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-auth-block',
  styleUrls: ['./auth-block.component.scss'],
  template: `
    <ng-content></ng-content>
  `,
})
export class AuthBlockComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
