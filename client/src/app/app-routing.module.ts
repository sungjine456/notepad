import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {SignupComponent} from "./com/notepad/user/signup/signup.component";
import {MainComponent} from "./com/notepad/main/main.component";
import {RegisteredComponent} from "./com/notepad/post/registered/registered.component";

const routes: Routes = [
  {
    path: 'signup',
    component: SignupComponent
  },
  {
    path: 'registered',
    component: RegisteredComponent
  },
  {
    path: '',
    component: MainComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
