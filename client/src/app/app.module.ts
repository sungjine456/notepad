import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {SignupComponent} from './com/notepad/user/signup/signup.component';
import {MainComponent} from './com/notepad/main/main.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RegisteredComponent} from './com/notepad/post/registered/registered.component';
import {ListComponent} from './com/notepad/user/list/list.component';
import {HttpInterceptorService} from "./com/notepad/common/HttpInterceptorService";

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    MainComponent,
    RegisteredComponent,
    ListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
