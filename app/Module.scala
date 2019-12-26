import com.google.inject.{AbstractModule, Provides}
import com.mohiva.play.silhouette.api.services.AuthenticatorService
import com.mohiva.play.silhouette.api.util._
import com.mohiva.play.silhouette.api.{Environment, EventBus, Silhouette, SilhouetteProvider}
import com.mohiva.play.silhouette.impl.authenticators.{BearerTokenAuthenticator, BearerTokenAuthenticatorService, BearerTokenAuthenticatorSettings}
import com.mohiva.play.silhouette.impl.util.{PlayCacheLayer, SecureRandomIDGenerator}
import com.mohiva.play.silhouette.persistence.repositories.CacheAuthenticatorRepository
import com.notepad.post.{PostService, PostServiceImpl}
import com.notepad.security.{DefaultEnv, PasswordHasherImpl, SecurityService, SecurityServiceImpl}
import com.notepad.user.{UserService, UserServiceImpl}
import net.codingwell.scalaguice.ScalaModule
import play.api.Configuration

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationLong

class Module(val environment: play.api.Environment,
             val config: Configuration) extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[UserService].to[UserServiceImpl]
    bind[PostService].to[PostServiceImpl]
    bind[SecurityService].to[SecurityServiceImpl]
    bind[PasswordHasher].to[PasswordHasherImpl]
    bind[Silhouette[DefaultEnv]].to[SilhouetteProvider[DefaultEnv]]
    bind[CacheLayer].to[PlayCacheLayer]
    bind[IDGenerator] toInstance new SecureRandomIDGenerator
    bind[EventBus] toInstance EventBus()
    bind[Clock] toInstance Clock()
  }

  def authenticatorSettings: BearerTokenAuthenticatorSettings = {
    val key = s"notepad.authentication.${BearerTokenAuthenticatorService.ID}"

    val settings = config.get[Configuration](key)

    val header = settings.get[String]("headerName")
    val idleTimeout = settings.getMillis("authenticatorIdleTimeout").milliseconds
    val expiration = settings.getMillis("authenticatorExpiry").milliseconds

    BearerTokenAuthenticatorSettings(
      fieldName = header,
      authenticatorIdleTimeout = Option(idleTimeout),
      authenticatorExpiry = expiration)
  }

  @Provides
  def createAuthenticatorService(cache: CacheLayer,
                                 generator: IDGenerator,
                                 clock: Clock): AuthenticatorService[BearerTokenAuthenticator] = {

    val repository = new CacheAuthenticatorRepository[BearerTokenAuthenticator](cache)

    new BearerTokenAuthenticatorService(authenticatorSettings, repository, generator, clock)
  }

  @Provides
  def createEnvironment(securityService: SecurityServiceImpl,
                        authenticatorService: AuthenticatorService[BearerTokenAuthenticator],
                        eventBus: EventBus): Environment[DefaultEnv] = {

    Environment[DefaultEnv](
      securityService,
      authenticatorService,
      Seq(),
      eventBus)
  }
}
