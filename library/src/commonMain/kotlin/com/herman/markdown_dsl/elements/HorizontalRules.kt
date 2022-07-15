package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.MarkdownElement

enum class HorizontalRuleStyle(internal val tag: String) {
    Hyphen("-"),
    Asterisks("*"),
    Underscore("_")
}

internal class HorizontalRule(
    private val style: HorizontalRuleStyle
) : MarkdownElement() {

    private val requiredNumberOfTags = 3

    override fun toMarkdown(): String = buildString {
        repeat(requiredNumberOfTags) {
            append(style.tag)
        }
    }
}