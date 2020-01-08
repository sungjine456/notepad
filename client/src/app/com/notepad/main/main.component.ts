import {Component} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent {
  title = 'notepad';

  constructor(private router: Router,
              private httpClient: HttpClient) {
  }

  signOut() {
    const res = this.httpClient.get(environment.server_url + "/api/auth/signOut", {responseType: 'text'});

    res.subscribe(() => {
        sessionStorage.removeItem("token");

        this.router.navigate(['/'])
      });
  }
}
