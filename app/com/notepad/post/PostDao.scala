package com.notepad.post

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class PostDao @Inject()(val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig.profile.api._

  val posts: TableQuery[Schema] = TableQuery[Schema]

  class Schema(tag: Tag) extends Table[Post](tag, "Post") {

    def idx = column[Long]("idx", O.PrimaryKey)

    def owner = column[Long]("owner")

    def contents = column[String]("contents")

    def * = (idx, owner, contents) <> ((Post.apply _).tupled, Post.unapply)
  }

}
