package com.notepad.security

import java.nio.charset.StandardCharsets.UTF_8
import java.util.Base64

import com.mohiva.play.silhouette.api.util.{PasswordHasher, PasswordInfo}
import com.notepad.security.PasswordHasherImpl._
import io.github.nremond.PBKDF2
import javax.inject.Inject
import play.api.Configuration

class PasswordHasherImpl @Inject()(val config: Configuration) extends PasswordHasher {

  private def salt: String = config.get[String]("notepad.authentication.salt")

  override def id: String = "PBKDF2"

  override def hash(plainPassword: String): PasswordInfo = {
    require(plainPassword != null, "Missing argument 'plainPassword'.")

    val (id, password) = {
      val values = plainPassword.split(Separator, 2)

      if (values.length == 2) {
        (values.head, values.last)
      } else {
        ("", plainPassword)
      }
    }

    val data = PBKDF2(password.getBytes(UTF_8), salt.getBytes)
    val value = Base64.getEncoder.encodeToString(data)

    PasswordInfo(id, value, Option(salt))
  }

  override def matches(passwordInfo: PasswordInfo, suppliedPassword: String): Boolean = {
    require(passwordInfo != null, "Missing argument 'passwordInfo'.")
    require(suppliedPassword != null, "Missing argument 'suppliedPassword'.")

    hash(suppliedPassword).password == passwordInfo.password
  }

  override def isDeprecated(passwordInfo: PasswordInfo): Option[Boolean] = {
    require(passwordInfo != null, "Missing argument 'passwordInfo'.")

    passwordInfo.salt.map(_ == salt)
  }
}

object PasswordHasherImpl {

  val Separator: String = ":"
}