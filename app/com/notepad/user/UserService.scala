package com.notepad.user

import scala.concurrent.Future

trait UserService {

  def findAll(): Future[Seq[User]]

  def create(id: String, password: String): Future[User]
}
