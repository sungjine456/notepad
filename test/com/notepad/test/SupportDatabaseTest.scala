package com.notepad.test

import com.notepad.common.SequenceDao
import com.notepad.post.PostDao
import com.notepad.user.{User, UserDao}
import org.scalatest.{BeforeAndAfterEach, Suite}

trait SupportDatabaseTest extends DatabaseTest with BeforeAndAfterEach {
  this: Suite =>

  val userDao = new UserDao(provider)
  val postDao = new PostDao(provider)
  val sequenceDao = new SequenceDao(provider)

  import dbConfig.db
  import dbConfig.profile.api._
  import userDao.users
  import postDao.posts

  override protected def afterEach(): Unit = {
    super.afterEach()

    await(deleteUser().map(_ => deletePost()))
  }

  override protected def beforeEach(): Unit = {
    super.beforeEach()

    await(addUser())
  }

  private def deleteUser() = {
    db run {
      users.delete
    }
  }

  private def deletePost() = {
    db run {
      posts.delete
    }
  }

  private def addUser() = {
    db run {
      sequenceDao.sequences.filter(_.id === "User").map(_.value).update(2)

      val rows = users returning users.map(_.idx) into ((user, idx) => user.copy(idx = idx))

      rows += User(1, "newUserId", "password")
    }
  }
}
