package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.ElementContainerBuilder
import com.herman.markdown_dsl.MarkdownElement

/**
 * ## Simple text
 *
 * Simple text element without any formatting (printed as is)
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     text("text")
 * }
 * ```
 * That will produce:
 *
 * text
 *
 * <br></br>
 *
 * @param content Textual content of this element
 */
internal class Text(
    private val content: String
) : MarkdownElement() {
    override fun toMarkdown(): String = buildString {
        append(content)
    }
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
 *     italic("text")
 * }
 * ```
 * That will produce:
 *
 * _text_
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
internal class Italic(
    private val content: String,
    private val emphasisMarker: EmphasisMarker
) : MarkdownElement() {
    override fun toMarkdown(): String = buildString {
        content.lineSequence()
            .forEach { line ->
                append(emphasisMarker.tag)
                append(line)
                append(emphasisMarker.tag)
            }
    }
}

/**
 * ## Simple Bold text
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     bold("text")
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
 *     bold("text", EmphasisMarker.Underscore)
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

/**
 * ## Simple BoldItalic text
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     boldItalic("text")
 * }
 * ```
 * That will produce:
 *
 * ***text***
 *
 * By default, ***bold*** will be created with [asterisks tag][EmphasisMarker.Asterisks].
 * If you want to use a different style, set a [emphasisMarker] to one of the values
 * specified in [EmphasisMarker]:
 * ```
 * markdown {
 *     boldItalic("text", EmphasisMarker.Underscore)
 * }
 * ```
 * This will produce:
 *
 * ___text___
 *
 *
 * @param content Textual content of this element
 * @param emphasisMarker Custom marker for this element, see [EmphasisMarker]
 */
internal class BoldItalic(
    private val content: String,
    private val emphasisMarker: EmphasisMarker
) : MarkdownElement() {
    override fun toMarkdown(): String = buildString {
        content.lineSequence()
            .forEach { line ->
                repeat(3) {
                    append(emphasisMarker.tag)
                }
                append(line)
                repeat(3) {
                    append(emphasisMarker.tag)
                }
            }
    }
}

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
 * Marker interface for all [element builders][ElementBuilder]
 * that should support [TextLine] as their nested element.
 *
 * Implementations of this interface get all the idiomatic extensions registered
 * to the context of [BlockQuoteContainerBuilder].
 */
interface TextLineContainerBuilder : ElementContainerBuilder {
    fun line(content: String){
        addToContainer(TextLine(content))
    }
}

/** Constructs a new simple text line **/
inline fun TextLineContainerBuilder.line(
    content: () -> String
) {
    line(content())
}





