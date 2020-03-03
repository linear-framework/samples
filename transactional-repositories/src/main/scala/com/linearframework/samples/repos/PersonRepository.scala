package com.linearframework.samples.repos

import com.linearframework.sql.{Committable, Database}

/**
 * Repository for storing/fetching data from the `persons` table
 */
class PersonRepository(override protected val database: Database) extends TransactionalRepository {

  /**
   * Creates a person record, returning the generated id
   */
  def createPerson(firstName: String, lastName: String)(implicit tx: Committable = null): Long = {
    db.sql("""
        INSERT INTO persons(first_name, last_name)
        VALUES ({firstName}, {lastName})
      """)
      .params(
        "firstName" -> firstName,
        "lastName" -> lastName
      )
      .returningKey(_.getLong(1))
      .execute()
  }

  /**
   * Finds the first and last name of the person with the given ID
   */
  def findPerson(personId: Long)(implicit tx: Committable = null): Option[(String, String)] = {
    db.sql("""
        SELECT first_name, last_name
        FROM persons
        WHERE person_id = {personId}
      """)
      .params("personId" -> personId)
      .returningRecord { rs => (rs.getString("first_name"), rs.getString("last_name")) }
      .execute()
  }

}
