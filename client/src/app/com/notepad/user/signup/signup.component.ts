import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../../environments/environment";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.sass']
})
export class SignupComponent implements OnInit {

  userForm: FormGroup;

  constructor(private fb: FormBuilder, private httpClient: HttpClient) {
    this.userForm = this.fb.group({
      id: [''],
      password: ['']
    });
  }

  ngOnInit() {
  }

  onSubmit(id, password) {
    const body = {
      id: id,
      password: password
    };

    const res = this.httpClient.post(environment.server_url + "/user", body, { responseType: 'text'});

    res.subscribe((data) => {
      console.log(data);
    });
  }
}
