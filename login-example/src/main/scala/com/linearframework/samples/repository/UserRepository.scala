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

  /**
   * Gets username for the given id
   */
  def findUsername(userId: Long)(implicit tx: Transaction = null): Option[String] = {
    db.sql("""
        SELECT username
        FROM users
        WHERE user_id = {userId}
      """)
      .params("userId" -> userId)
      .returningRecord(_.getString("username"))
      .execute()
  }

  /**
   * Gets password and userId for the given username
   */
  def findPassword(username: String)(implicit tx: Transaction = null): Option[(Long, String)] = {
    db.sql("""
        SELECT user_id, password
        FROM users
        WHERE username = {username}
      """)
      .params("username" -> username)
      .returningRecord { rs =>
        rs.getLongOption("user_id").getOrElse(-1L) -> rs.getString("password")
      }
      .execute()
  }

}
