package com.notepad.logging

import play.api.Logger

trait LoggingSupport {

  val logger: Logger = Logger(getClass)
}
