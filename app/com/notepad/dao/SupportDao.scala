package com.notepad.dao

import java.sql.Timestamp
import java.util.Date

import slick.ast.BaseTypedType
import slick.basic.DatabaseConfig
import slick.jdbc.{JdbcProfile, JdbcType}

trait SupportDao {

  val dbConfig: DatabaseConfig[JdbcProfile]

  implicit def dateType: JdbcType[Date] with BaseTypedType[Date] = {
    import dbConfig.profile.api._

    dbConfig.profile.MappedColumnType.base[Date, Timestamp](
      d => new Timestamp(d.getTime),
      t => new Date(t.getTime))
  }
}
