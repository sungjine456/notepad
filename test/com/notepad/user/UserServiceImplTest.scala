package com.notepad.user

import com.notepad.common.{SequenceDao, SequenceService}
import com.notepad.test.SupportDatabaseTest
import org.postgresql.util.PSQLException
import org.scalatestplus.play.PlaySpec

class UserServiceImplTest extends PlaySpec with SupportDatabaseTest {

  val service: UserService = {
    val sequenceDao = new SequenceDao(provider)

    val sequenceService = new SequenceService(sequenceDao)

    new UserServiceImpl(userDao, sequenceService)
  }

  "create(id: String, password: String)" should {
    "succeed" in {
      val beforeFindAll: Seq[User] = await(service.findAll())

      beforeFindAll.length mustBe 1

      val user = await(service.create("newUserId", "pass"))

      val afterFindAll: Seq[User] = await(service.findAll())

      afterFindAll.length mustBe 2
      user.id mustBe "newUserId"
      user.password mustBe "pass"
    }

    "throw PSQLException when put short id" in {
      a[PSQLException] must be thrownBy {
        await(service.create("id", "pass"))
      }
    }
  }

  "findAll()" should {
    "succeed" in {
      val result: Seq[User] = await(service.findAll())

      result.length mustBe 1
    }
  }
}
