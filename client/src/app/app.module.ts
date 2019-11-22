import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {SignupComponent} from './com/notepad/user/signup/signup.component';
import {MainComponent} from './com/notepad/main/main.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RegisteredComponent} from './com/notepad/post/registered/registered.component';
import {ListComponent} from './com/notepad/user/list/list.component';

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
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
