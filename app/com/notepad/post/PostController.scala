package com.notepad.post

import com.mohiva.play.silhouette.api.Silhouette
import com.notepad.post.PostForms._
import com.notepad.security.{DefaultEnv, SecuredController}
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
                               val silhouette: Silhouette[DefaultEnv],
                               env: Environment) extends AbstractController(cc)
  with SecuredController[DefaultEnv]{

  def registered: Action[AnyContent] = SecuredAction { implicit request =>
    val post = postRegisteredForm.bindFromRequest.get

    service.registered(1L, post.contents)

    Ok("registered")
  }
}

object PostForms {

  case class PostRegisteredFormDomain(contents: String)

  val postRegisteredForm: Form[PostRegisteredFormDomain] = Form(
    mapping(
      "contents" -> text
    )(PostRegisteredFormDomain.apply)(PostRegisteredFormDomain.unapply)
  )
}
