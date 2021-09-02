plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"
    defaultConfig {
        minSdk = 21
        targetSdk = 30
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.3.0")
}
