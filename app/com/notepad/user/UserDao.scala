package com.notepad.user

import java.sql.Timestamp
import java.util.Date

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.ast.BaseTypedType
import slick.jdbc.{JdbcProfile, JdbcType}

import scala.concurrent.ExecutionContext

@Singleton
class UserDao @Inject()(val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

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

  implicit def dateType: JdbcType[Date] with BaseTypedType[Date] = {
    val api = dbConfig.profile.api

    import api._

    dbConfig.profile.MappedColumnType.base[Date, Timestamp](
      d => new Timestamp(d.getTime),
      t => new Date(t.getTime))
  }
}
