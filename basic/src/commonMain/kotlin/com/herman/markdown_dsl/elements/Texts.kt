package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.ElementContainerBuilder
import com.herman.markdown_dsl.MarkdownElement

/**
 * ## Text Line
 *
 * Simple line of text
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     line("text")
 * }
 * ```
 * That will produce:
 *
 * text
 *
 * <br></br>
 *
 * @param content Raw content of this element
 */
class TextLine(
    private val content: String
) : MarkdownElement() {
    override fun toMarkdown(): String = content.trim()
}

/**
 * Marker interface representing *parent* [element builders][ElementBuilder]
 * that should support [TextLine] as their nested element.
 *
 * Implementations of this interface get all the idiomatic extensions registered
 * to the context of [BlockQuoteContainerBuilder].
 *
 * Default implementation simply adds [TextLine] to the list of nested elements, which should be enough for
 * most of the parent implementations.
 */
interface TextLineContainerBuilder : ElementContainerBuilder {
    fun line(content: String) {
        addToContainer(TextLine(content))
    }
}

/** Constructs a new simple text line **/
inline fun TextLineContainerBuilder.line(
    content: () -> String
) {
    line(content())
}

/**
 * ## Emphasis Marker
 *
 * Used for customising emphasis tags for emphasized [Text] elements, see [Bold],[Italic],[BoldItalic]
 *
 * Markdown output will look like:
 *
 * **[Bold with Asterisks][Asterisks]**
 *
 * *[Italic with Asterisks][Asterisks]*
 *
 * ***[BoldItalic with Asterisks][Asterisks]***
 *
 * __[Bold with Underscore][Underscore]__
 *
 * _[Italic with Underscore][Underscore]_
 *
 * ___[BoldItalic with Underscore][Underscore]___
 */
enum class EmphasisMarker(
    internal val tag: String
) {
    Asterisks("*"),
    Underscore("_")
}

/**
 * ## Simple Italic text
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *    line {
 *        italic("text")
 *    }
 * }
 * ```
 * That will produce:
 *
 *```
 * _text_
 *```
 *
 * By default, _italic_ will be created with [underscore tag][EmphasisMarker.Underscore].
 * If you want to use a different style, set a [emphasisMarker] to one of the values
 * specified in [EmphasisMarker]:
 * ```
 * markdown {
 *     italic("text", EmphasisMarker.Asterisks)
 * }
 * ```
 * This will produce:
 *
 * *text*
 *
 * <br></br>
 *
 * @param content Textual content of this element
 * @param emphasisMarker Custom marker for this element, see [EmphasisMarker]
 */
class Italic(
    private val content: String,
    private val emphasisMarker: EmphasisMarker
) : MarkdownElement() {
    override fun toMarkdown(): String = "${emphasisMarker.tag}${content}${emphasisMarker.tag}"
}

@JvmName("toItalic")
fun String.italic(
    emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks
) = Italic(this, emphasisMarker).toMarkdown()

fun italic(
    content: String,
    emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks
) = Italic(content, emphasisMarker).toMarkdown()

fun italic(
    content: () -> String,
    emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks
) = Italic(content(), emphasisMarker).toMarkdown()

/**
 * ## Simple Bold text
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     line {
 *         bold("text")
 *     }
 * }
 * ```
 * That will produce:
 *
 * **text**
 *
 * By default, **bold** will be created with [asterisks tag][EmphasisMarker.Asterisks].
 * If you want to use a different style, set a [emphasisMarker] to one of the values
 * specified in [EmphasisMarker]:
 * ```
 * markdown {
 *     line {
 *         bold("text", EmphasisMarker.Underscore)
 *     }
 * }
 * ```
 * This will produce:
 *
 * __text__
 *
 * <br></br>
 *
 * @param content Textual content of this element
 * @param emphasisMarker Custom marker for this element, see [EmphasisMarker]
 */
internal class Bold(
    private val content: String,
    private val emphasisMarker: EmphasisMarker
) : MarkdownElement() {
    override fun toMarkdown(): String = buildString {
        content.lineSequence()
            .forEach { line ->
                repeat(2) {
                    append(emphasisMarker.tag)
                }
                append(line)
                repeat(2) {
                    append(emphasisMarker.tag)
                }
            }
    }
}

@JvmName("toBold")
fun String.bold(
    emphasisMarker: EmphasisMarker = EmphasisMarker.Underscore
) = Bold(this, emphasisMarker).toMarkdown()

fun bold(
    content: String,
    emphasisMarker: EmphasisMarker = EmphasisMarker.Underscore
) = Bold(content, emphasisMarker).toMarkdown()

fun bold(
    content: () -> String,
    emphasisMarker: EmphasisMarker = EmphasisMarker.Underscore
) = Bold(content(), emphasisMarker).toMarkdown()


/**
 * ## Simple Code Span
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     line {
 *         codeSpan("text")
 *     }
 * }
 * ```
 * That will produce:
 *
 * `text`
 *
 * <br></br>
 *
 * @param content Textual content of this element
 */
internal class CodeSpan(
    private val content: String
) : MarkdownElement() {

    override fun toMarkdown(): String = "`${content}`"
}

@JvmName("toCodeSpan")
fun String.codeSpan() = CodeSpan(this).toMarkdown()

fun codeSpan(content: String) = CodeSpan(content).toMarkdown()

fun codeSpan(content: () -> String, ) = CodeSpan(content()).toMarkdown()





