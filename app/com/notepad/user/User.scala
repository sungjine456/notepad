package com.notepad.user

import java.util.Date

import play.api.libs.json.Json

case class User(idx: Long, id: String, password: String, updated: Option[Date], registered: Date)

object User {

  implicit def jsonWrites = Json.writes[User]

  implicit def jsonReads = Json.reads[User]
}