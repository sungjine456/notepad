package com.notepad.user

import com.notepad.common.SequenceService
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserServiceImpl @Inject()(dao: UserDao,
                                databaseSupport: SequenceService
                               )(implicit ec: ExecutionContext) extends UserService {

  import dao.dbConfig.db
  import dao.dbConfig.profile.api._
  import dao.users

  override def findAll(): Future[Seq[User]] = {
    db run {
      users.sortBy(_.id).result
    }
  }

  override def create(id: String, password: String): Future[User] = {
    require(checkString(id, 6, 12), "")
    require(checkString(password, 8, 20), "")

    for {
      idx <- databaseSupport.nextValue("User")
      user <- db run {
        val rows = users returning users.map(_.idx) into ((user, idx) => user.copy(idx = idx))

        rows += User(idx, id, password)
      }
    } yield {
      user
    }
  }

  private def checkString(str: String, min: Int, max: Int): Boolean = {
    str != null && str.length >= min && str.length <= max
  }
}
