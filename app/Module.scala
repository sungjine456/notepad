import com.google.inject.{AbstractModule, Provides, TypeLiteral}
import com.mohiva.play.silhouette.api.services.AuthenticatorService
import com.mohiva.play.silhouette.api.util.{CacheLayer, Clock, IDGenerator}
import com.mohiva.play.silhouette.api.{Environment, EventBus, Silhouette, SilhouetteProvider}
import com.mohiva.play.silhouette.impl.authenticators.{BearerTokenAuthenticator, BearerTokenAuthenticatorService, BearerTokenAuthenticatorSettings}
import com.mohiva.play.silhouette.impl.util.SecureRandomIDGenerator
import com.mohiva.play.silhouette.persistence.repositories.CacheAuthenticatorRepository
import com.notepad.post.{PostService, PostServiceImpl}
import com.notepad.security.{DefaultEnv, SecurityServiceImpl}
import com.notepad.user.{UserService, UserServiceImpl}
import play.api.Configuration

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationLong

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module(val environment: play.api.Environment,
             val config: Configuration) extends AbstractModule {

  override def configure(): Unit = {
    registerBindings()

    bind(classOf[UserService]).to(classOf[UserServiceImpl])
    bind(classOf[PostService]).to(classOf[PostServiceImpl])
    bind(new TypeLiteral[Silhouette[DefaultEnv]] {}).to(new TypeLiteral[SilhouetteProvider[DefaultEnv]] {})
    bind(classOf[IDGenerator]).toInstance(new SecureRandomIDGenerator)
    bind(classOf[Clock]).toInstance(Clock())
  }

  private def registerBindings(): Unit = {
    val subConfig = config.get[Configuration]("notepad.bindings")

    val loader = environment.classLoader
    val bindings: Set[String] = subConfig.subKeys

    def register[A](binding: String): Unit = {
      val typeName = binding.replace('/', '.')
      val typeClass = loader.loadClass(typeName).asInstanceOf[Class[A]]

      val implName = subConfig.get[String](binding).replace('/', '.')
      val implClass = loader.loadClass(implName).asSubclass(typeClass)

      bind(typeClass) to implClass
    }

    bindings.foreach(register)
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

    new BearerTokenAuthenticatorService(
      authenticatorSettings, repository, generator, clock)
  }

  @Provides
  def createEnvironment(securityManager: SecurityServiceImpl,
                        authenticatorService: AuthenticatorService[BearerTokenAuthenticator],
                        bus: EventBus): Environment[DefaultEnv] = {

    Environment[DefaultEnv](
      securityManager,
      authenticatorService,
      Seq(),
      bus)
  }
}
