package com.herman.markdown_dsl

import com.herman.markdown_dsl.elements.BlockQuote
import com.herman.markdown_dsl.elements.Bold
import com.herman.markdown_dsl.elements.BoldItalic
import com.herman.markdown_dsl.elements.EmphasisMarker
import com.herman.markdown_dsl.elements.Heading
import com.herman.markdown_dsl.elements.HeadingSizeMarker
import com.herman.markdown_dsl.elements.HorizontalRule
import com.herman.markdown_dsl.elements.HorizontalRuleStyle
import com.herman.markdown_dsl.elements.Italic
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

internal interface ElementBuilder {
    fun build(): Markdown
}

open class MarkdownBuilder : ElementBuilder {

    internal val elementsContainer: MutableList<MarkdownElement> = mutableListOf()

    fun text(content: String) {
        elementsContainer.add(Text(content))
    }

    fun bold(
        content: String,
        emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks
    ) {
        elementsContainer.add(Bold(content, emphasisMarker))
    }

    fun italic(
        content: String,
        emphasisMarker: EmphasisMarker = EmphasisMarker.Underscore
    ) {
        elementsContainer.add(Italic(content, emphasisMarker))
    }

    fun boldItalic(
        content: String,
        emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks
    ) {
        elementsContainer.add(BoldItalic(content, emphasisMarker))
    }

    fun paragraph(
        content: String,
    ) {
        elementsContainer.add(Paragraph(content))
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

    override fun build(): Markdown {
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



