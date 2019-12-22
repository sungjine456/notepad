package com.notepad.security

import com.mohiva.play.silhouette.api.services.IdentityService

trait SecurityService extends IdentityService[SecurityContent]
