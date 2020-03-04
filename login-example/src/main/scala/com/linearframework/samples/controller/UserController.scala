package com.linearframework.samples.controller

import com.linearframework.i18n.ResourceBundle
import com.linearframework.samples.model.NewUserForm
import com.linearframework.samples.service.UserService
import com.linearframework.validation.ValidationException
import com.linearframework.web._
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale
import scala.util.control.NonFatal

object UserController extends Controller {
  private lazy val messageBundle = the[ResourceBundle]

  POST("/user") { (request, response) =>
    try {
      val form = request.as[NewUserForm].validated
      val userId = UserService.createUser(form.username, form.password)
      userId // todo: log in the user
    }
    catch {
      case NonFatal(e) =>
        val error = encodeErrorMessage(e, request.raw().getLocale) // todo: remove raw() call
        response.redirect(s"/html/newuser.html?error=$error")
    }
    "OK" // todo: remove return value
  }

  private def encodeErrorMessage(e: Throwable, locale: Locale): String = {
    val messages = messageBundle.forLocale(locale)

    val error = e match {
      case e: ValidationException =>
        e.errors
          .flatMap { case (_, errors) => errors }
          .map(error => messages(error).getOrElse(error))
          .mkString(" ")
      case e =>
        messages(e.getMessage).getOrElse(e.getClass + ": " + e.getMessage)
    }

    URLEncoder.encode(error, StandardCharsets.UTF_8.toString)
  }

}
