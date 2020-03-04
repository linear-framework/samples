package com.linearframework.samples.config

import com.linearframework.i18n.{ResourceBundle, ResourceUtils}
import com.linearframework.web.{CLASSPATH, EXTERNAL, StaticFileLocation}
import org.apache.commons.lang3.StringUtils
import java.io.File

/**
 * Enables hot-reloading of resources when running in a development environment
 */
object Resources {

  /** The location of the static web files */
  lazy val htmlLocation: StaticFileLocation = {
    if (isRunningFromJar) CLASSPATH
    else EXTERNAL
  }

  /** The path to the static web files */
  lazy val htmlPath: String = {
    if (isRunningFromJar) {
      "public/"
    }
    else {
      s"$getProjectPath/src/main/resources/public/"
    }
  }

  /** The messages resource bundle */
  lazy val messages: ResourceBundle = {
    ResourceBundle.of(
      bundleName = "messages",
      cached = isRunningFromJar,
      getFileUrl = { fileName =>
        if (isRunningFromJar) ResourceUtils.getClasspathResource(fileName)
        else new File(s"$getProjectPath/src/main/resources/$fileName").toURI.toURL
      }
    )
  }

  private def isRunningFromJar: Boolean = {
    val codePath = this.getClass.getProtectionDomain.getCodeSource.getLocation.getPath
    new File(codePath).isFile
  }

  private def getProjectPath: String = {
    val currentPath = new File(".").getAbsolutePath.replace("\\", "/").replaceAll("(/\\.)$", "")
    StringUtils.substringBefore(currentPath, "/login-example") + "/login-example"
  }
}
