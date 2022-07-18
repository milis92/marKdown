package com.herman.markdown_dsl

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class MarkdownBuilderMarker

internal interface ElementBuilder<out T : MarkdownElement> {
    fun build(): T
}

interface ElementContainerBuilder {
    fun addToContainer(element: MarkdownElement)
}

abstract class MarkdownElement {

    abstract fun toMarkdown(): String

    override fun toString() = toMarkdown()
}