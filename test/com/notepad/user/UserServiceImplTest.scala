package com.notepad.user

import com.notepad.common.{SequenceDao, SequenceService}
import com.notepad.test.SupportDatabaseTest
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

      val user = await(service.create("newUserId", "pass123!"))

      val afterFindAll: Seq[User] = await(service.findAll())

      afterFindAll.length mustBe 2
      user.id mustBe "newUserId"
      user.password mustBe "pass123!"
    }

    "throw IllegalArgumentException when put short id" in {
      a[IllegalArgumentException] must be thrownBy {
        await(service.create("id", "password123"))
      }
    }

    "throw IllegalArgumentException when put short password" in {
      a[IllegalArgumentException] must be thrownBy {
        await(service.create("newUserId", "pass"))
      }
    }

    "throw IllegalArgumentException when put password without a number" in {
      a[IllegalArgumentException] must be thrownBy {
        await(service.create("newUserId", "pass!!!a"))
      }
    }

    "throw IllegalArgumentException when put password without a string" in {
      a[IllegalArgumentException] must be thrownBy {
        await(service.create("newUserId", "1234123!@#"))
      }
    }

    "throw IllegalArgumentException when put password without a special characters" in {
      a[IllegalArgumentException] must be thrownBy {
        await(service.create("newUserId", "pass1234"))
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
