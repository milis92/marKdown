package com.herman.markdown_dsl

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class MarkdownElementMarker

@MarkdownElementMarker
abstract class MarkdownElement {

    abstract fun toMarkdown(): String

    override fun toString() = toMarkdown()
}

internal interface ElementBuilder {
    fun build(): Markdown
}



