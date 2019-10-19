package com.notepad.user

import scala.concurrent.Future

trait UserService {

  def create(id: String, password: String): Future[User]
}
