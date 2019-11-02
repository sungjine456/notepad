import {Component, OnInit} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {User} from "../User";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.sass']
})
export class ListComponent implements OnInit {

  list: Array<User>;

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit() {
    const res = this.httpClient.get(environment.server_url + "/user/list");

    res.subscribe((data: Array<User>) => {
      this.list = data;
    });
  }
}
