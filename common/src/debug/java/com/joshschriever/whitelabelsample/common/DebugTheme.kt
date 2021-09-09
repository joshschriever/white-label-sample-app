package com.joshschriever.whitelabelsample.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun DebugTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalCustomerColors provides CustomerColors(
            backgroundColor = colorResource(R.color.backgroundColor_debug),
            primaryTextColor = colorResource(R.color.primaryTextColor_debug),
            secondaryTextColor = colorResource(R.color.secondaryTextColor_debug)
        ),
        LocalCustomerStrings provides CustomerStrings(
            appName = stringResource(R.string.app_name_debug)
        ),
        LocalCustomerImages provides CustomerImages(
            customerLogo = painterResource(R.drawable.customer_logo_debug)
        ),
        content = content
    )
}
