// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  val kotlinVersion by extra("1.5.21")
  val hiltVersion by extra("2.38.1")

  repositories {
    google()
    mavenCentral()
  }
  dependencies {
    classpath("com.android.tools.build:gradle:4.2.0")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files

    classpath("com.google.gms:google-services:4.3.8")
    classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
    // jcenter() // Warning: this repository is going to shut down soon
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}