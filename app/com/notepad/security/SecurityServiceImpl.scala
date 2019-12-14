package com.notepad.security

import com.mohiva.play.silhouette.api.LoginInfo
import com.notepad.user.UserService
import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class SecurityServiceImpl @Inject()(userService: UserService)(implicit ex: ExecutionContext) extends SecurityService {

  def retrieve(loginInfo: LoginInfo): Future[Option[SecurityContent]] = Future.successful(None)
}
