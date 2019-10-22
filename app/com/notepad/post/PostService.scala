package com.notepad.post

import scala.concurrent.Future

trait PostService {

  def registered(owner: Long, contents: String): Future[Post]
}
