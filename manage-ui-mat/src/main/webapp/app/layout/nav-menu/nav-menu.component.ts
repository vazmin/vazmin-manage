import { Component, OnInit } from '@angular/core';
import {MenuItem} from 'app/shared/components/menu-list-item/menu-item';
import {animate, state, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.scss'],
  animations: [
    trigger('bodyExpansion', [
      state('collapsed', style({height: '0px', display: 'none'})),
      state('expanded', style({height: '*', display: 'block'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4,0.0,0.2,1)')),
    ]),
  ]
})
export class NavMenuComponent implements OnInit {

  menus: MenuItem[] = [
    {
      id: '1-1',
      title: 'E-commerce',
      icon: 'info',
      link: '/pages/dashboard',
      home: true,
    },
    {
      id: '1-1',
      title: 'IoT Dashboard',
      icon: 'home',
      link: '/pages/iot-dashboard',
    },
    {
      id: '1-1',
      title: 'Layout',
      icon: 'info',
      children: [
        {
          id: '1-1',
          title: 'Stepper',
          link: '/pages/layout/stepper',
        },
        {
          id: '1-1',
          title: 'List',
          link: '/pages/layout/list',
        }
      ],
    }
  ];

  currentItemId = '1-2';

  constructor() { }

  ngOnInit(): void {
  }

}
