package com.linearframework.samples

import com.linearframework.samples.repos.{PersonRepository, PetRepository}
import com.linearframework.sql.Database
import org.apache.commons.dbcp2.BasicDataSource

object App {
  private val DB_URL = "jdbc:h2:mem:linear_transaction_samples;DB_CLOSE_DELAY=-1"
  private val DB_DRIVER = "org.h2.Driver"
  private val DB_USERNAME = ""
  private val DB_PASSWORD = ""

  private lazy val database: Database = {
    val ds = new BasicDataSource
    ds.setUrl(DB_URL)
    ds.setDriverClassName(DB_DRIVER)
    ds.setUsername(DB_USERNAME)
    ds.setPassword(DB_PASSWORD)

    val db = Database(ds)

    db.sql("CREATE TABLE persons(person_id IDENTITY, first_name VARCHAR, last_name VARCHAR)").execute()
    db.sql("CREATE TABLE pets(pet_id IDENTITY, name VARCHAR, breed VARCHAR, owner_person_id BIGINT)").execute()

    db
  }

  private lazy val personRepository = new PersonRepository(database)
  private lazy val petRepository = new PetRepository(database)

  def main(args: Array[String]): Unit = {
    var person: Option[(String, String)] = None
    var pets: List[(String, String)] = List()
    var personId: Long = -1
    var petId1: Long = -1
    var petId2: Long = -1

    println()
    println("Running TRANSACTIONAL Repository methods...")
    println()

    database.transaction { implicit tx =>
      println("   Creating person Bill Gates")
      personId = personRepository.createPerson("Bill", "Gates")
      println("   Bill Gates created with ID: " + personId)

      println("   Creating pet Rex")
      petId1 = petRepository.createPet("Rex", "Dinosaur", personId)
      println("   Rex created with ID: " + petId1)

      println("   Creating pet Rover")
      petId2 = petRepository.createPet("Rover", "Dog", personId)
      println("   Rover created with ID: " + petId2)

      println("   Rolling back transaction")
      tx.rollback()
    }

    println("   Finding Bill Gates...")
    person = personRepository.findPerson(personId)
    println("      " + person)

    println("   Finding Bill's Pets...")
    pets = petRepository.findPets(personId)
    println("      " + pets)

    println()
    println("Running NON-TRANSACTIONAL Repository methods...")
    println()

    println("   Creating person Melinda Gates")
    personId = personRepository.createPerson("Melinda", "Gates")
    println("   Melinda Gates created with ID: " + personId)

    println("   Creating pet Herman")
    petId1 = petRepository.createPet("Herman", "Turtle", personId)
    println("   Herman created with ID: " + petId1)

    println("   Creating pet Leo")
    petId2 = petRepository.createPet("Leo", "Dog", personId)
    println("   Leo created with ID: " + petId2)

    println("   Finding Melinda Gates...")
    person = personRepository.findPerson(personId)
    println("      " + person)

    println("   Finding Melinda's Pets...")
    pets = petRepository.findPets(personId)
    println("      " + pets)

    println()
  }

}