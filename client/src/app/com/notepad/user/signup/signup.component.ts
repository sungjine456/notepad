import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../../environments/environment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.sass']
})
export class SignupComponent implements OnInit {

  userForm: FormGroup;

  constructor(private fb: FormBuilder,
              private httpClient: HttpClient,
              private router: Router) {
    this.userForm = this.fb.group({
      id: ['', [Validators.required, Validators.minLength(6)]],
      password: ['', [Validators.required, Validators.minLength(8)]]
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

    const res = this.httpClient.post(environment.server_url + "/user", body, {responseType: 'text'});

    res.subscribe((data) => {
        if (data == "registered") this.router.navigate(['/'])
      },
      (error) => {
        alert("아이디나 비밀번호를 다시 확인해주세요.")
      });
  }
}
