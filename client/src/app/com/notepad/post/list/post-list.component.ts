import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../../environments/environment";
import {User} from "../../user/User";
import {Post} from "../Post";

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss']
})
export class PostListComponent implements OnInit {

  posts: Array<Post> = [];

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit() {
    const res = this.httpClient.get(environment.server_url + "/posts");

    res.subscribe((data: Array<Post>) => {
      this.posts = data;
    });
  }
}
