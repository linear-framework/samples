package com.linearframework.samples.repository

import com.linearframework.sql.Transaction
import java.time.{LocalDateTime, ZoneOffset}

/**
 * Handles persistence of login objects
 */
object LoginRepository extends Repository {

  /**
   * Creates a login record
   */
  def createLogin(loginId: String, userId: Long)(implicit tx: Transaction = null): Unit = {
    db.sql("""
        INSERT INTO logins (login_id, user_id, last_login)
        VALUES ({loginId}, {userId}, {now})
      """)
      .params(
        "loginId" -> loginId,
        "userId" -> userId,
        "now" -> LocalDateTime.now(ZoneOffset.UTC)
      )
      .execute()
  }

  /**
   * Finds the user ID associated with a login ID
   */
  def findLoginUser(loginId: String)(implicit tx: Transaction = null): Option[Long] = {
    db.sql("""
        SELECT user_id
        FROM logins
        WHERE login_id = {loginId}
      """)
      .params("loginId" -> loginId)
      .returningRecord(_.getLong("user_id"))
      .execute()
      .map(_.toLong)
  }

}
