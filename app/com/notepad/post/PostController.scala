package com.notepad.post

import com.mohiva.play.silhouette.api.Silhouette
import com.notepad.post.PostForms._
import com.notepad.security.{DefaultEnv, SecuredController}
import javax.inject._
import play.api.Environment
import play.api.data.Forms._
import play.api.data._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class PostController @Inject()(implicit ec: ExecutionContext,
                               cc: ControllerComponents,
                               service: PostService,
                               val silhouette: Silhouette[DefaultEnv],
                               env: Environment) extends AbstractController(cc)
  with SecuredController[DefaultEnv]{

  def find(idx: Int): Action[AnyContent] = SecuredAction async { implicit request =>
    service.findByIdxAndOwner(idx, request.identity.id) map { post =>
      Ok(Json.toJson(post))
    }
  }

  def posts: Action[AnyContent] = SecuredAction async { implicit request =>
    service.findAll(request.identity.id) map { list =>
      Ok(Json.toJson(list))
    }
  }

  def registered: Action[AnyContent] = SecuredAction async { implicit request =>
    val post = postRegisteredForm.bindFromRequest.get

    service.registered(request.identity.id, post.contents).map(_ => Ok)
  }

  def updated(idx: Int): Action[AnyContent] = SecuredAction async { implicit request =>
    val post = postRegisteredForm.bindFromRequest.get

    service.update(idx, request.identity.id, post.contents).map(_ => Ok)
  }
}

object PostForms {

  case class PostRegisteredFormDomain(contents: String)

  val postRegisteredForm: Form[PostRegisteredFormDomain] = Form(
    mapping(
      "contents" -> text(maxLength = 200)
    )(PostRegisteredFormDomain.apply)(PostRegisteredFormDomain.unapply)
  )
}
