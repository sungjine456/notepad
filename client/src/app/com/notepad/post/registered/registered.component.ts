import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../../environments/environment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registered',
  templateUrl: './registered.component.html',
  styleUrls: ['./registered.component.scss']
})
export class RegisteredComponent implements OnInit {

  postForm: FormGroup;

  constructor(private fb: FormBuilder,
              private httpClient: HttpClient,
              private router: Router) {
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

    const res = this.httpClient.post(environment.server_url + "/post", body, {responseType: 'text'});

    res.subscribe((data) => {
      if (data == "registered") this.router.navigate(['/'])
    });
  }
}
