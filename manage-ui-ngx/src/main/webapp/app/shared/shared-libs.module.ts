import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
import { CommonModule } from '@angular/common';
import {NbAlertModule} from '@nebular/theme';

// export function createTranslateLoader(http: HttpClient) {
//     return new TranslateHttpLoader(http, './assets/i18n/', '.json');
// }



@NgModule({
    imports: [

    ],
    exports: [
        FormsModule,
        HttpClientModule,
        CommonModule,
        NbAlertModule
    ]
})
export class SharedLibsModule {}
