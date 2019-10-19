package com.notepad.test

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, Future }

import play.api.inject.guice.GuiceApplicationBuilder
import play.api.{ Application, Configuration, Mode }

import com.typesafe.config.ConfigFactory

trait ApplicationTest {

  def application: Application = GuiceApplicationBuilder()
    .configure(Configuration(ConfigFactory.load("test.conf")))
    .in(Mode.Test)
    .build()

  def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)
}