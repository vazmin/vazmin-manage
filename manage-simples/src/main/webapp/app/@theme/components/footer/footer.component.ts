import { Component } from '@angular/core';

@Component({
  selector: 'ngx-footer',
  styleUrls: ['./footer.component.scss'],
  template: `
    <span class="created-by" ngxTranslate="footer">这里是页脚</span>
  `,
})
export class FooterComponent {
}
