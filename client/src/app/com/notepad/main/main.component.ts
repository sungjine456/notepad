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
  isLogin = false;

  constructor(private router: Router,
              private httpClient: HttpClient) {

    this.isLogin = sessionStorage.getItem("token") !== null;
  }

  signOut() {
    const res = this.httpClient.get(environment.server_url + "/api/auth/signOut");

    res.subscribe(() => {
      sessionStorage.removeItem("token");

      this.router.navigate(['/blank'], {skipLocationChange: true}).then(
        () => this.router.navigate(['/'])
      )
    });
  }
}
