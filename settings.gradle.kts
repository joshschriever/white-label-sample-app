include(
    "app",
    "common",
    "featureone",
    "featuretwo"
)

pluginManagement {
    val androidGradlePluginVersion: String by settings
    val kotlinVersion: String by settings

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    plugins {
        kotlin("android") version kotlinVersion
        id("com.android.application") version androidGradlePluginVersion
        id("com.android.library") version androidGradlePluginVersion
    }
}
