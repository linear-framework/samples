package com.linearframework.samples

import com.linearframework.i18n.ResourceBundle
import com.linearframework.validation.ValidationException
import com.linearframework.web.{Component, Response, SameSite}
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale

package object controller extends Component {
  private lazy val messageBundle = the[ResourceBundle]

  def doLogin(loginId: String, response: Response): String = {
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

  def encodeErrorMessage(e: Throwable, locale: Locale): String = {
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
