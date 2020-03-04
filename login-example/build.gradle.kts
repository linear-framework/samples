plugins {
  scala
  application
}

application {
  mainClassName = "com.linearframework.samples.App"
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
  jcenter()
  mavenCentral()
  maven {
    url = uri("https://maven.pkg.github.com/linear-framework/linear-config")
    credentials {
      username = System.getenv("GITHUB_USER")
      password = System.getenv("GITHUB_TOKEN")
    }
  }
  maven {
    url = uri("https://maven.pkg.github.com/linear-framework/linear-i18n")
    credentials {
      username = System.getenv("GITHUB_USER")
      password = System.getenv("GITHUB_TOKEN")
    }
  }
  maven {
    url = uri("https://maven.pkg.github.com/linear-framework/linear-sql")
    credentials {
      username = System.getenv("GITHUB_USER")
      password = System.getenv("GITHUB_TOKEN")
    }
  }
  maven {
    url = uri("https://maven.pkg.github.com/linear-framework/linear-validation")
    credentials {
      username = System.getenv("GITHUB_USER")
      password = System.getenv("GITHUB_TOKEN")
    }
  }
  maven {
    url = uri("https://maven.pkg.github.com/linear-framework/linear-web")
    credentials {
      username = System.getenv("GITHUB_USER")
      password = System.getenv("GITHUB_TOKEN")
    }
  }
  maven {
    url = uri("https://maven.pkg.github.com/linear-framework/linear-macros")
    credentials {
      username = System.getenv("GITHUB_USER")
      password = System.getenv("GITHUB_TOKEN")
    }
  }
}

dependencies {
  implementation("org.scala-lang:scala-library:2.13.1")

  implementation("com.linearframework:config:" + version)
  implementation("com.linearframework:i18n:" + version)
  implementation("com.linearframework:sql:" + version)
  implementation("com.linearframework:validation:" + version)
  implementation("com.linearframework:web:" + version)

  implementation("org.apache.commons:commons-dbcp2:2.7.0")
  implementation("org.apache.commons:commons-lang3:3.9")
  implementation("org.mindrot:jbcrypt:0.4")
  implementation("org.flywaydb:flyway-core:6.2.4")

  runtimeOnly("com.h2database:h2:1.4.200")
  runtimeOnly("ch.qos.logback:logback-classic:1.2.3")
}
