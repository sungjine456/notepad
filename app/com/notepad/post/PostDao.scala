package com.notepad.post

import java.util.Date

import com.notepad.dao.SupportDao
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class PostDao @Inject()(val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends SupportDao {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig.profile.api._

  val posts: TableQuery[Schema] = TableQuery[Schema]

  class Schema(tag: Tag) extends Table[Post](tag, "Post") {

    def idx = column[Long]("idx", O.PrimaryKey)

    def owner = column[Long]("owner")

    def contents = column[String]("contents")

    def updated = column[Option[Date]]("updated")(dateType.optionType)

    def registered = column[Date]("registered")(dateType)

    def * = (idx, owner, contents, updated, registered) <> ((Post.apply _).tupled, Post.unapply)
  }

}
