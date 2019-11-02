package com.notepad.user

import play.api.libs.json.Json

case class User(idx: Long, id: String, password: String)

object User {
  implicit def jsonWrites = Json.writes[User]

  implicit def jsonReads = Json.reads[User]
}