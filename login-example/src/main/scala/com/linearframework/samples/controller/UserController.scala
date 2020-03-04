package com.linearframework.samples.controller

import com.linearframework.samples.model.NewUserForm
import com.linearframework.samples.service.{LoginService, UserService}
import com.linearframework.web._
import scala.util.control.NonFatal

/**
 * Handles user-related requests
 */
object UserController extends Controller {

  /**
   * Creates a new user
   */
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

  /**
   * Displays the user details
   */
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

}
