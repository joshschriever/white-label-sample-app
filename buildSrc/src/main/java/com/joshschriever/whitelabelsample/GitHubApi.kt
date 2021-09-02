package com.joshschriever.whitelabelsample

import io.ktor.client.features.auth.providers.BasicAuthCredentials
import io.ktor.client.features.auth.providers.BasicAuthProvider

internal abstract class GitHubApi : BasicApi() {

    // Not needed for this sample, but something like this would be needed to pull assets from a private repo
    final override val auth by lazy {
        System.getenv()["GITHUB_OAUTH_REPO_TOKEN"]?.let {
            BasicAuthProvider(
                credentials = { BasicAuthCredentials(username = it, password = "x-oauth-basic") },
                sendWithoutRequestCallback = { true }
            )
        }
    }
}
