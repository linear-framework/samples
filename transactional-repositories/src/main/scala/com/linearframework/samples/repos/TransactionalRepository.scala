package com.linearframework.samples.repos

import com.linearframework.sql._

/**
 * A repository contains the methods required to fetch/store data in a database table.
 */
trait TransactionalRepository {

  /**
   * The database over which this repository runs.
   */
  protected val database: Database

  /**
   * Runs SQL as part of larger transaction, if appropriate.
   * All database access should be proctored through this method.
   *
   * By accessing the database through this method, instead of directly using the database object,
   * multiple repository methods can be chained together in the same database transaction.
   */
  protected final def db(implicit tx: Committable): SqlRunner = {
    if (tx == null) {
      database
    }
    else {
      tx.asInstanceOf[Transaction]
    }
  }

}
