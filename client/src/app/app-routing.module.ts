import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignupComponent} from "./com/notepad/user/signup/signup.component";
import {MainComponent} from "./com/notepad/main/main.component";
import {RegisteredComponent} from "./com/notepad/post/registered/registered.component";
import {ListComponent} from "./com/notepad/user/list/list.component";

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
    path: 'user/list',
    component: ListComponent
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
