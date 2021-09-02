import java.util.Properties

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    kotlin("plugin.serialization")
}

val rootProperties = Properties().apply { file("../gradle.properties").inputStream().use(::load) }
val kotlinVersion: String by rootProperties
val androidGradlePluginVersion: String by rootProperties

val ktorVersion = "1.6.2"
val serializationVersion = "1.2.2"

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation(kotlin("gradle-plugin", kotlinVersion))
    implementation(kotlin("serialization", kotlinVersion))
    implementation("com.android.tools.build:gradle:$androidGradlePluginVersion")

    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:$serializationVersion")
}

gradlePlugin {
    plugins {
        register("whiteLabelCustomerFlavorsPlugin") {
            id = "white-label-customer-flavors"
            implementationClass = "com.joshschriever.whitelabelsample.WhiteLabelCustomerFlavorsPlugin"
        }
    }
}
