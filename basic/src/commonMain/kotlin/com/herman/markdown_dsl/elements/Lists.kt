package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.ElementContainerBuilder
import com.herman.markdown_dsl.MarkdownElement
import kotlin.streams.toList

/**
 * ## Unordered List Style Marker
 *
 * Used for style configuration of [UnorderedList] elements
 *
 * Depending on the option, Markdown output will look like:
 *
 * * [Asterisks]
 * + [Plus]
 * - [Hyphen]
 */
enum class ListStyleMarker(internal val tag: String) {
    Asterisks("*"),
    Plus("+"),
    Hyphen("-")
}

/**
 * ## [Unordered List](https://daringfireball.net/projects/markdown/syntax#list)
 *
 * ### Constructs the Markdown Unordered List from the provided content.
 *
 * In order to generate prettier outputs, unordered list will automatically indent all the content by
 * 2 empty space characters from the Markdown unordered list tag.
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *
 *     // Pass a list of items
 *     unorderedList(listOf("Item 1", "Item 2"))
 *
 *     // Or build a list of items if you prefer items to be grouped
 *     unorderedList {
 *         item("Item 1")
 *         item("Item 2")
 *     }
 *
 *     // Or build a list of items than can hold nested elements
 *     unorderedList {
 *         item {
 *             paragraph {
 *                 line("Item 1")
 *                 line("Long explanation about item 1")
 *             }
 *         }
 *         item {
 *             paragraph("Item 1")
 *             unorderedList(listOf("Sub-Item 1", "Sub-Item 2")
 *         }
 *     }
 * }
 * ```
 * That will produce:
 * ```
 * *  Item 1
 * *  Item 2
 *
 * *  Item 1
 * *  Item 2
 *
 * *  Item 1
 *    Long explanation about item 1
 *
 * *  Item 1
 *    * Sub-Item 1
 *    * Sub-Item 2
 * ```
 *
 * By default, list will be created using [asterisks][ListStyleMarker.Asterisks] tag.
 * If you want to use a different tag, set a  list [style] to one of the
 * Markdown supported values, specified in [ListStyleMarker].
 *
 * For example:
 * ```
 * markdown {
 *     unorderedList(ListStyleMarker.Plus)("Item 1")
 * }
 * ```
 * That will produce:
 *```
 * + Item 1
 *```
 *
 *
 * @param items list of Raw, non-sanitised items of this list
 * @param style Custom style for this list, for options see [ListStyleMarker]
 */
class UnorderedList(
    private val items: List<String>,
    private val style: ListStyleMarker
) : MarkdownElement() {

    private val indent = "   "

    override fun toMarkdown(): String = buildString {
        items.forEach { content ->
            val indentedContent = buildString {
                append(content.prependIndent(indent))
                replace(0, 1, style.tag)
            }
            appendLine(indentedContent.removeSuffix(indent))
        }
    }
}

/**
 * ## [Ordered List](https://daringfireball.net/projects/markdown/syntax#list)
 *
 * ### Constructs the Markdown Ordered List from the provided content.
 *
 * In order to generate prettier outputs, ordered list will automatically indent all the content by
 * 2 empty space characters from the list item number.
 *
 * For example, list with 100 elements will be formatted in a following way:
 * ```
 * 1.  Item 1
 * 10.  Item 2
 * 100.  Item 3
 * ```
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *
 *     // Pass a list of items
 *     orderedList(listOf("Item 1", "Item 2"))
 *
 *     // Or build a list of items if you prefer items to be grouped
 *     orderedList {
 *         item("Item 1")
 *         item("Item 2")
 *     }
 *
 *     // Or build a list of items than can hold nested elements
 *     orderedList {
 *         item {
 *             paragraph {
 *                 line("Item 1")
 *                 line("Long explanation about item 1")
 *             }
 *         }
 *         item {
 *             paragraph("Item 1")
 *             unorderedList(listOf("Sub-Item 1", "Sub-Item 2")
 *         }
 *     }
 * }
 * ```
 * That will produce:
 * ```
 * 1.  Item 1
 * 2.  Item 2
 *
 * 1.  Item 1
 * 2.  Item 2
 *
 * 1.  Item 1
 *     Long explanation about item 1
 *
 * 2.  Item 1
 *     1.  Sub-Item 1
 *     2.  Sub-Item 2
 * ```
 *
 * @param items list of Raw, non-sanitised items of this list
 */
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

/**
 * ## ListItem
 *
 * ### Used as a container for complex list items
 *
 * <br></br>
 *
 * ### Usage:
 * @see OrderedList
 * @see UnorderedList
 *
 * @suppress
 */
class ListItem(
    private val item: String
) : MarkdownElement() {
    override fun toMarkdown(): String = item
}

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class ListItemBuilderMarker

/** @suppress **/
class ListItemBuilder : ElementBuilder<ListItem>, TextLineContainerBuilder, ParagraphContainerBuilder,
    ListContainerBuilder,
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

/** @suppress **/
@ListItemBuilderMarker
interface ListItemContainerBuilder : ElementContainerBuilder {
    fun item(content: String) {
        addToContainer(ListItem(content))
    }
}

/** Constructs a new list item and adds it to the parent element **/
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
    private val style: ListStyleMarker
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

/**
 * Marker interface for all [element builders][ElementBuilder]
 * that should support [OrderedList] or [UnorderedList] as their nested elements.
 *
 * Implementations of this interface get all the idiomatic extensions registered
 * to the context of [HeadingContainerBuilder].
 */
@ListBuilderMarker
interface ListContainerBuilder : ElementContainerBuilder {
    fun orderedList(items: List<String>) {
        addToContainer(OrderedList(items))
    }

    fun unorderedList(items: List<String>, style: ListStyleMarker = ListStyleMarker.Asterisks) {
        addToContainer(UnorderedList(items, style))
    }
}

/** Constructs a new ordered list and adds it to the parent element **/
inline fun ListContainerBuilder.orderedList(
    initialiser: @ListBuilderMarker OrderedListBuilder.() -> Unit
) {
    val orderedList = OrderedListBuilder().apply(initialiser).build()
    addToContainer(orderedList)
}

/** Constructs a new unordered list and adds it to the parent element **/
inline fun ListContainerBuilder.unorderedList(
    style: ListStyleMarker = ListStyleMarker.Asterisks,
    initialiser: @ListBuilderMarker UnorderedListBuilder.() -> Unit
) {
    val markdownList = UnorderedListBuilder(style).apply(initialiser).build()
    addToContainer(markdownList)
}