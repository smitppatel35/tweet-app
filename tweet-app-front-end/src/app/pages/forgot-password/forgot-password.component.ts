import { Component, OnInit } from '@angular/core';
import {UntypedFormBuilder, UntypedFormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user/user.service";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  form: UntypedFormGroup;

  get password() {
    return this.form.get('password');
  }

  get email() {
    return this.form.get('email');
  }

  constructor(
    private fb: UntypedFormBuilder,
    private _userService: UserService
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this._userService.forgotPassword(this.form.value['email'], this.form.value['password']);
    }
  }
}
