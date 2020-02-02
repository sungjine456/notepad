import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../../environments/environment";
import {Post} from "../Post";
import {FormBuilder, FormGroup} from "@angular/forms";
import {mergeMap} from "rxjs/operators";

@Component({
  selector: 'app-post-update',
  templateUrl: './post-update.component.html',
  styleUrls: ['./post-update.component.scss']
})
export class PostUpdateComponent implements OnInit {

  post: Post = new Post;
  postForm: FormGroup;

  constructor(private fb: FormBuilder,
              private activatedRoute: ActivatedRoute,
              private httpClient: HttpClient,
              private router: Router) {

    this.postForm = this.fb.group({
      contents: [''],
    });

    activatedRoute.queryParams.pipe(
      mergeMap(params =>
        this.httpClient.get(environment.server_url + "/post/" + params.idx)
      )
    ).subscribe((post: Post) => {
      this.post = post;
    });
  }

  ngOnInit() {
  }

  onSubmit(contents) {
    const body = {
      contents: contents
    };

    const res = this.httpClient.post(environment.server_url + "/post/" + this.post.idx, body, {
      responseType: 'text'
    });

    res.subscribe(() => this.router.navigate(['/posts']));
  }
}
