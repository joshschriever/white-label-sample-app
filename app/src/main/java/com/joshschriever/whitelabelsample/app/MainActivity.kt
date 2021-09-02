package com.joshschriever.whitelabelsample.app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.joshschriever.whitelabelsample.common.CommonPlaceholder.FOO
import com.joshschriever.whitelabelsample.featureone.FeatureOnePlaceholder.BAR
import com.joshschriever.whitelabelsample.featuretwo.FeatureTwoPlaceholder.BAZ

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        findViewById<TextView>(R.id.text).text = "$FOO $BAR $BAZ"
    }
}
