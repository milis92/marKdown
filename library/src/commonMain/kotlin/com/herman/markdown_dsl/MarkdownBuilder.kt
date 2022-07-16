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
import com.herman.markdown_dsl.elements.ListBuilder
import com.herman.markdown_dsl.elements.ListMarker
import com.herman.markdown_dsl.elements.OrderedList
import com.herman.markdown_dsl.elements.Paragraph
import com.herman.markdown_dsl.elements.Text
import com.herman.markdown_dsl.elements.TextBuilder
import com.herman.markdown_dsl.elements.UnderlinedHeading
import com.herman.markdown_dsl.elements.UnderlinedHeadingStyle
import com.herman.markdown_dsl.elements.UnorderedList

data class Markdown(val content: String) {
    override fun toString(): String = content
}

@MarkdownBuilderMarker
open class MarkdownBuilder(
) : TextBuilder, ListBuilder {

    private val elementsContainer: MutableList<MarkdownElement> = mutableListOf()

    internal fun addElement(
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

    fun heading(
        content: String,
        style: HeadingSizeMarker
    ) {
        addElement(Heading(content, style))
    }

    fun underlinedHeading(
        content: String,
        style: UnderlinedHeadingStyle
    ) {
        addElement(UnderlinedHeading(content, style))
    }

    fun horizontalRule(
        style: HorizontalRuleStyle
    ) {
        addElement(HorizontalRule(style))
    }

    fun blockQuote(
        content: String
    ) {
        addElement(BlockQuote(content))
    }

    fun paragraph(
        content: String
    ) {
        addElement(Paragraph(content.lines()))
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

    fun build(): Markdown {
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

inline fun markdown(
    initialiser: @MarkdownBuilderMarker MarkdownBuilder.() -> Unit
): Markdown {
    return MarkdownBuilder().apply(initialiser).build()
}



