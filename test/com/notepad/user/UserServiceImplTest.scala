package com.notepad.user

import com.notepad.common.{SequenceDao, SequenceService}
import com.notepad.security.PasswordHasherImpl.Separator
import com.notepad.test.SupportDatabaseTest
import org.scalatestplus.play.PlaySpec

class UserServiceImplTest extends PlaySpec with SupportDatabaseTest {

  val service: UserService = new UserServiceImpl(userDao, sequenceService, hasher)

  "create(id: String, password: String)" should {
    "succeed" in {
      val id = "newUserId2"
      val password = "pass123!"

      val beforeFindAll: Seq[User] = await(service.findAll())

      beforeFindAll.length mustBe 1

      val user = await(service.create(id, password))

      val afterFindAll: Seq[User] = await(service.findAll())

      val passwordInfo = hasher.hash(Seq(id, password).mkString(Separator))

      afterFindAll.length mustBe 2
      user.id mustBe id
      user.password mustBe passwordInfo.password
    }

    "throw IllegalArgumentException when putting existing id" in {
      a[IllegalArgumentException] must be thrownBy {
        await(service.create("newUserId", "password123"))
      }
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

  "findById(id: String)" should {
    "return None when does not found by id" in {
      await(service.findById("userId")).isEmpty mustBe true
    }

    "return Some(User) when does not found by id" in {
      await(service.findById("newUserId")).isDefined mustBe true
    }
  }

  "findByIdx(idx: Long)" should {
    "return None when does not found by idx" in {
      await(service.findByIdx(100L)).isEmpty mustBe true
    }

    "return Some(User) when does not found by idx" in {
      await(service.findByIdx(1L)).isDefined mustBe true
    }
  }

  "findByIdAndPassword(id: String, password: String)" should {
    "return None when does not found by id and password" in {
      await(service.findByIdAndPassword("userId", "password")).isEmpty mustBe true
    }

    "return Some(User) when does not found by id and password" in {
      await(service.findByIdAndPassword("newUserId", "password")).isDefined mustBe true
    }
  }
}
