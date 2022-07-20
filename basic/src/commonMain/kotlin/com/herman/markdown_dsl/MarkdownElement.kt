package com.herman.markdown_dsl


/**
 * Builder for complex Markdown elements.
 * Markdown element is considered complex when it supports multiple different types of nested elements.
 */
internal interface ElementBuilder<out T : MarkdownElement> {
    fun build(): T
}

/**
 * This abstraction should be implemented by all child marker interfaces that want to provide common extensions points
 * to their respective [parent containers][ElementBuilder]. Note that parent container will have to implement
 * concrete child marker interface it wants to have as its nested child in order to get all extensions points.
 *
 * Extension point is an idiomatic (extension, infix or any other) function that provides cleaner api for building
 * complex Markdown elements.
 *
 * The concrete implementation of [ElementBuilder] will use concrete implementation
 * of [ElementContainerBuilder] extension points to build the [MarkdownElement]s.
 */
interface ElementContainerBuilder {
    /** add [element] to the parent container **/
    fun addToContainer(element: MarkdownElement)
}

/**
 * Basic building block for all Markdown elements.
 */
abstract class MarkdownElement {
    /** Produce Markdown content, specific to this element **/
    abstract fun toMarkdown(): String

    /** Same as [toMarkdown]**/
    override fun toString() = toMarkdown()
}