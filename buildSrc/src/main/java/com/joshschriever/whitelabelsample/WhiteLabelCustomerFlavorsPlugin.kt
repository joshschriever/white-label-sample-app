package com.joshschriever.whitelabelsample

import com.android.build.api.dsl.ApplicationExtension
import kotlinx.serialization.builtins.SetSerializer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.register

class WhiteLabelCustomerFlavorsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register<PopulateCustomers>("populateCustomers")

        val customersFile = project.file(POPULATED_CUSTOMERS_FILE_NAME)
        customersFile.takeUnless { it.exists() }?.writeText("[]")
        val customers = Json
            .nonstrict
            .decodeFromString(SetSerializer(CustomerProperties.serializer()), customersFile.readText())

        project.extra[POPULATED_CUSTOMERS_PROPERTY_NAME] = customers

        project.configure(setOf(project.extensions.getByName<ApplicationExtension>("android"))) {
            flavorDimensions.add(CUSTOMER_DIMENSION)

            if (customers.isEmpty()) {
                productFlavors.register("nocustomerpopulated") { dimension = CUSTOMER_DIMENSION }
            }

            customers.forEach { customer ->
                val signingConfigProvider = signingConfigs.register(customer.flavorName) {
                    storeFile = project.file("src/${customer.flavorName}/keystore/release.keystore")
                    storePassword = customer.signingKeyCredentials.storePassword
                    keyAlias = customer.signingKeyCredentials.keyAlias
                    keyPassword = customer.signingKeyCredentials.keyPassword
                }

                productFlavors.register(customer.flavorName) {
                    dimension = CUSTOMER_DIMENSION
                    applicationId = customer.packageName
                    signingConfig = signingConfigProvider.get()
                }
            }
        }
    }
}

private const val CUSTOMER_DIMENSION = "customer"

internal const val POPULATED_CUSTOMERS_FILE_NAME = "populated-customers.json"

private const val POPULATED_CUSTOMERS_PROPERTY_NAME = "populated-customers"

// Not used in this sample, but this is a function that could be used to access customer properties in module buildscripts
fun Project.getPopulatedCustomers() = extra[POPULATED_CUSTOMERS_PROPERTY_NAME] as Set<CustomerProperties>
