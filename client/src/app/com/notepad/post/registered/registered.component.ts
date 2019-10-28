import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../../environments/environment";

@Component({
  selector: 'app-registered',
  templateUrl: './registered.component.html',
  styleUrls: ['./registered.component.sass']
})
export class RegisteredComponent implements OnInit {

  postForm: FormGroup;

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

    const res = this.httpClient.post(environment.server_url + "/post", body, { responseType: 'text'});

    res.subscribe((data) => {
      console.log(data);
    });
  }
}
