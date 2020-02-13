package com.notepad.post

import com.notepad.common.{InvalidCredentialsException, NotFoundException}
import com.notepad.test.SupportDatabaseTest
import org.scalatestplus.play.PlaySpec

class PostServiceImplTest extends PlaySpec with SupportDatabaseTest {

  val service: PostService = new PostServiceImpl(postDao, sequenceService, clock)

  "registered(owner: Long, contents: String)" should {
    "succeed" in {
      val beforeFindAll: Seq[Post] = await(service.findAll(1))

      beforeFindAll.length mustBe 2

      await(service.registered(1, "contents"))

      val afterFindAll: Seq[Post] = await(service.findAll(1))

      afterFindAll.length mustBe 3
    }
  }

  "findAll(owner)" should {
    "succeed" in {
      val result: Seq[Post] = await(service.findAll(1))

      result.length mustBe 2
    }
  }

  "findByIdx(idx)" should {
    "succeed" in {
      val result: Option[Post] = await(service.findByIdx(1))

      result.head.contents mustBe "first contents"
    }

    "does not found when wrong the idx" in {
      val result: Option[Post] = await(service.findByIdx(100))

      result mustBe None
    }
  }

  "findByIdxAndOwner(idx, owner)" should {
    "succeed" in {
      val result: Option[Post] = await(service.findByIdxAndOwner(1, 1))

      result.head.contents mustBe "first contents"
    }

    "does not found when wrong the idx" in {
      val result: Option[Post] = await(service.findByIdxAndOwner(100, 1))

      result mustBe None
    }

    "does not found when wrong the owner" in {
      val result: Option[Post] = await(service.findByIdxAndOwner(1, 100))

      result mustBe None
    }
  }

  "update(idx, owner, contents)" should {
    "succeed" in {
      val beforeFindAll: Option[Post] = await(service.findByIdx(1))

      beforeFindAll.head.contents mustBe "first contents"

      await(service.update(1, 1, "update contents"))

      val afterFindAll: Option[Post] = await(service.findByIdx(1))

      afterFindAll.head.contents mustBe "update contents"
    }

    "fail if not exists the post" in {
      an[NotFoundException] should be thrownBy {
        await(service.update(100, 1, "update contents"))
      }
    }

    "fail if the owner is not the author of the posting" in {
      an[InvalidCredentialsException] should be thrownBy {
        await(service.update(1, 100, "update contents"))
      }
    }
  }

  "delete(idx, owner)" should {
    "succeed" in {
      val beforeFindAll: Option[Post] = await(service.findByIdx(1))

      beforeFindAll.size mustBe 1

      await(service.delete(1, 1))

      val afterFindAll: Option[Post] = await(service.findByIdx(1))

      afterFindAll.size mustBe 0
    }

    "fail if not exists the post" in {
      an[NotFoundException] should be thrownBy {
        await(service.delete(100, 1))
      }
    }

    "fail if the owner is not the author of the posting" in {
      an[InvalidCredentialsException] should be thrownBy {
        await(service.delete(1, 100))
      }
    }
  }
}
