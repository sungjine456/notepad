package com.notepad.post

import java.util.Date

import play.api.libs.json.{Json, OWrites, Reads}

case class Post(idx: Long, owner: Long, contents: String, updated: Option[Date], registered: Date)

object Post {

  implicit def jsonWrites: OWrites[Post] = Json.writes[Post]

  implicit def jsonReads: Reads[Post] = Json.reads[Post]
}