plugins {
    id("com.android.application")
    kotlin("android")
    id("white-label-customer-flavors")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"
    defaultConfig {
        minSdk = 21
        targetSdk = 30
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
