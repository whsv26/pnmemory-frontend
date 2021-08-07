val kotlinVersion: String by rootProject.extra
val hiltVersion: String by rootProject.extra

plugins {
  id("com.android.application")
  id("dagger.hilt.android.plugin") // DI support
  id("kotlin-android")
  id("com.google.gms.google-services")
  id("kotlin-kapt") // annotation processor support
}

android {
  compileSdkVersion(30)
  buildToolsVersion("30.0.2")

  defaultConfig {
    applicationId = "org.whsv26.pnmemory"
    minSdkVersion(30)
    targetSdkVersion(30)
    versionCode = 1
    versionName = "1.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }

  buildFeatures {
    viewBinding = true
    // dataBinding = true
  }
}

dependencies {
  testImplementation("junit:junit:4.+")
  androidTestImplementation("androidx.test.ext:junit:1.1.2")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
  androidTestImplementation("com.google.dagger:hilt-android-testing:2.38.1")
  kaptAndroidTest("com.google.dagger:hilt-compiler:2.38.1")
  testImplementation("com.google.dagger:hilt-android-testing:2.38.1")
  kaptTest("com.google.dagger:hilt-compiler:2.38.1")

  implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
  implementation("androidx.core:core-ktx:1.3.1")
  implementation("androidx.appcompat:appcompat:1.2.0")
  implementation("com.google.android.material:material:1.2.1")
  implementation("androidx.constraintlayout:constraintlayout:2.0.1")
  implementation("androidx.annotation:annotation:1.2.0")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
  implementation(platform("com.google.firebase:firebase-bom:28.3.0"))
  implementation("com.google.firebase:firebase-messaging-ktx:22.0.0")
  implementation("com.google.firebase:firebase-analytics-ktx:19.0.0")
  implementation("com.github.kittinunf.fuel:fuel:2.3.1")
  implementation("com.github.kittinunf.fuel:fuel-android:2.3.1")
  implementation("com.github.kittinunf.fuel:fuel-gson:2.3.1")
  implementation("io.arrow-kt:arrow-core:0.13.2")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")

  implementation("com.google.dagger:hilt-android:$hiltVersion")
  kapt("com.google.dagger:hilt-compiler:$hiltVersion")
  implementation("androidx.fragment:fragment-ktx:1.3.6")

  implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
//  kapt("androidx.hilt:hilt-compiler:1.0.0")
}

kapt {
  correctErrorTypes = true
}