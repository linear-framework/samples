package com.linearframework.samples.service

import com.linearframework.samples.repository.{LoginRepository, UserRepository}
import com.linearframework.web.UnauthorizedException
import org.mindrot.jbcrypt.BCrypt
import java.util.UUID

object LoginService extends Service {

  /**
   * Logs in the user with the given credentials, returning the generated
   * login id
   */
  def login(username: String, password: String): String = {
    val cleanUsername = username.toLowerCase.replaceAll("\\s+", "")
    UserRepository.findPassword(cleanUsername) match {
      case Some((userId, encryptedPassword)) if BCrypt.checkpw(password, encryptedPassword) =>
        createLogin(userId)
      case _ =>
        throw new UnauthorizedException("login.failed")
    }
  }

  /**
   * Creates a login record for the given user,
   * returning the generated login id
   */
  def createLogin(userId: Long): String = {
    val id = UUID.randomUUID().toString.replaceAll("-", "")
    LoginRepository.createLogin(id, userId)
    id
  }

}
