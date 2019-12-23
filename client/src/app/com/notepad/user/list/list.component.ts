import {Component, OnInit} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {User} from "../User";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
  providers: [DatePipe]
})
export class ListComponent implements OnInit {

  list: Array<User>;

  constructor(private httpClient: HttpClient,
              public datePipe: DatePipe) {
  }

  ngOnInit() {
    const res = this.httpClient.get(environment.server_url + "/user/list", {
      headers: {'X-Auth-Token': sessionStorage.getItem("token")}
    });

    res.subscribe((data: Array<User>) => {
      data.forEach(value => {
        value.registered = new Date(value.registered);
      });

      this.list = data;
    });
  }
}
