package com.notepad.post

import scala.concurrent.Future

trait PostService {

  def registered(owner: Long, contents: String): Future[Post]

  def findAll(owner: Long): Future[Seq[Post]]

  def findByIdx(idx: Long): Future[Option[Post]]

  def findByIdxAndOwner(idx: Long, owner: Long): Future[Option[Post]]

  def update(idx: Long, owner: Long, contents: String): Future[Unit]
}
