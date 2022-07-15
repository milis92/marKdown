package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.Markdown
import com.herman.markdown_dsl.MarkdownBuilder
import com.herman.markdown_dsl.MarkdownElement
import com.herman.markdown_dsl.elements.HeadingSizeMarker.H1
import com.herman.markdown_dsl.elements.HeadingSizeMarker.H2
import com.herman.markdown_dsl.elements.HeadingSizeMarker.H3
import com.herman.markdown_dsl.elements.HeadingSizeMarker.H4
import com.herman.markdown_dsl.elements.HeadingSizeMarker.H5
import com.herman.markdown_dsl.elements.HeadingSizeMarker.H6

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
open class Text(
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
) : Text(content) {
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
) : Text(content) {
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
) : Text(content) {
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
 * ## [Paragraph](https://daringfireball.net/projects/markdown/syntax#p)
 *
 * For correctness every item contained in this paragraph will be delimited with break tag (double space characters),
 * and will have a new empty line at the end.
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     paragraph {
 *         text { "text" }
 *         bold { "bold" }
 *         italic { "italic" }
 *         boldItalic { "boldItalic" }
 *     }
 * }
 * ```
 * That will produce:
 *```
 * text
 * **bold**
 * _italic_
 * ***boldItalic***
 *
 *```
 * _Note the blank line after the paragraph_
 *
 * <br></br>
 *
 * @param content Textual content of this element
 */
internal class Paragraph(
    private val content: String
) : Text(content) {

    private val lineBreak = "  "

    override fun toMarkdown(): String = buildString {
        content.lineSequence()
            .forEach { textLine ->
                if (textLine.isNotBlank()) {
                    append(textLine)
                    //By standard every line should end with line break and a return charactres
                    append(lineBreak)
                    appendLine()
                }
            }
    }
}

class ParagraphBuilder : ElementBuilder {

    private val elementContainer = mutableListOf<Text>()

    fun text(content: String) {
        elementContainer.add(Text(content))
    }

    fun bold(
        content: String,
        emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks
    ) {
        elementContainer.add(Bold(content, emphasisMarker))
    }

    fun italic(
        content: String,
        emphasisMarker: EmphasisMarker = EmphasisMarker.Underscore
    ) {
        elementContainer.add(Italic(content, emphasisMarker))
    }

    fun boldItalic(
        content: String,
        emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks
    ) {
        elementContainer.add(BoldItalic(content, emphasisMarker))
    }

    override fun build(): Markdown {
        val content = buildString {
            elementContainer.stream()
                .map { it.toMarkdown() }
                .forEach { element ->
                    append(element)
                    appendLine()
                }
        }
        return Markdown(content)
    }
}

fun ParagraphBuilder.text(
    content: () -> String
) {
    text(content())
}

fun ParagraphBuilder.bold(
    emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks,
    content: () -> String
) {
    bold(content(), emphasisMarker)
}

fun ParagraphBuilder.italic(
    emphasisMarker: EmphasisMarker = EmphasisMarker.Underscore,
    content: () -> String
) {
    italic(content(), emphasisMarker)
}

fun ParagraphBuilder.boldItalic(
    emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks,
    content: () -> String
) {
    boldItalic(content(), emphasisMarker)
}

fun MarkdownBuilder.text(
    content: () -> String
) {
    text(content())
}

fun MarkdownBuilder.bold(
    emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks,
    content: () -> String
) {
    bold(content(), emphasisMarker)
}

fun MarkdownBuilder.italic(
    emphasisMarker: EmphasisMarker = EmphasisMarker.Underscore,
    content: () -> String
) {
    italic(content(), emphasisMarker)
}

fun MarkdownBuilder.boldItalic(
    emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks,
    content: () -> String
) {
    boldItalic(content(), emphasisMarker)
}

fun MarkdownBuilder.paragraph(initialiser: ParagraphBuilder.() -> Unit) {
    val text = ParagraphBuilder().apply(initialiser).build()
    paragraph(text.content)
}




