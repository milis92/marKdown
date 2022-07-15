package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.Markdown
import com.herman.markdown_dsl.MarkdownBuilder
import com.herman.markdown_dsl.MarkdownElement

enum class HeaderSize(
    internal val numberOfHashes: Int
) {
    H1(1), H2(2), H3(3), H4(4), H5(5), H6(6)
}

// ATXHeader
class Header(
    private val text: String,
    private val size: HeaderSize
) : MarkdownElement() {

    override fun toMarkdown(): String = buildString {
        repeat(size.numberOfHashes) {
            append("#")
        }
        append(" ")
        append(text)
    }
}

enum class UnderlinedHeaderStyle(internal val tag: String) {
    H1("="),
    H2("-")
}

// SetextHeader
class UnderlinedHeader(
    private val text: String,
    private val size: UnderlinedHeaderStyle
) : MarkdownElement() {

    override fun toMarkdown(): String = buildString {
        append(text)
        appendLine()
        repeat(text.length) {
            append(size.tag)
        }
    }
}

fun MarkdownBuilder.header(
    style: HeaderSize = HeaderSize.H1,
    text: () -> String
) {
    header(text(), style)
}

fun MarkdownBuilder.underlineHeader(
    style: UnderlinedHeaderStyle = UnderlinedHeaderStyle.H1,
    text: () -> String
) {
    underlineHeader(text(), style)
}