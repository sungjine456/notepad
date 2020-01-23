import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {SignUpComponent} from './com/notepad/user/signup/sign-up.component';
import {MainComponent} from './com/notepad/main/main.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RegisteredComponent} from './com/notepad/post/registered/registered.component';
import {UserListComponent} from './com/notepad/user/list/user-list.component';
import {HttpInterceptorService} from "./com/notepad/common/HttpInterceptorService";
import {SignInComponent} from "./com/notepad/user/signin/sign-in.component";
import {PostListComponent} from "./com/notepad/post/list/post-list.component";
import {BlankComponent} from "./com/notepad/common/BlankComponent";
import {PostUpdateComponent} from "./com/notepad/post/update/post-update.component";

@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    SignInComponent,
    MainComponent,
    RegisteredComponent,
    UserListComponent,
    PostListComponent,
    BlankComponent,
    PostUpdateComponent
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
