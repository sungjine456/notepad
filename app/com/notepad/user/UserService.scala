package com.notepad.user

import scala.concurrent.Future

trait UserService {

  def findAll(): Future[Seq[User]]

  def findById(id: String): Future[Option[User]]

  def findByIdx(idx: Long): Future[Option[User]]

  def findByIdAndPassword(id: String, password: String): Future[Option[User]]

  def create(id: String, password: String): Future[User]
}
