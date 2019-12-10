package com.notepad.session

import com.mohiva.play.silhouette.api.{Env, Identity}
import com.mohiva.play.silhouette.impl.authenticators.BearerTokenAuthenticator

case class SessionContent(userID: Long) extends Identity {
}

trait DefaultEnv extends Env {
  type I = SessionContent
  type A = BearerTokenAuthenticator
}
