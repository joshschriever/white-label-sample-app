package com.joshschriever.whitelabelsample.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Stable
class CustomerColors(
    val backgroundColor: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color
)

@Stable
class CustomerStrings(
    val appName: String
)

@Stable
class CustomerImages(
    val customerLogo: Painter
)

object CustomerTheme {
    val colors: CustomerColors
        @Composable get() = LocalCustomerColors.current

    val strings: CustomerStrings
        @Composable get() = LocalCustomerStrings.current

    val images: CustomerImages
        @Composable get() = LocalCustomerImages.current
}

@Composable
fun CustomerTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalCustomerColors provides CustomerThemeProvider.customerThemeBuilder.colors(),
        LocalCustomerStrings provides CustomerThemeProvider.customerThemeBuilder.strings(),
        LocalCustomerImages provides CustomerThemeProvider.customerThemeBuilder.images(),
        content = content
    )
}

interface BaseCustomerThemeBuilder {
    @Composable fun colors(): CustomerColors
    @Composable fun strings(): CustomerStrings
    @Composable fun images(): CustomerImages
}

internal val LocalCustomerColors = staticCompositionLocalOf<CustomerColors> {
    error("No CustomerColors container provided")
}

internal val LocalCustomerStrings = staticCompositionLocalOf<CustomerStrings> {
    error("No CustomerStrings container provided")
}

internal val LocalCustomerImages = staticCompositionLocalOf<CustomerImages> {
    error("No CustomerImages container provided")
}
