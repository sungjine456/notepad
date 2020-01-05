package com.notepad.user

import java.util.Date

import com.notepad.dao.SupportDao
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class UserDao @Inject()(val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends SupportDao {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig.profile.api._

  val users: TableQuery[Schema] = TableQuery[Schema]

  class Schema(tag: Tag) extends Table[User](tag, "User") {

    def idx = column[Long]("idx", O.PrimaryKey)

    def id = column[String]("id")

    def password = column[String]("password")

    def updated = column[Option[Date]]("updated")

    def registered = column[Date]("registered")

    def * = (idx, id, password, updated, registered) <> ((User.apply _).tupled, User.unapply)
  }

}
