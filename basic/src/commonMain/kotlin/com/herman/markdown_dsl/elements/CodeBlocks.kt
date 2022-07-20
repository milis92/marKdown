package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementContainerBuilder
import com.herman.markdown_dsl.MarkdownElement

class CodeBlock(
    private val content: String,
) : MarkdownElement() {

    private val indent = "    "

    override fun toMarkdown(): String = buildString {
        println(content.prependIndent(indent))
        appendLine(content.prependIndent(indent))
    }
}

interface CodeBlockContainerBuilder : ElementContainerBuilder {
    fun codeBlock(content: String) {
        addToContainer(CodeBlock(content))
    }
}

inline fun CodeBlockContainerBuilder.codeBlock(
    content: () -> String
) {
    codeBlock(content())
}