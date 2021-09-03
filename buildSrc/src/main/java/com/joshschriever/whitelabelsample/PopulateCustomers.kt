package com.joshschriever.whitelabelsample

import kotlinx.serialization.builtins.SetSerializer
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.property
import java.io.File

internal abstract class PopulateCustomers : DefaultTask() {

    @get:Input
    @Option(description = "Comma-separated customer IDs to populate")
    val ids: Property<String> = project.objects.property()
    private val customerIds = ids.map { it.split(",").filter(String::isNotBlank) }

    @get:Input
    @Option(option = "no-clear", description = "Don't clear any currently populated customers before populating")
    val noClear: Property<Boolean> = project.objects.property()
    private val clearPopulatedCustomers = noClear.map(Boolean::not)

    init {
        ids.convention("")
        noClear.convention(false)
    }

    @TaskAction
    private fun populateCustomers() {
        val customersFile = project.file(POPULATED_CUSTOMERS_FILE_NAME)
        customersFile.takeUnless { it.exists() }?.writeText("[]")

        val srcDir = project.file("src")

        if (clearPopulatedCustomers.get()) {
            clearPopulatedCustomers(customersFile = customersFile, srcDir = srcDir)
        }

        customerIds.get().forEach {
            populateCustomer(
                customersFile = customersFile,
                srcDir = srcDir,
                customerId = it
            )
        }
    }

    private fun clearPopulatedCustomers(customersFile: File, srcDir: File) {
        customersFile.writeText("[]")
        srcDir
            .walkTopDown()
            .filter { it.name == TRANSIENT_FLAVOR_DIR_MARKER_NAME }
            .forEach { it.parentFile.deleteRecursively() }
    }

    private fun populateCustomer(
        customersFile: File,
        srcDir: File,
        customerId: String
    ) {
        val customerProperties = AssetsRepo.get<CustomerProperties>("customers/$customerId/properties.json", logger)

        val customers = Json
            .nonstrict
            .decodeFromString(SetSerializer(CustomerProperties.serializer()), customersFile.readText())
            .filterNot { it.customerId == customerId }
            .toSet() + customerProperties

        customersFile.writeText(Json.pretty.encodeToString(SetSerializer(CustomerProperties.serializer()), customers))

        val flavorDir = File(srcDir, customerProperties.flavorName).also { it.mkdir() }
        File(flavorDir, ".gitignore").writeText("*\n")
        File(flavorDir, TRANSIENT_FLAVOR_DIR_MARKER_NAME).writeText("\n")

        File(flavorDir, "keystore/release.keystore")
            .also { it.parentFile.mkdir() }
            .writeBytes(AssetsRepo.get("customers/$customerId/android/release.keystore", logger))

        File(flavorDir, "java/com/joshschriever/whitelabelsample/app/CustomerId.kt")
            .also { it.parentFile.mkdirs() }
            .writeText(
                """
                    package com.joshschriever.whitelabelsample.app

                    object CustomerId {
                        const val id = "${customerProperties.customerId}"
                    }

                """.trimIndent()
            )

        File(flavorDir, "res/values/customer.xml")
            .also { it.parentFile.mkdirs() }
            .writeText(
                xmlDocument("resources") {
                    element("string", customerProperties.appName) {
                        attribute("name", "app_name_${customerProperties.flavorName}")
                    }
                }
            )

        File(flavorDir, "AndroidManifest.xml").writeText(
            xmlDocument("manifest") {
                attribute("xmlns:android", "http://schemas.android.com/apk/res/android")
                element("application") {
                    attribute("android:label", "@string/app_name_${customerProperties.flavorName}")
                }
            }
        )

        drawableDensities.forEach { density ->
            val densityDir = File(flavorDir, "res/$density").apply { mkdir() }
            setOf(
                "ic_launcher.png",
                "ic_launcher_foreground.png",
                "ic_launcher_background.png"
            ).forEach {
                File(densityDir, it)
                    .writeBytes(AssetsRepo.get("customers/$customerId/android/$density/$it", logger))
            }
        }

        setOf(
            Triple("customer_logo_portrait.png", "-port", "customer_logo_${customerProperties.flavorName}.png"),
            Triple("customer_logo_landscape.png", "-land", "customer_logo_${customerProperties.flavorName}.png")
        ).forEach { (remoteFileName, orientation, localFileName) ->
            File(flavorDir, "res/drawable$orientation-nodpi/$localFileName")
                .also { it.parentFile.mkdir() }
                .writeBytes(AssetsRepo.get("customers/$customerId/themes/light/$remoteFileName", logger))

            AssetsRepo.getIfExists<ByteArray>("customers/$customerId/themes/dark/$remoteFileName", logger)?.let { bytes ->
                File(flavorDir, "res/drawable$orientation-night-nodpi/$localFileName")
                    .also { it.parentFile.mkdir() }
                    .writeBytes(bytes)
            }
        }
    }

    companion object {
        private const val TRANSIENT_FLAVOR_DIR_MARKER_NAME = ".transient-customer-flavor-dir-marker"

        private val drawableDensities = listOf(
            "drawable-mdpi",
            "drawable-hdpi",
            "drawable-xhdpi",
            "drawable-xxhdpi",
            "drawable-xxxhdpi"
        )
    }
}
