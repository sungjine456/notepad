package com.notepad.post

import com.notepad.post.PostForms._
import javax.inject._
import play.api.Environment
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class PostController @Inject()(implicit ec: ExecutionContext,
                               cc: ControllerComponents,
                               service: PostService,
                               env: Environment) extends AbstractController(cc) {

  def postView: Action[AnyContent] = Action {
    Ok(views.html.post())
  }

  def registered: Action[AnyContent] = Action { implicit request =>
    val post = postRegisteredForm.bindFromRequest.get

    service.registered(1L, post.contents)

    Ok("registered")
  }
}

object PostForms {
  val postRegisteredForm = Form(
    mapping(
      "contents" -> text
    )(PostRegisteredFormDomain.apply)(PostRegisteredFormDomain.unapply)
  )

  case class PostRegisteredFormDomain(contents: String)

}
