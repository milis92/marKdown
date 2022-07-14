package com.herman.markdown_dsl

import com.herman.markdown_dsl.elements.LineBreak
import com.herman.markdown_dsl.elements.Paragraph
import com.herman.markdown_dsl.elements.Text

data class Markdown(val content: String) {
    override fun toString(): String = content
}

open class MarkdownBuilder {

    private val elements: MutableList<MarkdownElement> = mutableListOf()

    fun text(content: String) {
        elements.add(Text(content))
    }

    fun paragraph(
        content: String,
        lineBreak: LineBreak = LineBreak.Space,
    ) {
        elements.add(Paragraph(content, lineBreak))
    }

    internal fun build(): Markdown {
        val content = buildString {
            elements.stream()
                .map { it.toMarkdown() }
                .forEach { element ->
                    append(element)
                    appendLine()
                }
        }.removeSuffix("\n")
        return Markdown(content)
    }
}

fun markdown(initialiser: @MarkdownElementMarker MarkdownBuilder.() -> Unit): Markdown {
    return MarkdownBuilder().apply(initialiser).build()
}



