pluginManagement {
    val rootProperties = java.util.Properties().apply { file("../gradle.properties").inputStream().use(::load) }
    val kotlinVersion: String by rootProperties

    plugins {
        kotlin("plugin.serialization") version kotlinVersion
    }
}
