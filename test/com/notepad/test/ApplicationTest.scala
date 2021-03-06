package com.notepad.test

import com.typesafe.config.ConfigFactory
import net.sf.ehcache.CacheManager
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.{Application, Configuration, Mode}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

trait ApplicationTest {

  def application: Application = GuiceApplicationBuilder()
    .configure(Configuration(ConfigFactory.load("test.conf")))
    .in(Mode.Test)
    .overrides(bind[CacheManager].toProvider[CacheManagerProvider])
    .build()

  def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)
}
