package com.notepad.post

import play.api.libs.json.{Json, OWrites, Reads}

case class Post(idx: Long, owner: Long, contents: String)

object Post {

  implicit def jsonWrites: OWrites[Post] = Json.writes[Post]

  implicit def jsonReads: Reads[Post] = Json.reads[Post]
}