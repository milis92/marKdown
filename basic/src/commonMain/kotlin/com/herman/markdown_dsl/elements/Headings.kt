package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.ElementContainerBuilder
import com.herman.markdown_dsl.MarkdownElement

/**
 * ## ATX Header Style Marker
 *
 * Used for style configuration of [Heading] elements
 *
 * Depending on the option, Markdown output will look like:
 *
 * # [H1]
 * ## [H2]
 * ### [H3]
 * #### [H4]
 * ##### [H5]
 * ###### [H6]
 */
enum class HeadingStyleMarker(
    internal val tag: String
) {
    H1("#"), H2("##"), H3("###"), H4("####"), H5("#####"), H6("######")
}

/**
 * ## [ATX-Stiled Heading](https://daringfireball.net/projects/markdown/syntax#header)
 *
 * ### Constructs the Markdown ATX-Stiled heading from the provided content.
 *
 * Markdown headings don't support multiline texts so actual content will be sanitised into a single line.
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     heading("Heading")
 * }
 * ```
 * That will produce:
 * ```
 * # Heading
 * ```
 *
 * By default, heading will be created as an H1 heading.
 * If you need a heading of a different style, set a  heading [style] to
 * one of the values specified in [HeadingStyleMarker].
 *
 * For example:
 * ```
 * markdown {
 *     heading(HeadingSize.H2)("Heading")
 * }
 * ```
 * That will produce:
 *```
 * ## Heading
 *```
 *
 *
 * @param content Raw, non-sanitised content for this element
 * @param style Custom style for this heading, for options see [HeadingStyleMarker]
 */
internal class Heading(
    private val content: String,
    private val style: HeadingStyleMarker
) : MarkdownElement() {

    override fun toMarkdown(): String = buildString {
        // Append heading tags
        append(style.tag)
        // For compatibility separate heading tag from heading content
        append(" ")
        // Append sanitised content
        append(sanitiseContent(content))
    }
}

/** ## SetextHeader Style Marker
 *
 * Used for style configuration of [UnderlinedHeading] elements
 *
 * Depending on the option, Markdown output will look like:
 *
 * ```
 * H1
 * ==
 * ```
 *
 * ```
 * H2
 * --
 * ```
 */
enum class UnderlinedHeadingStyle(
    internal val tag: String
) {
    H1("="),
    H2("-")
}

/**
 * ## [Setext-Stiled Heading](https://daringfireball.net/projects/markdown/syntax#header)
 *
 * ### Constructs the Markdown Setext-Stiled heading from the provided content.
 *
 * Markdown headings don't support multiline texts so actual content will be sanitised into a single line.
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     underlinedHeading("Heading")
 * }
 * ```
 * That will produce:
 * ```
 * Heading
 * =======
 * ```
 *
 * By default, heading will be created with as an H1 heading.
 * If you need a heading of a different style set a heading [style] to one of the
 *  Markdown supported values, specified in [UnderlinedHeadingStyle].
 *
 * For example:
 * ```
 * markdown {
 *     underlinedHeading(HeadingSize.H2)("Heading")
 * }
 * ```
 * That will produce:
 * ```
 * Heading
 * -------
 * ```
 *
 * @param content Raw, non-sanitised content for this element
 * @param style Custom style for this heading, for options see [UnderlinedHeadingStyle]
 */
internal class UnderlinedHeading(
    private val content: String,
    private val style: UnderlinedHeadingStyle
) : MarkdownElement() {

    override fun toMarkdown(): String = buildString {
        // Append sanitised content
        val sanitisedContent = sanitiseContent(content)
        append(sanitisedContent)
        // Separate content from tag
        appendLine()
        // Underline header
        // 2 new line and 1x space at the end of content
        repeat(length - 1) {
            append(style.tag)
        }
    }
}

private fun sanitiseContent(
    content: String
): String = buildString {
    content.lineSequence().forEach { content ->
        if (content.isNotBlank()) {
            append(content)
            append(" ")
        }
    }
}.trim()

/**
 * Marker interface for all [element builders][ElementBuilder]
 * that should support [Heading] or [UnderlinedHeading] as their nested elements.
 *
 * Implementations of this interface get all the idiomatic extensions registered
 * to the context of [HeadingContainerBuilder].
 */
interface HeadingContainerBuilder : ElementContainerBuilder {
    fun heading(
        content: String,
        style: HeadingStyleMarker = HeadingStyleMarker.H1
    ) {
        addToContainer(Heading(content, style))
    }

    fun underlinedHeading(
        content: String,
        style: UnderlinedHeadingStyle = UnderlinedHeadingStyle.H1
    ) {
        addToContainer(UnderlinedHeading(content, style))
    }
}

/** Constructs a new heading and adds it to the parent element **/
inline fun HeadingContainerBuilder.heading(
    style: HeadingStyleMarker = HeadingStyleMarker.H1,
    text: () -> String
) {
    heading(text(), style)
}

/** Constructs a new underlined heading and adds it to the parent element **/
inline fun HeadingContainerBuilder.underlinedHeading(
    style: UnderlinedHeadingStyle = UnderlinedHeadingStyle.H1,
    text: () -> String
) {
    underlinedHeading(text(), style)
}

