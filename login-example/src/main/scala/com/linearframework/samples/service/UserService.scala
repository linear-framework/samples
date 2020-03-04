package com.linearframework.samples.service

import com.linearframework.samples.repository.UserRepository
import com.linearframework.web.ConflictException
import org.mindrot.jbcrypt.BCrypt

/**
 * API for user operations
 */
object UserService extends Service {

  /**
   * Creates a user, returning the generated user id
   */
  def createUser(username: String, password: String): Long = {
    val cleanUsername = username.toLowerCase.replaceAll("\\s+", "")
    val encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt())

    transactionally { implicit tx =>
      if (UserRepository.userExists(cleanUsername)) {
        throw new ConflictException("newuser.username.exists")
      }
      UserRepository.createUser(cleanUsername, encryptedPassword)
    }
  }

}
