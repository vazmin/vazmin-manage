import { Injectable } from '@angular/core';
import {
  AuthServerProvider,
  LoginCredentials
} from 'app/core/auth/auth-jwt.service';
import {flatMap} from 'rxjs/operators';
import {AccountService} from 'app/core/auth/account.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private authServerProvider: AuthServerProvider, private accountService: AccountService) { }

  login(credentials: LoginCredentials) {
    return this.authServerProvider.login(credentials)
      .pipe(flatMap(() => this.accountService.identity(true)));
  }
}
