package com.linearframework.samples.config

import com.linearframework.web._
import org.apache.commons.lang3.StringUtils
import java.io.File

/**
 * Enable hot-reload of static files when running in a development environment
 */
object StaticFiles {

  lazy val location: StaticFileLocation = {
    if (isRunningFromJar) CLASSPATH
    else EXTERNAL
  }

  lazy val path: String = {
    if (isRunningFromJar) {
      "public/"
    }
    else {
      val currentPath = new File(".").getAbsolutePath.replace("\\", "/").replaceAll("(/\\.)$", "")
      val basePath = StringUtils.substringBefore(currentPath, "/login-example") + "/login-example"
      s"$basePath/src/main/resources/public/"
    }
  }

  private def isRunningFromJar: Boolean = {
    val codePath = this.getClass.getProtectionDomain.getCodeSource.getLocation.getPath
    new File(codePath).isFile
  }

}
