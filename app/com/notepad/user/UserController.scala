package com.notepad.user

import javax.inject._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import UserForms._
import play.api.Environment

@Singleton
class UserController @Inject()(cc: ControllerComponents,
                               env: Environment) extends AbstractController(cc) {

  def signupView: Action[AnyContent] = Action {
    Ok(views.html.signup())
  }

  def signup: Action[AnyContent] = Action { implicit request =>
    val user = userRegisteredForm.bindFromRequest.get

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
