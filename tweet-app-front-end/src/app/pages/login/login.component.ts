import { Component, OnInit } from '@angular/core';
import {
  UntypedFormBuilder,
  UntypedFormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  form: UntypedFormGroup;

  get password() {
    return this.form.get('password');
  }

  get email() {
    return this.form.get('email');
  }

  constructor(
    private fb: UntypedFormBuilder,
    private _userService: UserService,
    public oidcSecurityService: OidcSecurityService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // this.form = this.fb.group({
    //   email: ['', [Validators.required, Validators.email]],
    //   password: ['', Validators.required]
    // });
  }

  login() {
    this.oidcSecurityService.authorize();
    this.router.navigate(['/home/']);
  }

  // onSubmit() {
  //   if (this.form.valid) {
  //     this._userService.login(this.form.value);
  //   }
  // }
}
