import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-top-menu',
  templateUrl: './top-menu.component.html',
  styleUrls: ['./top-menu.component.scss']
})
export class TopMenuComponent implements OnInit {

  @Output() menuToggle = new EventEmitter<any>();

  constructor() { }

  ngOnInit(): void {
  }

  onMenuToggle() {
    this.menuToggle.emit();
  }
}
