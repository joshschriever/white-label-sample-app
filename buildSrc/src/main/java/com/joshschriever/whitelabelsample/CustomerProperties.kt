package com.joshschriever.whitelabelsample

import kotlinx.serialization.Serializable

@Serializable
data class CustomerProperties(
    val customerId: String,
    val flavorName: String,
    val appName: String,
    val packageName: String,
    val signingKeyCredentials: SigningKeyCredentials
)

@Serializable
data class SigningKeyCredentials(
    val storePassword: String,
    val keyAlias: String,
    val keyPassword: String
)
