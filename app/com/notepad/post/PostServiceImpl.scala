package com.notepad.post

import com.mohiva.play.silhouette.api.util.Clock
import com.notepad.common.{InvalidCredentialsException, NotFoundException, SequenceService}
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

  override def findByIdx(idx: Long): Future[Option[Post]] = {
    db run {
      posts.filter(_.idx === idx).result.headOption
    }
  }

  override def findByIdxAndOwner(idx: Long, owner: Long): Future[Option[Post]] = {
    db run {
      posts.filter(posts => posts.idx === idx && posts.owner === owner).result.headOption
    }
  }

  override def update(idx: Long, owner: Long, contents: String): Future[Unit] = {
    def updateContents(): Future[Int] = {
      db run {
        posts.filter(p => p.idx === idx && p.owner === owner)
          .map(post => (post.contents, post.updated))
          .update((contents, Some(clock.now.toDate)))
      }
    }

    for {
      post <- findByIdx(idx) map {
        case None =>
          throw new NotFoundException(s"not found idx : $idx")
        case p => p
      }
      _ <- post.filter(_.owner == owner) match {
        case None =>
          throw new InvalidCredentialsException(s"insufficient permission idx : $idx, owner : $owner")
        case _ =>
          Future.successful({})
      }
      _ <- updateContents()
    } yield {}
  }
}
