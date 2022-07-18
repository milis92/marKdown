package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.ElementContainerBuilder
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
            .map { it.trim() }
            .forEach { line ->
                if(line.isNotBlank()){
                    appendLine(line + lineBreak)
                }
            }
    }
}

private class Line(
    private val content: String
) : MarkdownElement() {
    override fun toMarkdown(): String = content
}

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class ParagraphBuilderMarker

class ParagraphBuilder : ElementBuilder<Paragraph> {

    private val elementsContainer = mutableListOf<MarkdownElement>()

    private fun addToContainer(element: MarkdownElement) {
        elementsContainer.add(element)
    }

    fun line(
        content: String
    ) {
        addToContainer(Line(content))
    }

    override fun build(): Paragraph {
        val content = elementsContainer
            .stream()
            .map { it.toMarkdown() }
            .toList()
        return Paragraph(content)
    }
}

inline fun ParagraphBuilder.line(
    content: () -> String
) {
    line(content())
}

@ParagraphBuilderMarker
interface ParagraphContainerBuilder : ElementContainerBuilder {
    fun paragraph(lines: List<String>) {
        addToContainer(Paragraph(lines))
    }
}

inline fun ParagraphContainerBuilder.paragraph(
    initialiser: @ParagraphBuilderMarker ParagraphBuilder.() -> Unit
) {
    val paragraph = ParagraphBuilder().apply(initialiser).build()
    addToContainer(paragraph)
}

fun ParagraphContainerBuilder.paragraph(content: String) {
    paragraph(listOf(content))
}