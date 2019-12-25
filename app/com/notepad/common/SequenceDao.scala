package com.notepad.common

import com.google.inject.Singleton
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.ExecutionContext

@Singleton
class SequenceDao @Inject()(val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig.profile.api._

  val sequences: TableQuery[Schema] = TableQuery[Schema]

  class Schema(tag: Tag) extends Table[(String, Long)](tag, "Sequence") {

    def id: Rep[String] = column[String]("id", O.PrimaryKey)

    def value: Rep[Long] = column[Long]("value")

    def * : ProvenShape[(String, Long)] = (id, value)
  }

}