package com.joshschriever.whitelabelsample.app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import com.joshschriever.whitelabelsample.common.CustomerThemeProvider
import com.joshschriever.whitelabelsample.featuretwo.CustomerInfoScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CustomerThemeProvider.customerThemeBuilder = CustomerThemeBuilder

        setContentView(R.layout.activity_main)

        findViewById<ComposeView>(R.id.compose_view).setContent {
            CustomerInfoScreen()
        }

        findViewById<TextView>(R.id.customer_id).text = CustomerId.id
    }
}
