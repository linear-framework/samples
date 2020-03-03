package com.linearframework.samples.utils

import java.io.File
import org.apache.commons.lang3.StringUtils

/**
 * Contains utilities for reflecting on the runtime values of classes, types, and objects
 */
object ProjectUtil {

  /**
   * Determines if the current code is running in a JAR or from classes on a file system
   */
  def isRunningFromJar: Boolean = {
    val codePath = this.getClass.getProtectionDomain.getCodeSource.getLocation.getPath
    new File(codePath).isFile
  }

  /**
   * If running from files on the local filesystem, gets the base project directory.
   * If running from a JAR, returns None.
   */
  def getProjectDirectory: Option[String] = {
    if (isRunningFromJar) {
      None
    }
    else {
      val currentPath = new File(".").getAbsolutePath.replace("\\", "/").replaceAll("(/\\.)$", "")
      val basePath = StringUtils.substringBefore(currentPath, "/secure-cookie-login") + "/secure-cookie-login"
      Some(basePath)
    }
  }

}
