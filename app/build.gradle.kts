plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"
    defaultConfig {
        minSdk = 21
        targetSdk = 30
        applicationId = "com.joshschriever.whitelabelsample.app"
        versionCode = 1
        versionName = "1.0"
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation(project(":featureone"))
    implementation(project(":featuretwo"))

    implementation("androidx.appcompat:appcompat:1.3.0")
}
