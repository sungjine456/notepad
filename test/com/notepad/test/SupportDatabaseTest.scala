package com.notepad.test

import java.util.Date

import com.notepad.common.SequenceDao
import com.notepad.post.{Post, PostDao}
import com.notepad.security.PasswordHasherImpl.Separator
import com.notepad.user.{User, UserDao}
import org.scalatest.{BeforeAndAfterEach, Suite}

trait SupportDatabaseTest extends DatabaseTest with BeforeAndAfterEach with ConfigurationTest {
  this: Suite =>

  val userDao = new UserDao(provider)
  val postDao = new PostDao(provider)
  val sequenceDao = new SequenceDao(provider)

  import dbConfig.db
  import dbConfig.profile.api._
  import postDao.posts
  import sequenceDao.sequences
  import userDao.users

  override protected def afterEach(): Unit = {
    super.afterEach()

    await(deleteUser().map(_ => deletePost()))
  }

  override protected def beforeEach(): Unit = {
    super.beforeEach()

    await {
      for {
        _ <- addUser()
        _ <- addPost(1, 1, "first contents")
        _ <- addPost(2, 1, "second contents")
        _ <- addPost(3, 2, "other first contents")
        _ <- addPost(4, 1, "removed contents", removed = true)
      } yield {}
    }
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
    val result = for {
      _ <- sequences.filter(_.id === "User").map(_.value).update(2)
      add <- {
        val id = "newUserId"

        val rows = users returning users.map(_.idx) into ((user, idx) => user.copy(idx = idx))

        val passwordInfo = hasher.hash(Seq(id, "password").mkString(Separator))

        rows += User(1, id, passwordInfo.password, None, new Date())
      }
    } yield add

    db.run(result.transactionally)
  }

  private def addPost(idx: Long, owner: Long, contents: String, removed: Boolean = false) = {
    val result = for {
      _ <- sequences.filter(_.id === "Post").map(_.value).update(idx + 1)
      add <- {
        val rows = posts returning posts.map(_.idx) into ((post, idx) => post.copy(idx = idx))

        rows += Post(idx, owner, contents, None, new Date(), removed)
      }
    } yield add

    db.run(result.transactionally)
  }
}
