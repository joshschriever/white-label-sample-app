package com.joshschriever.whitelabelsample.featuretwo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.joshschriever.whitelabelsample.common.CustomerTheme

@Composable
fun CustomerInfoScreen() {
    CustomerTheme {
        CustomerInfoContent()
    }
}

@Composable
internal fun CustomerInfoContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            CustomerTheme.images.customerLogo,
            contentDescription = null
        )
        Text(
            CustomerTheme.strings.appName,
            fontSize = 32.sp,
            color = CustomerTheme.colors.primaryTextColor
        )
    }
}
