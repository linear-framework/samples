package com.linearframework.samples.repository

import com.linearframework.sql.Transaction

/**
 * Handles persistence of user objects
 */
object UserRepository extends Repository {

  /**
   * Whether or not the given username already exists
   */
  def userExists(username: String)(implicit tx: Transaction = null): Boolean = {
    db.sql("""
        SELECT COUNT(1)
        FROM users
        WHERE username = {username}
        LIMIT 1
      """)
      .params("username" -> username)
      .returningRecord(_.getLong(1) > 0)
      .execute()
      .getOrElse(false)
  }

  /**
   * Creates a user, returning the new user's id
   */
  def createUser(username: String, password: String)(implicit tx: Transaction = null): Long = {
    db.sql("""
        INSERT INTO users (username, password)
        VALUES ({username}, {password})
      """)
      .params(
        "username" -> username,
        "password" -> password
      )
      .returningKey(_.getLong(1))
      .execute()
  }

}
