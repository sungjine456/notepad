package com.notepad.user

import java.util.Date

import play.api.libs.json.{Json, OWrites, Reads}

case class User(idx: Long, id: String, password: String, updated: Option[Date], registered: Date)

object User {

  implicit def jsonWrites: OWrites[User] = Json.writes[User]

  implicit def jsonReads: Reads[User] = Json.reads[User]
}