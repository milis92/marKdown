package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementContainerBuilder
import com.herman.markdown_dsl.MarkdownBuilder
import com.herman.markdown_dsl.MarkdownBuilderMarker
import com.herman.markdown_dsl.MarkdownElement
import com.herman.markdown_dsl.ElementBuilder

/**
 * ## [BlockQuote](https://daringfireball.net/projects/markdown/syntax#blockquote)
 *
 * ### Constructs the Markdown formatted blockquote from the provided content.
 *
 * Blockquote will automatically sanitise inputs by striping all blank lines before and after the actual content
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     blockQuote("Text")
 * }
 * ```
 * That will produce:
 *```
 * > Text
 *```
 *
 * Blockquote can hold other markdown elements including nested blockquotes:
 * ```
 * markdown {
 *     blockQuote {
 *         blockQuote {
 *             paragraph("Paragraph 1")
 *         }
 *         paragraph("Paragraph 2")
 *     }
 * }
 * ```
 * That will produce:
 *
 * ```
 * > > Paragraph 1
 * >
 * > Paragraph 2
 * ```
 *
 *
 * @param content Raw, non-sanitised content for this element
 */
internal class BlockQuote(
    private val content: String
) : MarkdownElement() {

    override fun toMarkdown(): String = buildString {
        content.lineSequence()
            .forEach { content ->
                // Append blockquote tag
                append(">")
                // Add space delimiter if content is not empty
                if (content.isNotBlank()) {
                    append(" $content")
                }
                // Finish off with a new line
                appendLine()
            }
    }
}

/**
 * Marker interface for all [element builders][ElementBuilder]
 * that should support [BlockQuote] as their nested element.
 *
 * Implementations of this interface get all the idiomatic extensions registered
 * to the context of [BlockQuoteContainerBuilder].
 */
interface BlockQuoteContainerBuilder : ElementContainerBuilder {
    fun blockQuote(content: String) {
        addToContainer(BlockQuote(content))
    }
}

/** Constructs a new blockquote of all valid markdown elements and adds it to the parent element **/
inline fun BlockQuoteContainerBuilder.blockQuote(
    initialiser: @MarkdownBuilderMarker MarkdownBuilder.() -> Unit
) {
    val blockQuoteBuilder = MarkdownBuilder().apply(initialiser).build()
    blockQuote(blockQuoteBuilder.content)
}