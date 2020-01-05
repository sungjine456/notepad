package com.notepad.post

import java.util.Date

import com.notepad.common.SequenceService
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostServiceImpl @Inject()(dao: PostDao,
                                databaseSupport: SequenceService
                               )(implicit ec: ExecutionContext) extends PostService {

  import dao.dbConfig.db
  import dao.dbConfig.profile.api._
  import dao.posts

  override def registered(owner: Long, contents: String): Future[Post] = {
    for {
      idx <- databaseSupport.nextValue("Post")
      post <- db run {
        val rows = posts returning posts.map(_.idx) into ((post, idx) => post.copy(idx = idx))

        rows += Post(idx, owner, contents, None, new Date)
      }
    } yield {
      post
    }
  }

  override def findAll(owner: Long): Future[Seq[Post]] = {
    db run {
      posts.result
    }
  }
}
