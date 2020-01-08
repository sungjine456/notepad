package com.notepad.security

import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.impl.providers._
import com.notepad.logging.LoggingSupport
import com.notepad.security.SignInForms.{SignInFormDomain, signInForm}
import com.notepad.user.UserService
import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.libs.json.Json
import play.api.mvc.{Action, _}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SecurityController @Inject()(implicit val executionContext: ExecutionContext,
                                 val controllerComponents: ControllerComponents,
                                 val userService: UserService,
                                 val silhouette: Silhouette[DefaultEnv],
                                 val securityService: SecurityService) extends SecuredController[DefaultEnv]
  with LoggingSupport {

  def signIn: Action[SignInFormDomain] = Action.async(parse.form(signInForm)) { implicit request =>
    val SignInFormDomain(id, password) = request.body

    logger.info(s"sign in = id : $id, password : $password")

    userService.findByIdAndPassword(id, password) flatMap {
      case Some(user) =>
        val loginInfo = LoginInfo(CredentialsProvider.ID, user.idx.toString)

        securityService.retrieve(loginInfo) flatMap {
          case Some(securityContent) =>
            authenticatorService.create(loginInfo) flatMap { authenticator =>
              eventBus.publish(LoginEvent(securityContent, request))

              authenticatorService.init(authenticator) map { token =>
                Ok(Json.obj("token" -> token))
              }
            }
          case None => Future.successful(Unauthorized)
        }
      case None => Future.successful(Unauthorized)
    }
  }

  def signOut: Action[AnyContent] = SecuredAction async { implicit request =>
    eventBus publish {
      LogoutEvent(request.identity, request)
    }

    authenticatorService.discard(request.authenticator, Ok)
  }
}

object SignInForms {

  case class SignInFormDomain(id: String, password: String)

  val signInForm = Form(
    mapping(
      "id" -> text,
      "password" -> text)(SignInFormDomain.apply)(SignInFormDomain.unapply))
}
