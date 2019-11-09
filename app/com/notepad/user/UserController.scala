package com.notepad.user

import javax.inject._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import UserForms._
import play.api.Environment
import User._
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(implicit ec: ExecutionContext,
                               cc: ControllerComponents,
                               userService: UserService,
                               env: Environment) extends AbstractController(cc) {

  def signup: Action[AnyContent] = Action async { implicit request =>
    val user = userRegisteredForm.bindFromRequest.get

    try {
      userService.create(user.id, user.password).map(_ => Ok)
    } catch {
      case _: IllegalArgumentException => Future.successful(NotFound)
    }
  }

  def users: Action[AnyContent] = Action async {
    userService.findAll().map(users => Ok(Json.toJson(users)))
  }
}

object UserForms {
  val userRegisteredForm = Form(
    mapping(
      "id" -> text,
      "password" -> text
    )(UserRegisteredFormDomain.apply)(UserRegisteredFormDomain.unapply)
  )

  case class UserRegisteredFormDomain(id: String, password: String)
}
