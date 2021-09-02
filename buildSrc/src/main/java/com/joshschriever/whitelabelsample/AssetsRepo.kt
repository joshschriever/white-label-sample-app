package com.joshschriever.whitelabelsample

internal object AssetsRepo : GitHubApi() {
    override val baseUrl = "https://raw.githubusercontent.com/joshschriever/white-label-sample-assets/master"
}
