package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.MarkdownElement
import kotlin.streams.toList

enum class ListMarker(internal val tag: String) {
    Asterisks("*"),
    Plus("+"),
    Hyphen("-")
}

open class MarkdownList(
    @PublishedApi
    internal open val items: List<String>
) : MarkdownElement() {
    override fun toMarkdown(): String = items.joinToString()
}

internal class UnorderedList(
    override val items: List<String>,
    private val listMarker: ListMarker
) : MarkdownList(items) {

    private val indent = "   "

    override fun toMarkdown(): String = buildString {
        items.forEach { content ->
            val indentedContent = buildString {
                append(content.prependIndent(indent))
                replace(0, 1, listMarker.tag)
            }
            append(indentedContent)
            appendLine()
        }
    }
}

internal class OrderedList(
    override val items: List<String>
) : MarkdownList(items) {

    private val indent = "  "

    override fun toMarkdown(): String = buildString {
        items
            .map { it.removePrefix("\n") }
            .forEachIndexed { index, content ->
                println("S${content}E")
                val stringIndex = "${index + 1}."
                // Indent that takes index length into account, so we can offset paragraphs that are on 9+ position
                val indexedIndent = " ".repeat(stringIndex.length) + indent

                /*
                 Indented content that should look like
                 1.  Line 1
                     Line 2
                 10.  Line 1
                      Line 2
                 100.  Line 1
                       Line 2
                 */
                val indentedContent = buildString {
                    append(content.prependIndent(indexedIndent))
                    replace(0, stringIndex.length, stringIndex)
                }.removeSuffix(indexedIndent)

                appendLine(indentedContent)
            }
    }
}

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class ListBuilderMarker

@ListBuilderMarker
interface ListBuilder {
    fun orderedList(content: List<String>)

    fun unorderedList(content: List<String>, listMarker: ListMarker = ListMarker.Asterisks)
}

class MarkdownListBuilder : ElementBuilder<MarkdownList>, TextBuilder, ListBuilder {

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

    //TODO Add tests for this
    fun blockQuote(
        content: String
    ) {
        addElement(BlockQuote(content))
    }

    //TODO Add tests for this
    fun heading(
        content: String,
        style: HeadingSizeMarker = HeadingSizeMarker.H1
    ) {
        addElement(Heading(content, style))
    }

    //TODO Add tests for this
    fun underlinedHeading(
        content: String,
        style: UnderlinedHeadingStyle = UnderlinedHeadingStyle.H1
    ) {
        addElement(UnderlinedHeading(content, style))
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

    override fun build(): MarkdownList {
        val content = elementsContainer
            .stream()
            .map { it.toMarkdown() }
            .toList()
        return MarkdownList(content)
    }
}

inline fun ListBuilder.orderedList(
    initialiser: @ListBuilderMarker MarkdownListBuilder.() -> Unit
) {
    val markdownList = MarkdownListBuilder().apply(initialiser).build()
    orderedList(markdownList.items)
}

inline fun ListBuilder.unorderedList(
    initialiser: @ListBuilderMarker MarkdownListBuilder.() -> Unit
) {
    val markdownList = MarkdownListBuilder().apply(initialiser).build()
    unorderedList(markdownList.items)
}

fun MarkdownListBuilder.paragraph(
    initialiser: @ParagraphBuilderMarker ParagraphBuilder.() -> Unit
) {
    val paragraph = ParagraphBuilder().apply(initialiser).build()
    addElement(paragraph)
}
