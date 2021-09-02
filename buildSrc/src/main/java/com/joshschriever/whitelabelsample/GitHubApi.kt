package com.joshschriever.whitelabelsample

import io.ktor.client.features.auth.providers.BasicAuthCredentials
import io.ktor.client.features.auth.providers.BasicAuthProvider
import org.gradle.api.GradleException

internal abstract class GitHubApi : BasicApi() {
    final override val auth by lazy {
        if (System.getenv().containsKey("GITHUB_OAUTH_REPO_TOKEN")) {
            BasicAuthProvider(
                credentials = {
                    BasicAuthCredentials(
                        username = System.getenv("GITHUB_OAUTH_REPO_TOKEN"),
                        password = "x-oauth-basic"
                    )
                },
                sendWithoutRequestCallback = { true }
            )
        } else {
            throw GradleException("You must set the GITHUB_OAUTH_REPO_TOKEN environment variable")
        }
    }
}
