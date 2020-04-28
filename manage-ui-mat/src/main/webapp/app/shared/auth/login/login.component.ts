import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {LoginService} from 'app/shared/auth/login/login.service';
import {Router} from '@angular/router';
import {StateStorageService} from 'app/core/auth/state-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  authenticationError: boolean;
  visibility: boolean = false;

  loginForm = this.fb.group({
    username: [null, Validators.required],
    password: [null, Validators.required],
    rememberMe: [false]
  });

  constructor(private fb: FormBuilder,
              private loginService: LoginService,
              private stateStorageService: StateStorageService,
              private router: Router,) { }

  ngOnInit(): void {
  }

  login(): boolean {
    if (this.loginForm.invalid) {
      return false;
    }
    this.loginService
      .login(this.loginForm.value)
      .subscribe(
        () => {
          this.authenticationError = false;
          // this.activeModal.dismiss('login success');
          setTimeout(() => {
            this.router.navigate(['/pages']).then(() => {});
          }, 100);


          // this.eventManager.broadcast({
          //   name: 'authenticationSuccess',
          //   content: 'Sending Authentication Success'
          // });

          // previousState was set in the authExpiredInterceptor before being redirected to login modal.
          // since login is successful, go to stored previousState and clear previousState
          const redirect = this.stateStorageService.getUrl();
          if (redirect) {
            this.stateStorageService.storeUrl('');
            this.router.navigateByUrl(redirect);
          }
        },
        () => (this.authenticationError = true)
      );
    return true;
  }

}
