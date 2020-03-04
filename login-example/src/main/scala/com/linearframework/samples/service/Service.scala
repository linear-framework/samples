package com.linearframework.samples.service

import com.linearframework.sql._
import com.linearframework.web.Component

/**
 * Base service
 */
trait Service extends Component {
  private lazy val database = the[Database]

  /**
   * Provides a database transaction.  A database connection remains open
   * while this is being executed, so it is best to limit the scope of application
   * code that runs inside a transaction
   */
  protected def transactionally[T](block: Committable => T): T = {
    database.transaction { implicit tx =>
      val result = block(tx)
      if (!tx.isClosed) {
        tx.commit()
      }
      result
    }
  }
}

