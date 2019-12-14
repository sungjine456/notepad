package com.notepad.security

import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators.BearerTokenAuthenticator

trait DefaultEnv extends Env {
  type I = SecurityContent
  type A = BearerTokenAuthenticator
}
