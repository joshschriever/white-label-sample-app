package com.joshschriever.whitelabelsample

internal class XmlElement(
    private val name: String,
    private val indentation: Int = 0,
    private val text: String? = null,
    private val attributes: MutableMap<String, String> = mutableMapOf(),
    private val children: MutableList<XmlElement> = mutableListOf(),
    private val escapeTextValue: Boolean = true,
    private val escapeAttributeValues: Boolean = true
) {

    fun attribute(name: String, value: String) {
        attributes[name] = value
    }

    fun element(name: String, escapeAttributeValues: Boolean = true, block: XmlElement.() -> Unit = {}) {
        children.add(XmlElement(name, indentation + 1, escapeAttributeValues = escapeAttributeValues).apply(block))
    }

    fun element(name: String, text: String, escapeTextValue: Boolean = true, escapeAttributeValues: Boolean = true, block: XmlElement.() -> Unit = {}) {
        children.add(XmlElement(name, indentation + 1, text, escapeTextValue = escapeTextValue, escapeAttributeValues = escapeAttributeValues).apply(block))
    }

    override fun toString(): String = StringBuilder().run {
        append(TAB.repeat(indentation))
        append("<$name")
        attributes.forEach { append(" ${it.key}=\"${it.value.let { value -> if (escapeAttributeValues) value.escape() else value }}\"") }
        if (text == null && children.isEmpty()) {
            append("/>")
        } else {
            if (text != null) {
                append(">")
                append(text.let { if (escapeTextValue) it.escape() else it })
            } else {
                appendLine(">")
                children.forEach { appendLine(it.toString()) }
                append(TAB.repeat(indentation))
            }
            append("</$name>")
        }
        toString()
    }

    companion object {
        private const val TAB = "  "

        private fun String.escape() = this
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("&", "&amp;")
            .replace("'", "\\'")
            .replace("\"", "\\\"")
    }
}

internal fun xmlDocument(
    rootElementName: String,
    rootElementText: String?,
    block: XmlElement.() -> Unit = {}
): String = StringBuilder().run {
    appendLine("<?xml version='1.0' encoding='UTF-8'?>")
    appendLine(XmlElement(name = rootElementName, text = rootElementText).apply(block).toString())
    toString()
}

internal fun xmlDocument(rootElementName: String, block: XmlElement.() -> Unit = {}) =
    xmlDocument(rootElementName, null, block)
