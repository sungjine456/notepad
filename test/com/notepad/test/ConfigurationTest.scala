package com.notepad.test

import com.notepad.security.PasswordHasherImpl
import com.typesafe.config.ConfigFactory
import play.api.Configuration

trait ConfigurationTest {

  def config = Configuration(ConfigFactory.load("test.conf"))

  val hasher = new PasswordHasherImpl(config)
}
