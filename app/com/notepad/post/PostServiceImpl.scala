package com.notepad.post

import com.mohiva.play.silhouette.api.util.Clock
import com.notepad.common.SequenceService
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostServiceImpl @Inject()(dao: PostDao,
                                databaseSupport: SequenceService,
                                clock: Clock
                               )(implicit ec: ExecutionContext) extends PostService {

  import dao._
  import dao.dbConfig.db
  import dao.dbConfig.profile.api._

  override def registered(owner: Long, contents: String): Future[Post] = {
    for {
      idx <- databaseSupport.nextValue("Post")
      post <- db run {
        val rows = posts returning posts.map(_.idx) into ((post, idx) => post.copy(idx = idx))

        rows += Post(idx, owner, contents, None, clock.now.toDate)
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

  def findByIdxAndOwner(idx: Long, owner: Long): Future[Option[Post]] = {
    db run {
      posts.filter(posts => posts.idx === idx && posts.owner === owner).result.headOption
    }
  }

  override def update(idx: Long, contents: String): Future[Int] = {
    db run {
      posts.filter(_.idx === idx)
        .map(post => (post.contents, post.updated))
        .update((contents, Some(clock.now.toDate)))
    }
  }
}
