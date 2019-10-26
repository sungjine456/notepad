package com.notepad.user

import javax.inject._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import UserForms._
import play.api.Environment

import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject()(implicit ec: ExecutionContext,
                               cc: ControllerComponents,
                               userService: UserService,
                               env: Environment) extends AbstractController(cc) {

  def signup: Action[AnyContent] = Action { implicit request =>
    val user = userRegisteredForm.bindFromRequest.get

    userService.create(user.id, user.password)

    Ok("registered")
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
