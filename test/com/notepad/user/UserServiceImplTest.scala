package com.notepad.user

import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import com.notepad.common.{ SequenceDao, SequenceService }
import com.notepad.test.DatabaseTest

class UserServiceImplTest extends PlaySpec with BeforeAndAfter with DatabaseTest {

  val dao = new UserDao(provider)
  val sequenceDao = new SequenceDao(provider)

  import dao.dbConfig.db
  import dao.dbConfig.profile.api._
  import dao.users

  after {
    db run {
      users.delete
    }
  }

  before {
    db run {
      sequenceDao.sequences.filter(_.id === "User").map(_.value).update(1)

      val rows = users returning users.map(_.idx) into ((user, idx) => user.copy(idx = idx))

      rows += User(1, "id", "password")
    }
  }

  val service: UserService = {
    val sequenceDao = new SequenceDao(provider)

    val sequenceService = new SequenceService(sequenceDao)

    new UserServiceImpl(dao, sequenceService)
  }

  "create(id: String, password: String)" should {
    "succeed" in {
      val beforeFindAll: Seq[User] = await(service.findAll())

      beforeFindAll.length mustBe 1

      val user = await(service.create("id", "pass"))

      val afterFindAll: Seq[User] = await(service.findAll())

      afterFindAll.length mustBe 2
      user.id mustBe "id"
      user.password mustBe "pass"
    }
  }

  "findAll()" should {
    "succeed" in {
      val result: Seq[User] = await(service.findAll())

      result.length mustBe 1
    }
  }
}
