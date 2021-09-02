package com.joshschriever.whitelabelsample

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.property
import org.gradle.process.ExecOperations
import javax.inject.Inject

internal abstract class PopulateCustomers @Inject constructor(
    private val execOperations: ExecOperations
) : DefaultTask() {

    @get:Input
    @Option(description = "Comma-separated customer IDs to populate")
    val ids: Property<String> = project.objects.property()
    private val customerIds = ids.map { it.split(",").filter(String::isNotBlank) }

    @get:Input
    @Option(option = "no-clear", description = "Don't clear any currently populated customers before populating")
    val noClear: Property<Boolean> = project.objects.property()
    private val clear = noClear.map(Boolean::not)

    init {
        ids.convention("")
        noClear.convention(false)
    }

    @TaskAction
    private fun populateCustomers() {
    }
}
