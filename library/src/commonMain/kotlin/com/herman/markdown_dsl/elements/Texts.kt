package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.Markdown
import com.herman.markdown_dsl.MarkdownBuilder
import com.herman.markdown_dsl.MarkdownElement
import com.herman.markdown_dsl.MarkdownElementMarker

open class Text(
    private val content: String
) : MarkdownElement() {
    override fun toMarkdown(): String = buildString {
        append(content)
    }
}

enum class Emphasis(
    internal val tag: String
) {
    Asterisks("*"),
    Underscore("_")
}

internal class Italic(
    private val content: String,
    private val emphasis: Emphasis
) : Text(content) {
    override fun toMarkdown(): String = buildString {
        content.lineSequence()
            .forEach { line ->
                append(emphasis.tag)
                append(line)
                append(emphasis.tag)
            }
    }
}

internal class Bold(
    private val content: String,
    private val emphasis: Emphasis
) : Text(content) {
    override fun toMarkdown(): String = buildString {
        content.lineSequence()
            .forEach { line ->
                append(emphasis.tag)
                append(emphasis.tag)
                append(line)
                append(emphasis.tag)
                append(emphasis.tag)
            }
    }
}

enum class LineBreak(
    internal val tag: String
) {
    Space("  "),
    Backslash("\\")
}

internal class Paragraph(
    private val content: String,
    private val lineBreak: LineBreak
) : Text(content) {
    override fun toMarkdown(): String = buildString {
        content.lineSequence()
            .forEach { textLine ->
                if (textLine.isNotBlank()) {
                    append(textLine)
                    append(lineBreak.tag)
                    appendLine()
                }
            }
    }
}

class ParagraphBuilder : ElementBuilder {

    private val elementContainer = mutableListOf<Text>()

    fun text(content: () -> String) {
        elementContainer.add(Text(content()))
    }

    fun bold(
        emphasis: Emphasis = Emphasis.Asterisks,
        content: () -> String
    ) {
        elementContainer.add(Bold(content(), emphasis))
    }

    fun italic(
        emphasis: Emphasis = Emphasis.Underscore,
        content: () -> String
    ) {
        elementContainer.add(Italic(content(), emphasis))
    }

    override fun build(): Markdown {
        val content = buildString {
            elementContainer.stream()
                .map { it.toMarkdown() }
                .forEach { element ->
                    append(element)
                    appendLine()
                }
        }
        return Markdown(content)
    }
}

fun MarkdownBuilder.text(
    content: () -> String
) {
    text(content())
}

fun MarkdownBuilder.bold(
    emphasis: Emphasis = Emphasis.Asterisks,
    content: () -> String
) {
    bold(content(), emphasis)
}

fun MarkdownBuilder.italic(
    emphasis: Emphasis = Emphasis.Underscore,
    content: () -> String
) {
    italic(content(), emphasis)
}

fun MarkdownBuilder.paragraph(initialiser: ParagraphBuilder.() -> Unit) {
    val text = ParagraphBuilder().apply(initialiser).build()
    paragraph(text.content)
}




