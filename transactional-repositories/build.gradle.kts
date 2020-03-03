plugins {
  scala
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
  jcenter()
  mavenCentral()
  maven {
    url = uri("https://maven.pkg.github.com/linear-framework/linear-sql")
    credentials {
      username = System.getenv("GITHUB_USER")
      password = System.getenv("GITHUB_TOKEN")
    }
  }
}

dependencies {
  implementation("org.scala-lang:scala-library:2.13.1")
  implementation("com.linearframework:sql:" + version)
  implementation("org.apache.commons:commons-dbcp2:2.7.0")
  runtimeOnly("com.h2database:h2:1.4.200")
}
