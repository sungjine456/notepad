import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../../environments/environment";
import {Post} from "../Post";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss'],
  providers: [DatePipe]
})
export class PostListComponent implements OnInit {

  posts: Array<Post> = [];

  constructor(private httpClient: HttpClient,
              public datePipe: DatePipe) {
  }

  ngOnInit() {
    const res = this.httpClient.get(environment.server_url + "/posts");

    res.subscribe((data: Array<Post>) => {
      this.posts = data;
    });
  }

  remove(idx: number) {
    const res = this.httpClient.delete(environment.server_url + "/post/" + idx);

    res.subscribe(() => {
      this.posts = this.posts.filter(v => v.idx !== idx);
    });
  }
}
