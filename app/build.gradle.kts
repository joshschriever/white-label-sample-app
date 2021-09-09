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
    lint {
        disable("MissingDefaultResource")
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
    implementation(project(":common"))
    implementation(project(":featureone"))
    implementation(project(":featuretwo"))

    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.compose.ui:ui:1.0.2")
}
