package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.MarkdownElement

class CodeBlock(
    private val content: String,
) : MarkdownElement() {

    private val indent = "    "

    override fun toMarkdown(): String = buildString {
        content.trim().lines().forEach { content ->
            val indentedContent = buildString {
                append(content.prependIndent(indent))
            }
            appendLine(indentedContent.removeSuffix(indent))
        }
    }
}