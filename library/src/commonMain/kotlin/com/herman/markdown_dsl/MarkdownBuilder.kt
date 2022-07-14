package com.herman.markdown_dsl

import com.herman.markdown_dsl.elements.Bold
import com.herman.markdown_dsl.elements.Emphasis
import com.herman.markdown_dsl.elements.Italic
import com.herman.markdown_dsl.elements.LineBreak
import com.herman.markdown_dsl.elements.Paragraph
import com.herman.markdown_dsl.elements.Text

data class Markdown(val content: String) {
    override fun toString(): String = content
}

open class MarkdownBuilder {

    private val elementsContainer: MutableList<MarkdownElement> = mutableListOf()

    fun text(content: String) {
        elementsContainer.add(Text(content))
    }

    fun bold(content: String, emphasis: Emphasis = Emphasis.Asterisks){
        elementsContainer.add(Bold(content, emphasis))
    }

    fun italic(content: String, emphasis: Emphasis = Emphasis.Underscore){
        elementsContainer.add(Italic(content, emphasis))
    }

    fun paragraph(
        content: String,
        lineBreak: LineBreak = LineBreak.Space,
    ) {
        elementsContainer.add(Paragraph(content, lineBreak))
    }

    internal fun build(): Markdown {
        val content = buildString {
            elementsContainer.stream()
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



