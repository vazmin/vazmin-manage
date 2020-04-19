import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-pages',
  styleUrls: ['./pages.component.scss'],
    template: `
    <app-simple-layout>
      <router-outlet></router-outlet>
    </app-simple-layout>
  `,
})
export class PagesComponent implements OnInit {

  constructor() {  }

  ngOnInit(): void {
  }

}
