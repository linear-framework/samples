package com.linearframework.samples

import com.linearframework.samples.config.StaticFiles
import com.linearframework.web._

object App {

  def main(args: Array[String]): Unit = {
    Server
      .autoScan("com.linearframework.samples")
      .staticFiles(StaticFiles.location, StaticFiles.path)
      .start()
  }

}
