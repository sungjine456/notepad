package com.notepad.test

import java.util.UUID

import javax.inject.{Inject, Provider}
import net.sf.ehcache.CacheManager
import net.sf.ehcache.config.ConfigurationFactory
import play.api.{Configuration, Environment}
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future.successful

class CacheManagerProvider @Inject()(val environment: Environment,
                                     val config: Configuration,
                                     lifecycle: ApplicationLifecycle) extends Provider[CacheManager] {

  lazy val get: CacheManager = {
    val resourceName = config.get[String]("play.cache.configResource")
    val configResource = environment.resource(resourceName) getOrElse {
      environment.classLoader.getResource("ehcache-default.xml")
    }

    val settings = ConfigurationFactory.parseConfiguration(configResource)
    settings.setName(UUID.randomUUID.toString)

    val manager = CacheManager.newInstance(settings)

    lifecycle.addStopHook(() => successful {
      manager.shutdown()
    })

    manager
  }
}
