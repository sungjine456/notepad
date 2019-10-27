import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-registered',
  templateUrl: './registered.component.html',
  styleUrls: ['./registered.component.sass']
})
export class RegisteredComponent implements OnInit {

  postForm: FormGroup;
  private REST_API_SERVER = "http://localhost:9000";

  constructor(private fb: FormBuilder, private httpClient: HttpClient) {
    this.postForm = this.fb.group({
      contents: [''],
    });
  }

  ngOnInit() {
  }

  onSubmit(contents) {

    const body = {
      contents: contents
    };

    const res = this.httpClient.post(this.REST_API_SERVER + "/post", body, { responseType: 'text'});

    res.subscribe((data) => {
      console.log(data);
    });
  }
}
