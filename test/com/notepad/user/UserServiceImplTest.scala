package com.notepad.user

import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import com.notepad.common.{ SequenceDao, SequenceService }
import com.notepad.test.DatabaseTest

class UserServiceImplTest extends PlaySpec with BeforeAndAfter with DatabaseTest {

  val dao = new UserDao(provider)

  before {
    import dao.dbConfig.db
    import dao.dbConfig.profile.api._
    import dao.users

    db run {
      users.delete
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

      beforeFindAll.length mustBe 0

      await(service.create("id", "pass"))

      val afterFindAll: Seq[User] = await(service.findAll())

      afterFindAll.length mustBe 1
    }
  }

  "findAll()" should {
    "succeed" in {
      val result: Seq[User] = await(service.findAll())

      result.length mustBe 0
    }
  }
}
