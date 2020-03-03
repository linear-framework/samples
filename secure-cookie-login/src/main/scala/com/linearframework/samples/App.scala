package com.linearframework.samples

import com.linearframework.config._
import com.linearframework.samples.utils.ProjectUtil
import com.linearframework.sql._
import com.linearframework.web._
import org.apache.commons.dbcp2.BasicDataSource

object App {

  /** Application configuration */
  private lazy val config = Configuration.load()

  /** Database */
  private lazy val database = {
    val ds = new BasicDataSource
    ds.setUrl(config.getString("database.url"))
    ds.setDriverClassName(config.getString("database.driver"))
    ds.setUsername(config.getString("database.username", ""))
    ds.setPassword(config.getString("database.password", ""))

    val db = Database(ds)
    db.sql("CREATE TABLE users(username VARCHAR PRIMARY KEY, password VARCHAR)").execute()
    db
  }

  /** Web Server */
  private lazy val server = {
    // enable hot-reload of static files when running in a development environment
    val (staticFileType, staticFilePath) = {
      if (ProjectUtil.isRunningFromJar) CLASSPATH -> "public/"
      else EXTERNAL -> s"${ProjectUtil.getProjectDirectory.get}/src/main/resources/public/"
    }

    Server
      .autoScan("com.linearframework.samples")
      .port(config.getInt("server.port"))
      .host(config.getString("server.host"))
      .logRequests(config.getBoolean("server.request-logging-enabled"))
      .staticFiles(staticFileType, staticFilePath)
      .register[Configuration](config)
      .register[Database](database)
  }

  def main(args: Array[String]): Unit = {
    server.start()
  }

}