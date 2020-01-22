import {
    HttpInterceptor,
    HttpRequest,
    HttpErrorResponse,
    HttpHandler,
    HttpEvent
} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import {NgxEventManager} from '../../shared/service/event-manager.service';

/**
 * 错误拦截，广播
 */
export class ErrorHandlerInterceptor implements HttpInterceptor {

    constructor(private eventManager: NgxEventManager) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).do((event: HttpEvent<any>) => {
        }, (err: any) => {
            if (err instanceof HttpErrorResponse) {
                if (!(err.status === 401 &&
                    (err.message === '' || (err.url && err.url.indexOf('/api/account') === 0)))) {
                  if (this.eventManager !== undefined) {
                        this.eventManager.broadcast({name: 'NgxApp.httpError', content: err});
                    }
                }
            }
        });
    }
}
