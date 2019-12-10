package com.notepad.session

import java.util.UUID

import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.impl.providers.CommonSocialProfile

trait SessionService extends IdentityService[SessionContent] {

  def retrieve(id: UUID): Unit

  def save(user: SessionContent): Unit

  def save(profile: CommonSocialProfile): Unit
}
