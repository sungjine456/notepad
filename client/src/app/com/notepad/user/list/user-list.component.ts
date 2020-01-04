import {Component, OnInit} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {User} from "../User";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
  providers: [DatePipe]
})
export class UserListComponent implements OnInit {

  list: Array<User>;

  constructor(private httpClient: HttpClient,
              public datePipe: DatePipe) {
  }

  ngOnInit() {
    const res = this.httpClient.get(environment.server_url + "/users");

    res.subscribe((data: Array<User>) => {
      data.forEach(value => {
        value.registered = new Date(value.registered);
      });

      this.list = data;
    });
  }
}
