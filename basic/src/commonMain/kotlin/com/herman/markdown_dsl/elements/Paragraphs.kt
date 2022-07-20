package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.ElementContainerBuilder
import com.herman.markdown_dsl.MarkdownElement
import kotlin.streams.toList

/**
 * ## [Paragraph](https://daringfireball.net/projects/markdown/syntax#p)
 *
 * ### Constructs consecutive lines of text, seperated by Markdown line break (two empty characters + a new line)
 *
 * For correctness, paragraph will automatically sanitise inputs by striping all
 * blank lines before and after the actual content.
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     paragraph {
 *         line { "First line" }
 *         line { "Second line" }
 *     }
 * }
 * ```
 * That will produce:
 *```
 * First line
 * Second line
 *```
 *
 * <br></br>
 *
 * @param content Raw, non-sanitised list of lines constructing this paragraph
 */
class Paragraph(
    private val content: List<String>
) : MarkdownElement() {

    private val lineBreak = "  \n"

    override fun toMarkdown(): String = buildString {
        val sanitisedContent = buildString {
            content.stream()
                .map { it.trim() }
                .filter { it.isNotBlank() }
                .forEach { line ->
                    append(line + lineBreak)
                }
        }.removeSuffix(lineBreak)

        appendLine(sanitisedContent)
    }
}

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class ParagraphBuilderMarker

class ParagraphBuilder : ElementBuilder<Paragraph>, TextLineContainerBuilder {

    private val elementsContainer = mutableListOf<MarkdownElement>()

    override fun addToContainer(element: MarkdownElement) {
        elementsContainer.add(element)
    }

    override fun build(): Paragraph {
        val content = elementsContainer
            .stream()
            .map { it.toMarkdown() }
            .toList()
        return Paragraph(content)
    }
}

/**
 * Marker interface for all [element builders][ElementBuilder]
 * that should support [Paragraph] element.
 *
 * Implementations of this interface get all the idiomatic extensions registered
 * to the context of [ParagraphContainerBuilder].
 */
@ParagraphBuilderMarker
interface ParagraphContainerBuilder : ElementContainerBuilder {
    fun paragraph(lines: List<String>) {
        addToContainer(Paragraph(lines))
    }
}

/** Constructs a new paragraph that can have complex items **/
inline fun ParagraphContainerBuilder.paragraph(
    initialiser: @ParagraphBuilderMarker ParagraphBuilder.() -> Unit
) {
    val paragraph = ParagraphBuilder().apply(initialiser).build()
    addToContainer(paragraph)
}