package com.notepad.security

import com.mohiva.play.silhouette.api.LoginInfo
import com.notepad.user.UserService
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SecurityServiceImpl @Inject()(
  userService: UserService)(implicit ex: ExecutionContext) extends SecurityService {

  def retrieve(loginInfo: LoginInfo): Future[Option[SecurityContent]] = {
    for {
      user <- userService.findByIdx(loginInfo.providerKey.toLong)
    } yield {
      user map { u =>
        SecurityContent(u.idx)
      }
    }
  }
}
