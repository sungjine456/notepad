package com.notepad.post

import java.util.Date

import play.api.libs.json.{Json, Reads, Writes}

case class Post(idx: Long, owner: Long, contents: String, updated: Option[Date], registered: Date, removed: Boolean)

object Post {

  implicit val writer: Writes[Post] = (post: Post) => {
    Json.obj(
      "idx" -> post.idx,
      "contents" -> post.contents,
      "updated" -> post.updated,
      "registered" -> post.registered,
      "owner" -> post.idx)
  }

  implicit def jsonReads: Reads[Post] = Json.reads[Post]
}
