package com.herman.markdown_dsl

import com.herman.markdown_dsl.elements.BlockQuote
import com.herman.markdown_dsl.elements.BlockQuoteContainerBuilder
import com.herman.markdown_dsl.elements.Heading
import com.herman.markdown_dsl.elements.HeadingContainerBuilder
import com.herman.markdown_dsl.elements.HeadingStyleMarker
import com.herman.markdown_dsl.elements.HorizontalRule
import com.herman.markdown_dsl.elements.HorizontalRuleContainerBuilder
import com.herman.markdown_dsl.elements.HorizontalRuleStyle
import com.herman.markdown_dsl.elements.TextLine
import com.herman.markdown_dsl.elements.ListContainerBuilder
import com.herman.markdown_dsl.elements.ListStyleMarker
import com.herman.markdown_dsl.elements.OrderedList
import com.herman.markdown_dsl.elements.ParagraphContainerBuilder
import com.herman.markdown_dsl.elements.TextLineContainerBuilder
import com.herman.markdown_dsl.elements.UnderlinedHeading
import com.herman.markdown_dsl.elements.UnderlinedHeadingStyle
import com.herman.markdown_dsl.elements.UnorderedList

data class Markdown(val content: String) {
    override fun toString(): String = content
}

@MarkdownBuilderMarker
open class MarkdownBuilder : TextLineContainerBuilder, ParagraphContainerBuilder,
    ListContainerBuilder, BlockQuoteContainerBuilder, HeadingContainerBuilder, HorizontalRuleContainerBuilder {

    private val elementsContainer: MutableList<MarkdownElement> = mutableListOf()

    override fun addToContainer(
        element: MarkdownElement
    ) {
        elementsContainer.add(element)
    }

    override fun line(
        content: String
    ) {
        addToContainer(TextLine(content))
    }

    override fun heading(
        content: String,
        style: HeadingStyleMarker
    ) {
        addToContainer(Heading(content, style))
    }

    override fun underlinedHeading(
        content: String,
        style: UnderlinedHeadingStyle
    ) {
        addToContainer(UnderlinedHeading(content, style))
    }

    override fun horizontalRule(
        style: HorizontalRuleStyle
    ) {
        addToContainer(HorizontalRule(style))
    }

    override fun blockQuote(
        content: String
    ) {
        addToContainer(BlockQuote(content))
    }

    override fun orderedList(items: List<String>) {
        addToContainer(OrderedList(items))
    }

    override fun unorderedList(items: List<String>, style: ListStyleMarker) {
        addToContainer(UnorderedList(items, style))
    }

    /**
     * @suppress
     */
    @Suppress("UNUSED_PARAMETER")
    @Deprecated(
        level = DeprecationLevel.ERROR,
        message = "Invalid DSL Scope",
        replaceWith = ReplaceWith("")
    )
    fun markdown(
        initialiser: (@MarkdownBuilderMarker MarkdownBuilder).() -> Unit
    ) = Unit

    fun build(): Markdown {
        val content = buildString {
            elementsContainer.stream()
                .map { it.toMarkdown().trim() }
                .forEach { element ->
                    appendLine()
                    appendLine(element)
                }
        }.trim()
        return Markdown(content)
    }
}

inline fun markdown(
    initialiser: @MarkdownBuilderMarker MarkdownBuilder.() -> Unit
): Markdown = MarkdownBuilder().apply(initialiser).build()



