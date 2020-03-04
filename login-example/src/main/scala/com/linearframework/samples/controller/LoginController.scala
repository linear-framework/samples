package com.linearframework.samples.controller

import com.linearframework.samples.model.LoginForm
import com.linearframework.samples.service.LoginService
import com.linearframework.web._
import scala.util.control.NonFatal

/**
 * Handles login and logout requests
 */
object LoginController extends Controller {

  /**
   * Logs in a user
   */
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

  /**
   * Logs out a user
   */
  GET("/logout") { (_, response) =>
    response.removeCookie("LOGIN")
    response.redirect(s"/html/login.html")
  }

}
