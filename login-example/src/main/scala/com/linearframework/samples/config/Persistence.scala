package com.linearframework.samples.config

import com.linearframework.config._
import com.linearframework.sql._
import org.apache.commons.dbcp2.BasicDataSource
import org.flywaydb.core.Flyway
import java.nio.charset.StandardCharsets

/**
 * Configures persistence
 */
object Persistence {

  /**
   * Gets a fully-configured Database object
   */
  def database(config: Configuration): Database = {
    val dataSource = new BasicDataSource
    dataSource.setUrl(config.getString("database.url"))
    dataSource.setDriverClassName(config.getString("database.driver"))
    dataSource.setUsername(config.getString("database.username", ""))
    dataSource.setUsername(config.getString("database.password", ""))

    if (config.getBoolean("database.auto-migrate")) {
      Flyway.configure()
        .encoding(StandardCharsets.UTF_8)
        .dataSource(dataSource)
        .load()
        .migrate()
    }

    Database(dataSource)
  }

}
