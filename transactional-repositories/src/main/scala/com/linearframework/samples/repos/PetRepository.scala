package com.linearframework.samples.repos

import com.linearframework.sql._

/**
 * Repository for storing/fetching data from the `pets` table
 */
class PetRepository(override protected val database: Database) extends TransactionalRepository {

  /**
   * Creates a pet record for the given person, returning the generated record id
   */
  def createPet(petName: String, breed: String, ownerPersonId: Long)(implicit tx: Committable = null): Long = {
    db.sql("""
        INSERT INTO pets(name, breed, owner_person_id)
        VALUES ({name}, {breed}, {owner})
      """)
      .params(
        "name" -> petName,
        "breed" -> breed,
        "owner" -> ownerPersonId
      )
      .returningKey(_.getLong(1))
      .execute()
  }

  /**
   * Finds the name and breed of all pets owned by the given person
   */
  def findPets(ownerPersonId: Long)(implicit tx: Committable = null): List[(String, String)] = {
    db.sql("""
        SELECT name, breed
        FROM pets
        WHERE owner_person_id = {owner}
      """)
      .params("owner" -> ownerPersonId)
      .returningRecords { rs => (rs.getString("name"), rs.getString("breed")) }
      .execute()
      .toList
  }

}
