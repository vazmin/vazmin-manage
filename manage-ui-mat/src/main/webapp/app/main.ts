import './polyfills';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app.module';
import {ProdConfig} from 'app/blocks/config/prod.config';

ProdConfig();

if (module['hot']) {
  module['hot'].accept();
}

platformBrowserDynamic().bootstrapModule(AppModule, { preserveWhitespaces: true })
  .catch(err => console.error(err));
