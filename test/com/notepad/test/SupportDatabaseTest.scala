package com.notepad.test

import com.notepad.common.SequenceDao
import com.notepad.user.{User, UserDao}
import org.scalatest.{BeforeAndAfterEach, Suite}

trait SupportDatabaseTest extends DatabaseTest with BeforeAndAfterEach {
  this: Suite =>

  val dao = new UserDao(provider)
  val sequenceDao = new SequenceDao(provider)

  import dao.dbConfig.db
  import dao.dbConfig.profile.api._
  import dao.users

  override protected def afterEach(): Unit = {
    super.afterEach()

    db run {
      users.delete
    }
  }

  override protected def beforeEach(): Unit = {
    super.beforeEach()

    db run {
      sequenceDao.sequences.filter(_.id === "User").map(_.value).update(1)

      val rows = users returning users.map(_.idx) into ((user, idx) => user.copy(idx = idx))

      rows += User(1, "id", "password")
    }
  }
}
