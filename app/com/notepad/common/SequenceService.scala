package com.notepad.common

import com.google.inject.Singleton
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SequenceService @Inject()(dao: SequenceDao)(implicit ec: ExecutionContext) {

  import dao.dbConfig.db
  import dao.dbConfig.profile.api._
  import dao.sequences

  def nextValue(sequence: String): Future[Long] = {
    db run {
      def getCurrentValue =
        sql"""select "value" from "Sequence" where "id" = $sequence for update""".as[Long].headOption

      def checkValue(value: Option[Long]): Long = value getOrElse {
        throw new NoSuchElementException(
          s"The specified sequence does not exists: '$sequence'.")
      }

      val result = for {
        current <- getCurrentValue.map(checkValue)
        _ <- sequences.filter(_.id === sequence).map(_.value).update(current + 1)
      } yield current

      result.transactionally
    }
  }
}

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
