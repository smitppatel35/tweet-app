import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validator, Validators} from "@angular/forms";
import {UserService} from "../../services/user/user.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;

  get password() {
    return this.form.get('password');
  }

  get email() {
    return this.form.get('email');
  }

  get fName() {
    return this.form.get('firstName');
  }

  get lName() {
    return this.form.get('lastName');
  }

  get contactNumber() {
    return this.form.get('contactNumber');
  }

  constructor(
    private fb: FormBuilder,
    private _userService: UserService
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required, Validators.length >= 8],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      contactNumber: ['', Validators.required, Validators.length === 10],
      gender: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this._userService.register(this.form.value);
    }
  }

}
