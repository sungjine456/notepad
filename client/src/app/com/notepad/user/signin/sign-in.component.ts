import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../../environments/environment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {

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
      ]]
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    if (this.userForm.invalid) {
      return;
    }

    const body = {
      id: this.userForm.controls.id.value,
      password: this.userForm.controls.password.value
    };

    const res = this.httpClient.post(environment.server_url + "/api/auth/signIn", body, {responseType: 'text'});

    res.subscribe(() => {
        this.router.navigate(['/user/list'])
      },
      () => {
        alert("아이디나 비밀번호를 다시 확인해주세요.")
      });
  }
}
