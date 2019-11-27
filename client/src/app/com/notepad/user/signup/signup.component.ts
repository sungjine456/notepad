import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../../environments/environment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  userForm: FormGroup;

  constructor(private fb: FormBuilder,
              private httpClient: HttpClient,
              private router: Router) {
    this.userForm = this.fb.group({
      id: ['', [
        Validators.required,
        Validators.minLength(6)
      ]],
      password: ['', [
        Validators.required,
        Validators.pattern(/^(?=.*[a-zA-Z])(?=.*[!@#$%^~*+=-])(?=.*[0-9]).{8,20}/)
      ]],
      confirmPassword: ['']
    }, {validator: MustMatch('password', 'confirmPassword')});
  }

  ngOnInit() {
  }

  onChange() {
    const id = this.userForm.controls.id.value;

    const res = this.httpClient.head(environment.server_url + "/user/" + id, {responseType: 'text'});

    res.subscribe(() => {
        let errors = this.userForm.controls.id.errors;

        if (errors === null) {
          errors = {existId: true};
        } else {
          errors["existId"] = true;
        }
        this.userForm.controls.id.setErrors(errors);
      },
      () => {
        let errors = this.userForm.controls.id.errors;

        if (this.userForm.controls.id.hasError("existId")) {
          errors["existId"] = false;
        }
        this.userForm.controls.id.setErrors(errors);
      });
  }

  onSubmit() {
    if (this.userForm.invalid) {
      return;
    }

    const body = {
      id: this.userForm.controls.id.value,
      password: this.userForm.controls.password.value
    };

    const res = this.httpClient.post(environment.server_url + "/user", body, {responseType: 'text'});

    res.subscribe(() => {
        this.router.navigate(['/'])
      },
      () => {
        alert("아이디나 비밀번호를 다시 확인해주세요.")
      });
  }
}

export function MustMatch(controlName: string, matchingControlName: string) {
  return (formGroup: FormGroup) => {
    const control = formGroup.controls[controlName];
    const matchingControl = formGroup.controls[matchingControlName];

    if (matchingControl.errors && !matchingControl.errors.mustMatch) {
      return;
    }

    matchingControl.setErrors(control.value !== matchingControl.value ? {mustMatch: true} : null);
  }
}
