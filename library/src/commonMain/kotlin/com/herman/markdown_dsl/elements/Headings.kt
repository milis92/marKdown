package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.MarkdownBuilder
import com.herman.markdown_dsl.MarkdownElement

/**
 * ## ATX Header Size Marker
 *
 * Used for size configuration of [Heading] elements
 *
 * Markdown output will look like:
 *
 * # [H1]
 * ## [H2]
 * ### [H3]
 * #### [H4]
 * ##### [H5]
 * ###### [H6]
 */
enum class HeadingSizeMarker(
    internal val numberOfHashes: Int
) {
    H1(1), H2(2), H3(3), H4(4), H5(5), H6(6)
}

/**
 * ## [ATX-Stiled Heading](https://daringfireball.net/projects/markdown/syntax#header)
 *
 * To ensure correctness and compatibility, every heading will be seperated from previous content by a new line.
 * Note that Markdown headings don't support multiline texts so actual content will be turned into a single line.
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
 *
 * # Heading
 * _Note the blank line before the actual heading_
 *
 * By default, heading will be created with as H1 heading.
 * If you need a heading with a specific size, set a  heading [size] to one of the values specified in [HeadingSizeMarker]:
 * ```
 * markdown {
 *     heading(HeadingSize.H2)("Heading")
 * }
 * ```
 * This will produce:
 *
 * ## Heading
 *
 *
 * @param text Content for the heading
 * @param size Custom size style for this heading, see [HeadingSizeMarker]
 */
internal class Heading(
    private val text: String,
    private val size: HeadingSizeMarker
) : MarkdownElement() {

    override fun toMarkdown(): String = buildString {
        // For compatibility separate heading from previous content with a new line
        appendLine()
        // Append heading tags
        repeat(size.numberOfHashes) {
            append("#")
        }
        // For compatibility separate heading tag from heading content
        append(" ")
        // Append sanitised content
        append(sanitiseContent(text))
    }
}

/** ## SetextHeader Style Marker
 *
 * Used for size configuration of [UnderlinedHeading] elements
 *
 * Markdown output will look like:
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
 * To ensure correctness and compatibility, every heading will be seperated from previous content by a new line.
 * Note that Markdown headings don't support multiline texts so actual content will be turned into a single line.
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
 *
 * Heading
 * =======
 * _Note the blank line before the actual heading_
 *
 * By default, heading will be created with as H1 heading.
 * If you need a heading of a specific type, set a  heading [size]
 * to one of the values specified in [UnderlinedHeadingStyle]:
 * ```
 * markdown {
 *     underlinedHeading(HeadingSize.H2)("Heading")
 * }
 * ```
 * This will produce:
 *
 * Heading
 * -------
 *
 *
 * @param text Content for the heading
 * @param size Custom size style for this heading, see [UnderlinedHeadingStyle]
 */
class UnderlinedHeading(
    private val text: String,
    private val size: UnderlinedHeadingStyle
) : MarkdownElement() {

    override fun toMarkdown(): String = buildString {
        // For compatibility separate heading from previous content with a new line
        appendLine()
        // Append sanitised content
        append(sanitiseContent(text))
        // Separate content from tag
        appendLine()
        // Underline header
        // -3: 2x new line and 1x space at the end of content
        repeat(length - 3) {
            append(size.tag)
        }
    }
}

fun MarkdownBuilder.heading(
    style: HeadingSizeMarker = HeadingSizeMarker.H1,
    text: () -> String
) {
    heading(text(), style)
}

fun MarkdownBuilder.underlinedHeading(
    style: UnderlinedHeadingStyle = UnderlinedHeadingStyle.H1,
    text: () -> String
) {
    underlinedHeading(text(), style)
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
}