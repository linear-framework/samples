package com.linearframework.samples.controller

import com.linearframework.i18n.ResourceBundle
import com.linearframework.samples.model.{LoginForm, NewUserForm}
import com.linearframework.samples.service.{LoginService, UserService}
import com.linearframework.validation.ValidationException
import com.linearframework.web._
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale
import scala.util.control.NonFatal

object LoginController extends Controller {
  private lazy val messageBundle = the[ResourceBundle]

  POST("/login") { (request, response) =>
    try {
      val form = request.as[LoginForm].validated
      val loginId = LoginService.login(form.username, form.password)
      doLogin(loginId, response)
    }
    catch {
      case NonFatal(e) =>
        val error = encodeErrorMessage(e, request.getLocale)
        response.redirect(s"/html/login.html?error=$error")
    }
  }

  GET("/logout") { (_, response) =>
    response.removeCookie("LOGIN")
    response.redirect(s"/html/login.html")
  }

  POST("/users") { (request, response) =>
    try {
      val form = request.as[NewUserForm].validated
      val userId = UserService.createUser(form.username, form.password)
      val loginId = LoginService.createLogin(userId)
      doLogin(loginId, response)
    }
    catch {
      case NonFatal(e) =>
        val error = encodeErrorMessage(e, request.getLocale)
        response.redirect(s"/html/newuser.html?error=$error")
    }
  }

  GET("/users/me") { (request, response) =>
    val (message, link) =
      request.getAttribute[String]("username") match {
        case Some(name) =>
          s"Welcome, $name!" -> """<a href="/logout">Logout</a>"""
        case None =>
          "You aren't logged in!" -> """<a href="/html/login.html">Log in</a>"""
      }

    response.setContentType(HTML)

    s"""
      <!DOCTYPE html>
      <html lang="en">
      <head>
        <meta charset="UTF-8">
        <title>Welcome</title>
      </head>
      <body>
        <p>$message</p>
        <p>$link</p>
      </body>
      </html>
    """
  }

  private def doLogin(loginId: String, response: Response): String = {
    response.setCookie(
      name = "LOGIN",
      value = loginId,
      maxAge = 60 * 60 * 24 * 14,
      secure = true,
      httpOnly = true,
      sameSite = SameSite.STRICT
    )
    response.redirect("/users/me")
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
        messages(e.getMessage).getOrElse(s"${e.getClass}: ${e.getMessage}")
    }

    URLEncoder.encode(error, StandardCharsets.UTF_8.toString)
  }

}
