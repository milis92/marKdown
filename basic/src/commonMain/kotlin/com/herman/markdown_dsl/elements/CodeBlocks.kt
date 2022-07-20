package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.ElementContainerBuilder
import com.herman.markdown_dsl.MarkdownElement

/**
 * ## [CodeBlock](https://daringfireball.net/projects/markdown/syntax#precode)
 *
 * ### Constructs the Markdown Code block from the provided content.
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     codeBlock {
 *        @Language("kotlin")
 *        val block =
 *             """
 *             |val hello = "Hello World"
 *             """.trimMargin()
 *        block
 *     }
 * }
 * ```
 * That will produce:
 *```
 *     val hello = "Hello World"
 *```
 *
 *
 * @param content Raw, non-sanitised content for this element
 */
class CodeBlock(
    private val content: String,
) : MarkdownElement() {

    private val indent = "    "

    override fun toMarkdown(): String = buildString {
        appendLine(content.prependIndent(indent))
    }
}

/**
 * Marker interface representing *parent* [element builders][ElementBuilder]
 * that want to have [CodeBlock] as their nested element.
 *
 * Implementations of this interface get all the idiomatic extensions registered
 * to the context of [CodeBlockContainerBuilder].
 *
 * Default implementation adds [CodeBlock] to the list of nested elements,
 * which should be enough for most of the parents.
 */
interface CodeBlockContainerBuilder : ElementContainerBuilder {
    fun codeBlock(content: String) {
        addToContainer(CodeBlock(content))
    }
}

/** Constructs a new code block and adds it to the parent element **/
inline fun CodeBlockContainerBuilder.codeBlock(content: () -> String) = codeBlock(content())