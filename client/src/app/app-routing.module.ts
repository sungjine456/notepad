import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./com/notepad/user/signup/sign-up.component";
import {MainComponent} from "./com/notepad/main/main.component";
import {RegisteredComponent} from "./com/notepad/post/registered/registered.component";
import {UserListComponent} from "./com/notepad/user/list/user-list.component";
import {SignInComponent} from "./com/notepad/user/signin/sign-in.component";
import {PostListComponent} from "./com/notepad/post/list/post-list.component";

const routes: Routes = [
  {
    path: 'signIn',
    component: SignInComponent
  },
  {
    path: 'signUp',
    component: SignUpComponent
  },
  {
    path: 'registered',
    component: RegisteredComponent
  },
  {
    path: 'post/list',
    component: PostListComponent
  },
  {
    path: 'user/list',
    component: UserListComponent
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
