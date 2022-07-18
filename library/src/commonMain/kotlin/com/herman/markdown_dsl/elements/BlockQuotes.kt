package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementContainerBuilder
import com.herman.markdown_dsl.MarkdownBuilder
import com.herman.markdown_dsl.MarkdownBuilderMarker
import com.herman.markdown_dsl.MarkdownElement

/**
 * ## [Blockquotes](https://daringfireball.net/projects/markdown/syntax#blockquote)
 *
 * To ensure correctness and compatibility, every blockquote will be seperated
 * from previous and following content by a new line.
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     blockQuote {
 *         text { "text" }
 *         bold { "bold" }
 *         italic { "italic" }
 *         boldItalic { "boldItalic" }
 *     }
 * }
 * ```
 * That will produce:
 *```
 * > text
 * > **bold**
 * > _italic_
 * > ***boldItalic***
 *```
 * _Note the blank lines before and after the paragraph_
 *
 * <br></br>
 *
 * Blockquote can hold any other markdown element as well:
 * ```
 * markdown {
 *     blockQuote {
 *         paragraph {
 *             text { "Sentence 1" }
 *             text { "Sentence 2" }
 *         }
 *         paragraph {
 *             text { "Sentence 1" }
 *             text { "Sentence 2" }
 *         }
 *     }
 * }
 * ```
 * That will produce:
 *
 * ```
 * > Sentence 1
 * > Sentence 2
 * >
 * > Sentence 1
 * > Sentence 2
 * ```
 *
 * <br></br>
 *
 * @param content Textual content of this element
 */
class BlockQuote(
    private val content: String
) : MarkdownElement() {

    override fun toMarkdown(): String = buildString {
        content
            .removePrefix("\n")
            .removeSuffix("\n")
            .lineSequence()
            .forEach { content ->
                // Append blockquote tag
                append(">")
                // Add space delimiter if content is not empty
                if (content.isNotBlank()) {
                    append(" ")
                    append(content)
                }
                // Finish off with a new line
                appendLine()
            }
    }
}

interface BlockQuoteContainerBuilder : ElementContainerBuilder {
    fun blockQuote(content: String) {
        addToContainer(BlockQuote(content))
    }
}

inline fun BlockQuoteContainerBuilder.blockQuote(
    initialiser: @MarkdownBuilderMarker MarkdownBuilder.() -> Unit
) {
    val blockQuoteBuilder = MarkdownBuilder().apply(initialiser).build()
    blockQuote(blockQuoteBuilder.content)
}