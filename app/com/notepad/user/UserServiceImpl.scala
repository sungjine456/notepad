package com.notepad.user

import java.util.Date

import com.mohiva.play.silhouette.api.util.{PasswordHasher, PasswordInfo}
import com.notepad.common.SequenceService
import com.notepad.security.PasswordHasherImpl.Separator
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserServiceImpl @Inject()(dao: UserDao,
                                databaseSupport: SequenceService,
                                hasher: PasswordHasher
                               )(implicit ec: ExecutionContext) extends UserService {

  import dao.dbConfig.db
  import dao.dbConfig.profile.api._
  import dao.users

  override def findAll(): Future[Seq[User]] = {
    db run {
      users.sortBy(_.id).result
    }
  }

  override def findById(id: String): Future[Option[User]] = {
    db run {
      users.filter(_.id === id).result.headOption
    }
  }

  override def findByIdx(idx: Long): Future[Option[User]] = {
    db run {
      users.filter(_.idx === idx).result.headOption
    }
  }

  override def findByIdAndPassword(id: String, password: String): Future[Option[User]] = {
    val passwordInfo = makePasswordInfo(id, password)

    db run {
      users.filter(user => user.id === id && user.password === passwordInfo.password).result.headOption
    }
  }

  override def create(id: String, password: String): Future[User] = {
    require(id != null && id.length >= 6 && id.length <= 12, s"$id is wrong id")
    require(checkPassword(password), s"$password is wrong password")

    val now = new Date()

    val validate = findById(id) map {
      case Some(_) => throw new IllegalArgumentException(s"$id is already exists.")
      case _ =>
    }

    for {
      _ <- validate
      idx <- databaseSupport.nextValue("User")
      user <- db run {
        val passwordInfo = makePasswordInfo(id, password)

        val rows = users returning users.map(_.idx) into ((user, idx) => user.copy(idx = idx))

        rows += User(idx, id, passwordInfo.password, None, now)
      }
    } yield {
      user
    }
  }

  private def checkPassword(password: String): Boolean = {
    val regex = "(?=.*[a-zA-Z])(?=.*[!@#$%^~*+=-])(?=.*[0-9]).{8,20}".r

    regex.findFirstIn(password).isDefined
  }

  private def makePasswordInfo(id: String, password: String): PasswordInfo = {
    hasher.hash(Seq(id, password).mkString(Separator))
  }
}
