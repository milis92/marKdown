package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.MarkdownBuilder
import com.herman.markdown_dsl.MarkdownElement
import kotlin.streams.toList

/**
 * ## [Paragraph](https://daringfireball.net/projects/markdown/syntax#p)
 *
 * For correctness every item contained in this paragraph will be delimited with break tag (double space characters),
 * and will have a new empty line at the end.
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     paragraph {
 *         text { "text" }
 *         bold { "bold" }
 *         italic { "italic" }
 *         boldItalic { "boldItalic" }
 *     }
 * }
 * ```
 * That will produce:
 *```
 * text
 * **bold**
 * _italic_
 * ***boldItalic***
 *
 *```
 * _Note the blank line after the paragraph_
 *
 * <br></br>
 *
 * @param content Textual content of this element
 */
class Paragraph(
    private val content: List<String>
) : MarkdownElement() {

    private val lineBreak = "  "

    override fun toMarkdown(): String = buildString {
        content.stream()
            .map { it.removeSuffix("\n") }
            .forEach { line ->
                appendLine(line + lineBreak)
            }
    }
}

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class ParagraphBuilderMarker

@ParagraphBuilderMarker
class ParagraphBuilder : ElementBuilder<Paragraph>, TextBuilder, ListBuilder {

    private val elementsContainer = mutableListOf<MarkdownElement>()

    private fun addElement(
        element: MarkdownElement
    ) {
        elementsContainer.add(element)
    }

    override fun text(
        content: String
    ) {
        addElement(Text(content))
    }

    override fun bold(
        content: String,
        emphasisMarker: EmphasisMarker
    ) {
        addElement(Bold(content, emphasisMarker))
    }

    override fun italic(
        content: String,
        emphasisMarker: EmphasisMarker
    ) {
        addElement(Italic(content, emphasisMarker))
    }

    override fun boldItalic(
        content: String,
        emphasisMarker: EmphasisMarker
    ) {
        addElement(BoldItalic(content, emphasisMarker))
    }

    override fun orderedList(
        content: List<String>
    ) {
        addElement(OrderedList(content))
    }

    override fun unorderedList(
        content: List<String>,
        listMarker: ListMarker
    ) {
        addElement(UnorderedList(content, listMarker))
    }

    override fun build(): Paragraph {
        val content = elementsContainer
            .stream()
            .map { it.toMarkdown() }
            .toList()
        return Paragraph(content)
    }
}

fun MarkdownBuilder.paragraph(
    initialiser: @ParagraphBuilderMarker ParagraphBuilder.() -> Unit
) {
    val paragraph = ParagraphBuilder().apply(initialiser).build()
    addElement(paragraph)
}
