package com.joshschriever.whitelabelsample

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
internal data class CustomerThemes(
    val light: JsonObject,
    val dark: JsonObject
) {
    companion object {
        val themeColorNames = listOf(
            "backgroundColor",
            "primaryTextColor",
            "secondaryTextColor"
        )
    }
}
