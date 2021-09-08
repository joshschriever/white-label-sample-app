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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.2"
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("androidx.compose.foundation:foundation:1.0.2")
    implementation("androidx.compose.ui:ui:1.0.2")
}
