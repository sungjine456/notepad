package com.notepad.user

import com.notepad.user.User._
import com.notepad.user.UserForms._
import javax.inject._
import play.api.data.Forms._
import play.api.data._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.{Environment, Logger}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(implicit ec: ExecutionContext,
                               cc: ControllerComponents,
                               userService: UserService,
                               env: Environment) extends AbstractController(cc) {

  val logger = Logger(this.getClass)

  def signup: Action[AnyContent] = Action async { implicit request =>
    val user = userRegisteredForm.bindFromRequest.get

    logger.info(s"sign up = id : ${user.id}, password : ${user.password}")

    try {
      userService.create(user.id, user.password).map(_ => Ok)
    } catch {
      case _: IllegalArgumentException =>
        logger.debug(s"sign up IllegalArgumentException = id : ${user.id}, password : ${user.password}")
        Future.successful(NotFound)
    }
  }

  def users: Action[AnyContent] = Action async {
    userService.findAll().map(users => Ok(Json.toJson(users)))
  }

  def exist(id: String): Action[AnyContent] = Action async {
    userService.findById(id).map {
      case Some(_) => Ok
      case _ => NotFound
    }
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
