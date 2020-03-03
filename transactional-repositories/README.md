Transactional Repositories
==========================

Linear SQL supports running SQL transactionally or non-transactionally:
```scala
// non-transactional
database.sql("INSERT INTO users('billy')").execute()

// transactional
database.transaction { tx =>
  tx.sql("INSERT INTO users('billy')").execute()
  tx.commit()
}
```

This sample project demonstrates how to create repository classes which inherit this functionality:

```scala
// non-transactional
UserRepository.createUser("billy")

// transactional
database.transaction { implicit tx =>
  UserRepository.createUser("billy")
}
```