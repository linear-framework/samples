package com.linearframework.samples

import com.linearframework.config._
import com.linearframework.i18n.{ResourceBundle, ResourceUtils}
import com.linearframework.samples.config.{Persistence, Resources}
import com.linearframework.sql.Database
import com.linearframework.web._

object App {
  private lazy val config = Configuration.load()

  def main(args: Array[String]): Unit = {
    Server
      .autoScan("com.linearframework.samples")
      .host(config.getString("server.host"))
      .port(config.getInt("server.port"))
      .logRequests(config.getBoolean("server.request-logging-enabled"))
      .staticFiles(Resources.htmlLocation, Resources.htmlPath)
      .register[ResourceBundle](Resources.messages)
      .register[Database](Persistence.database(config))
      .secure(ResourceUtils.getClasspathResource("keystore.jks").getPath, "password") // todo: make this work from jar
      .start()
  }

}
