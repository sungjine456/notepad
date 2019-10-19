package com.notepad.test

import scala.concurrent.ExecutionContext

import play.api.db.slick.DatabaseConfigProvider
import play.api.inject.Injector

trait DatabaseTest extends ApplicationTest {

  private val injector: Injector = application.injector

  implicit val executionContext: ExecutionContext = injector.instanceOf[ExecutionContext]

  val provider: DatabaseConfigProvider = injector.instanceOf[DatabaseConfigProvider]
}
