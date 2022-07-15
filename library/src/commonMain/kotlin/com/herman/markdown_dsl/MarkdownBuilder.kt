package com.herman.markdown_dsl

import com.herman.markdown_dsl.elements.BlockQuote
import com.herman.markdown_dsl.elements.Bold
import com.herman.markdown_dsl.elements.Emphasis
import com.herman.markdown_dsl.elements.Heading
import com.herman.markdown_dsl.elements.HeadingSizeMarker
import com.herman.markdown_dsl.elements.HorizontalRule
import com.herman.markdown_dsl.elements.HorizontalRuleStyle
import com.herman.markdown_dsl.elements.Italic
import com.herman.markdown_dsl.elements.LineBreak
import com.herman.markdown_dsl.elements.ListMarker
import com.herman.markdown_dsl.elements.OrderedList
import com.herman.markdown_dsl.elements.Paragraph
import com.herman.markdown_dsl.elements.Text
import com.herman.markdown_dsl.elements.UnderlinedHeading
import com.herman.markdown_dsl.elements.UnderlinedHeadingStyle
import com.herman.markdown_dsl.elements.UnorderedList

data class Markdown(val content: String) {
    override fun toString(): String = content
}

open class MarkdownBuilder {

    internal val elementsContainer: MutableList<MarkdownElement> = mutableListOf()

    fun text(content: String) {
        elementsContainer.add(Text(content))
    }

    fun bold(
        content: String,
        emphasis: Emphasis = Emphasis.Asterisks
    ) {
        elementsContainer.add(Bold(content, emphasis))
    }

    fun italic(
        content: String,
        emphasis: Emphasis = Emphasis.Underscore
    ) {
        elementsContainer.add(Italic(content, emphasis))
    }

    fun paragraph(
        content: String,
        lineBreak: LineBreak = LineBreak.Space,
    ) {
        elementsContainer.add(Paragraph(content, lineBreak))
    }

    fun heading(
        content: String,
        style: HeadingSizeMarker = HeadingSizeMarker.H1
    ) {
        elementsContainer.add(Heading(content, style))
    }

    fun underlinedHeading(
        content: String,
        style: UnderlinedHeadingStyle = UnderlinedHeadingStyle.H1
    ) {
        elementsContainer.add(UnderlinedHeading(content, style))
    }

    fun horizontalRule(
        style: HorizontalRuleStyle = HorizontalRuleStyle.Hyphen
    ) {
        elementsContainer.add(HorizontalRule(style))
    }

    fun orderedList(
        content: List<String>
    ) {
        elementsContainer.add(OrderedList(content))
    }

    fun unorderedList(
        content: List<String>,
        listMarker: ListMarker = ListMarker.Asterisks
    ) {
        elementsContainer.add(UnorderedList(content, listMarker))
    }

    fun blockQuote(
        content: String
    ) {
        elementsContainer.add(BlockQuote(content))
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



