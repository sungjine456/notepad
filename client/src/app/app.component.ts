import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {
  title = 'notepad';
  products = [];

  private REST_API_SERVER = "http://localhost:9000";

  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
    const a = this.httpClient.get(this.REST_API_SERVER + "/count");

    a.subscribe((data: any[])=>{
      console.log(data);
      this.products = data;
    })
  }
}
