package com.joshschriever.whitelabelsample

import kotlinx.serialization.Serializable

@Serializable
internal data class CustomerThemes(
    val light: CustomerTheme,
    val dark: CustomerTheme
)

@Serializable
internal data class CustomerTheme(
    val placeholderColorOne: String,
    val placeholderColorTwo: String
)
