package com.notepad.post

import com.notepad.common.{SequenceDao, SequenceService}
import com.notepad.test.SupportDatabaseTest
import org.scalatestplus.play.PlaySpec

class PostServiceImplTest extends PlaySpec with SupportDatabaseTest {

  val service: PostService = {
    val sequenceDao = new SequenceDao(provider)

    val sequenceService = new SequenceService(sequenceDao)

    new PostServiceImpl(postDao, sequenceService)
  }

  "registered(owner: Long, contents: String)" should {
    "succeed" in {
      val beforeFindAll: Seq[Post] = await(service.findAll(1))

      beforeFindAll.length mustBe 1

      await(service.registered(1, "contents"))

      val afterFindAll: Seq[Post] = await(service.findAll(1))

      afterFindAll.length mustBe 2
    }
  }

  "findAll()" should {
    "succeed" in {
      val result: Seq[Post] = await(service.findAll(1))

      result.length mustBe 1
    }
  }

  "findByIdxAndOwner(idx, owner)" should {
    "succeed" in {
      val result: Option[Post] = await(service.findByIdxAndOwner(1, 1))

      result.head.contents mustBe "new contents"
    }
  }

  "update(idx, contents)" should {
    "succeed" in {
      val beforeFindAll: Seq[Post] = await(service.findAll(1))

      beforeFindAll.head.contents mustBe "new contents"

      await(service.update(1, "update contents"))

      val afterFindAll: Seq[Post] = await(service.findAll(1))

      afterFindAll.head.contents mustBe "update contents"
    }
  }
}
