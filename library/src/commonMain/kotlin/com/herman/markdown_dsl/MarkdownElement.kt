package com.herman.markdown_dsl

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class MarkdownBuilderMarker

@MarkdownBuilderMarker
internal interface ElementBuilder<out T : MarkdownElement> {
    fun build(): T
}

abstract class MarkdownElement {

    abstract fun toMarkdown(): String

    override fun toString() = toMarkdown()
}