import {Injectable} from '@angular/core';
import {
    HttpClient,
    HttpResponse,
    HttpHeaders,
    HttpErrorResponse
} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {of as observableOf} from 'rxjs/observable/of';
import {environment} from '../../../environments/environment';
import {catchError, map} from 'rxjs/operators';
import {AuthResult} from './auth-result';
import {deepExtend, getDeepFromObject} from '../../shared/helpers';
import {AuthSessionServiceConfig} from './username-pass-session-auth.options';
import {StateStorageService} from './state-storage.service';

@Injectable()
export class AuthSessionService {

    constructor(private http: HttpClient,
                private stateStorageService: StateStorageService) {
        this.setConfig(this.defaultConfig);
    }

    protected defaultConfig: AuthSessionServiceConfig = {
        baseEndpoint: environment.SERVER_API_URL,
        login: {
            alwaysFail: false,
            rememberMe: true,
            endpoint: '/api/authentication',
            method: 'post',
            redirect: {
                success: '/',
                failure: null,
            },
            defaultErrors: ['用户名或密码不正确，请重试'],
            defaultMessages: ['你已成功登录'],
        },
        logout: {
            alwaysFail: false,
            endpoint: '/api/logout',
            method: 'delete',
            redirect: {
                success: '/',
                failure: null,
            },
            defaultErrors: ['出了点问题，请重试'],
            defaultMessages: ['你已成功退出'],
        },
        errors: {
            key: 'data.errors',
            getter: (module: string, res: HttpErrorResponse) => getDeepFromObject(res.error,
                this.getConfigValue('errors.key'),
                this.getConfigValue(`${module}.defaultErrors`)),
        },
        messages: {
            key: 'data.messages',
            getter: (module: string, res: HttpResponse<Object>) => getDeepFromObject(res.body,
                this.getConfigValue('messages.key'),
                this.getConfigValue(`${module}.defaultMessages`)),
        },
    };

    protected config: any = {};

    setConfig(config: any): void {
        this.config = deepExtend({}, this.defaultConfig, config);
    }

    getConfigValue(key: string): any {
        return getDeepFromObject(this.config, key, null);
    }

    login(credentials): Observable<any> {
        const data = 'username=' + encodeURIComponent(credentials.username) +
            '&password=' + encodeURIComponent(credentials.password) +
            '&remember-me=' + credentials.rememberMe + '&submit=Login';
        const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
        return this.http.post(this.getActionEndpoint('login'), data, {
            headers,
            observe: 'response'
        })
            .pipe(
                map((res) => {
                    let redirect = this.stateStorageService.getUrl();
                    if (redirect) {
                        this.stateStorageService.storeUrl(null);
                    } else {
                        redirect = this.getConfigValue('login.redirect.success');
                    }
                    return new AuthResult(
                        true,
                        res,
                        redirect,
                        [],
                        [this.getConfigValue('messages.getter')('login', res)]);
                }),
                catchError((res) => {
                    let errors = [];
                    if (res instanceof HttpErrorResponse) {
                        errors = this.getConfigValue('errors.getter')('login', res);
                    } else {
                        errors.push('Something went wrong.');
                    }

                    return observableOf(
                        new AuthResult(
                            false,
                            res,
                            this.getConfigValue('login.redirect.failure'),
                            errors,
                        ));
                }),
            );
    }

    logout(): Observable<any> {
        // logout from the server
        return this.http.get(this.getActionEndpoint('logout'))
            .pipe(
                map((response: HttpResponse<any>) => {
                    // to get a new csrf token call the api
                    // this.http.get(environment.SERVER_API_URL + 'api/account').subscribe(() => {}, () => {});
                    return response.status === 401;
                }),
                catchError((err) => {
                    return observableOf(true);
                })
            );
    }

    protected getActionEndpoint(action: string): string {
        const actionEndpoint: string = this.getConfigValue(`${action}.endpoint`);
        const baseEndpoint: string = this.getConfigValue('baseEndpoint');
        return baseEndpoint + actionEndpoint;
    }
}
