package com.linearframework.samples.repository

import com.linearframework.sql.{Committable, Database, SqlRunner, Transaction}
import com.linearframework.web.Component

/**
 * Base repository
 */
trait Repository extends Component {
  private lazy val database = the[Database]

  /**
   * Provides transaction management
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

