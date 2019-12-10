package com.notepad.session

import java.util.UUID

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.impl.providers.CommonSocialProfile
import com.notepad.user.UserService
import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class SessionServiceImpl @Inject()(userService: UserService)(implicit ex: ExecutionContext) extends SessionService {

  def retrieve(id: UUID): Unit = {}

  def retrieve(loginInfo: LoginInfo): Future[Option[SessionContent]] = Future.successful(None)

  def save(user: SessionContent): Unit = {}

  def save(profile: CommonSocialProfile): Unit = {}
}
