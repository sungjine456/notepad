import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./com/notepad/user/signup/sign-up.component";
import {MainComponent} from "./com/notepad/main/main.component";
import {RegisteredComponent} from "./com/notepad/post/registered/registered.component";
import {ListComponent} from "./com/notepad/user/list/list.component";

const routes: Routes = [
  {
    path: 'signUp',
    component: SignUpComponent
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
