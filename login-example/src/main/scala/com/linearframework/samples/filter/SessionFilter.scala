package com.linearframework.samples.filter

import com.linearframework.samples.repository.{LoginRepository, UserRepository}
import com.linearframework.web._

object SessionFilter extends Filter {

  BEFORE { (request, _) =>
    request.getCookie("LOGIN") match {
      case Some(loginId) =>
        LoginRepository.findLoginUser(loginId) match {
          case Some(userId) =>
            UserRepository.findUsername(userId) match {
              case Some(username) =>
                request.setAttribute("username", username)
              case None =>
                request.removeAttribute("username")
            }
          case None =>
            request.removeAttribute("username")
        }
      case None =>
        request.removeAttribute("username")
    }
  }

}
