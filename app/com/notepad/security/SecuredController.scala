package com.notepad.security

import com.mohiva.play.silhouette.api.actions.{SecuredActionBuilder, UnsecuredActionBuilder}
import com.mohiva.play.silhouette.api.services.AuthenticatorService
import com.mohiva.play.silhouette.api.{EventBus, Silhouette}
import play.api.mvc.{AnyContent, BaseController}

trait SecuredController[A <: DefaultEnv] extends BaseController {

  def silhouette: Silhouette[A]

  def authenticatorService: AuthenticatorService[A#A] = silhouette.env.authenticatorService

  def eventBus: EventBus = silhouette.env.eventBus

  def SecuredAction: SecuredActionBuilder[A, AnyContent] = silhouette.SecuredAction

  def UnsecuredAction: UnsecuredActionBuilder[A, AnyContent] = silhouette.UnsecuredAction
}
