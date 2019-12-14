package com.notepad.security

import com.mohiva.play.silhouette.api.Identity

case class SecurityContent(userID: Long) extends Identity {
}
