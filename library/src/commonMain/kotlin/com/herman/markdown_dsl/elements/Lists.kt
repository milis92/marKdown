package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.ElementContainerBuilder
import com.herman.markdown_dsl.MarkdownBuilderMarker
import com.herman.markdown_dsl.MarkdownElement
import kotlin.streams.toList

enum class ListMarker(internal val tag: String) {
    Asterisks("*"),
    Plus("+"),
    Hyphen("-")
}

class UnorderedList(
    private val items: List<String>,
    private val listMarker: ListMarker
) : MarkdownElement() {

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

class OrderedList(
    private val items: List<String>
) : MarkdownElement() {

    private val indent = "  "

    override fun toMarkdown(): String = buildString {
        items
            .map { it.removePrefix("\n") }
            .forEachIndexed { index, content ->
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

class ListItem(
    private val item: String
) : MarkdownElement() {
    override fun toMarkdown(): String = item
}

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class ListItemBuilderMarker

class ListItemBuilder : ElementBuilder<ListItem>, ParagraphContainerBuilder, ListContainerBuilder,
    BlockQuoteContainerBuilder, HeadingContainerBuilder {

    private val elementsContainer: MutableList<MarkdownElement> = mutableListOf()

    override fun addToContainer(element: MarkdownElement) {
        elementsContainer.add(element)
    }

    override fun build(): ListItem {
        val content = buildString {
            elementsContainer.stream()
                .map { it.toMarkdown() }
                .forEach { element ->
                    append(element)
                }
        }
        return ListItem(content)
    }
}

@ListItemBuilderMarker
interface ListItemContainerBuilder : ElementContainerBuilder {
    fun item(content: String) {
        addToContainer(ListItem(content))
    }
}

inline fun ListItemContainerBuilder.item(
    initialiser: @ListItemBuilderMarker ListItemBuilder.() -> Unit
) {
    val listItem = ListItemBuilder().apply(initialiser).build()
    addToContainer(listItem)
}

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class ListBuilderMarker

class OrderedListBuilder : ElementBuilder<OrderedList>, ListItemContainerBuilder {

    private val elementsContainer: MutableList<MarkdownElement> = mutableListOf()

    override fun addToContainer(element: MarkdownElement) {
        elementsContainer.add(element)
    }

    override fun build(): OrderedList {
        val content = elementsContainer
            .stream()
            .map { it.toMarkdown() }
            .toList()
        return OrderedList(content)
    }
}

class UnorderedListBuilder(
    private val style: ListMarker
) : ElementBuilder<UnorderedList>, ListItemContainerBuilder {

    private val elementsContainer: MutableList<MarkdownElement> = mutableListOf()

    override fun addToContainer(element: MarkdownElement) {
        elementsContainer.add(element)
    }

    override fun build(): UnorderedList {
        val content = elementsContainer
            .stream()
            .map { it.toMarkdown() }
            .toList()
        return UnorderedList(content, style)
    }
}

@ListBuilderMarker
interface ListContainerBuilder : ElementContainerBuilder {
    fun orderedList(items: List<String>) {
        addToContainer(OrderedList(items))
    }

    fun unorderedList(items: List<String>, style: ListMarker = ListMarker.Asterisks) {
        addToContainer(UnorderedList(items, style))
    }
}

inline fun ListContainerBuilder.orderedList(
    initialiser: @ListBuilderMarker OrderedListBuilder.() -> Unit
) {
    val orderedList = OrderedListBuilder().apply(initialiser).build()
    addToContainer(orderedList)
}

inline fun ListContainerBuilder.unorderedList(
    style: ListMarker = ListMarker.Asterisks,
    initialiser: @ListBuilderMarker UnorderedListBuilder.() -> Unit
) {
    val markdownList = UnorderedListBuilder(style).apply(initialiser).build()
    addToContainer(markdownList)
}