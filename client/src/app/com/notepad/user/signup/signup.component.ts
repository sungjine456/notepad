import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.sass']
})
export class SignupComponent implements OnInit {

  angForm: FormGroup;
  private REST_API_SERVER = "http://localhost:9000";

  constructor(private fb: FormBuilder, private httpClient: HttpClient) {
    this.angForm = this.fb.group({
      email: [''],
      password: ['']
    });
  }

  ngOnInit() {
  }

  onSubmit(id, password) {
    console.log(id, password);

    const body = {
      id: id,
      password: password
    };

    const a = this.httpClient.post(this.REST_API_SERVER + "/user", body, { responseType: 'text'});

    a.subscribe((data) => {
      console.log(data);
    });
  }
}
