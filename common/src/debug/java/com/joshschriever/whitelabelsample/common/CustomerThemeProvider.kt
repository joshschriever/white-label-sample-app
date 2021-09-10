package com.joshschriever.whitelabelsample.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

object CustomerThemeProvider {

    var customerThemeBuilder: BaseCustomerThemeBuilder = object : BaseCustomerThemeBuilder {

        @Composable override fun colors() = CustomerColors(
            backgroundColor = colorResource(R.color.backgroundColor_debug),
            primaryTextColor = colorResource(R.color.primaryTextColor_debug),
            secondaryTextColor = colorResource(R.color.secondaryTextColor_debug)
        )

        @Composable override fun strings() = CustomerStrings(
            appName = stringResource(R.string.app_name_debug)
        )

        @Composable override fun images() = CustomerImages(
            customerLogo = painterResource(R.drawable.customer_logo_debug)
        )
    }
}
