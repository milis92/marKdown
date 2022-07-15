package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.MarkdownBuilder
import com.herman.markdown_dsl.MarkdownElement

class BlockQuote(
    private val text: String
) : MarkdownElement() {

    override fun toMarkdown(): String = buildString {
        appendLine()
        text.lineSequence()
            .forEach {
                append("> ")
                append(it)
                appendLine()
            }
        appendLine()
    }
}

fun MarkdownBuilder.blockQuote(initialiser: MarkdownBuilder.() -> Unit) {
    val blockQuoteBuilder = MarkdownBuilder().apply(initialiser).build()
    blockQuote(blockQuoteBuilder.content)
}