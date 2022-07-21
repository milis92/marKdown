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
 *
 *     line {
 *         """
 *         |Multi-line text, that has to be broken into multiple lines
 *         |in order to fit editor code width limits for example.
 *         """.trimMargin()
 *     }
 * }
 * ```
 * That will produce:
 *
 * text
 *
 * Multi-line text, that has to be broken into multiple lines in order to fit editor code width limits for example.
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
 *    italic("text")
 *
 *    italic {
 *       """
 *       |Multi-line italic text, that has to be broken into multiple lines
 *       |in order to fit editor code width limits for example.
 *       """.trimMargin()
 *     }
 * }
 * ```
 * That produces:
 *```
 * _text_
 *
 * _Multi-line text, that has to be broken into multiple lines in order to fit editor code width limits for example._
 *```
 *
 * Italic can also be inlined with any other text element, for example:
 * ```
 * markdown {
 *    line("An example of" + italic("Inlined italic") + " text")
 * }
 * ```
 * That produces:
 * ```
 * An example of _Inlined italic_ text
 * ```
 *
 * By default, _italic_ will be created with [underscore tag][EmphasisMarker.Underscore].
 * If you want to use a different style, set a [emphasisMarker] to one of the values
 * specified in [EmphasisMarker]:
 * ```
 * markdown {
 *     italic("text", EmphasisMarker.Asterisks)
 * }
 * ```
 * That produces:
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

/** Creates inlined piece of italic text **/
@JvmName("toItalic")
fun String.italic(
    emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks
) = Italic(this, emphasisMarker).toMarkdown()

/** Creates inlined piece of italic text **/
fun italic(
    content: String,
    emphasisMarker: EmphasisMarker = EmphasisMarker.Asterisks
) = Italic(content, emphasisMarker).toMarkdown()

/** Creates inlined piece of italic text **/
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
 *     bold("text")
 *
 *    bold {
 *       """
 *       |Multi-line italic text, that has to be broken into multiple lines
 *       |in order to fit editor code width limits for example.
 *       """.trimMargin()
 *     }
 * }
 * ```
 * That produces:
 *```
 * **text**
 *
 * **Multi-line text, that has to be broken into multiple lines in order to fit editor code width limits for example.**
 *```
 *
 * Bold can also be inlined with any other text element, for example:
 * ```
 * markdown {
 *    line("An example of" + bold("Inlined bold") + " text")
 * }
 * ```
 * That produces:
 * ```
 * An example of **Inlined bold** text
 * ```
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

/** Creates inlined piece of bold text **/

@JvmName("toBold")
fun String.bold(
    emphasisMarker: EmphasisMarker = EmphasisMarker.Underscore
) = Bold(this, emphasisMarker).toMarkdown()

/** Creates inlined piece of bold text **/
fun bold(
    content: String,
    emphasisMarker: EmphasisMarker = EmphasisMarker.Underscore
) = Bold(content, emphasisMarker).toMarkdown()

/** Creates inlined piece of bold text **/
fun bold(
    content: () -> String,
    emphasisMarker: EmphasisMarker = EmphasisMarker.Underscore
) = Bold(content(), emphasisMarker).toMarkdown()

/**
 * ## [Code Span](https://daringfireball.net/projects/markdown/syntax#code)
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     codeSpan("text")
 * }
 * ```
 * That produces:
 * ```
 * `text`
 * ```
 *
 * Code span can also be inlined with any other text element, for example:
 *
 * ```
 * markdown {
 *    line("An example of" + codeSpan("Inlined code"))
 * }
 * ```
 * That produces:
 * ```
 * An example of `Inlined code`
 * ```
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

/** Creates inlined code span **/
@JvmName("toCodeSpan")
fun String.codeSpan() = CodeSpan(this).toMarkdown()

/** Creates inlined code span **/
fun codeSpan(content: String) = CodeSpan(content).toMarkdown()

/** Creates inlined code span **/
fun codeSpan(content: () -> String) = CodeSpan(content()).toMarkdown()

/**
 * ## [Link](https://daringfireball.net/projects/markdown/syntax#link)
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     //Can be a link or a local path to the file
 *     link("text", "https://example.com")
 * }
 * ```
 * That will produce:
 * ```
 * [text](https://example.com)
 *```
 *
 * Link can also be inlined with any other text element, for example:
 * ```
 * markdown {
 *    line("An example of" + link("Inlined link", "https://example.com"))
 * }
 * ```
 * That produces:
 * ```
 * An example of [Inlined link](https://example.com)
 * ```
 *
 * <br></br>
 *
 * @param text Textual content of this element
 * @param path Local path to the reference or a URL
 * @param title Optional Title of this element
 */
internal class Link(
    private val text: String,
    private val path: String,
    private val title: String = ""
) : MarkdownElement() {

    override fun toMarkdown(): String = "[$text]($path $title)"
}

/** Creates inlined link **/
@JvmName("toLink")
fun String.link(path: String, title: String = "") = Link(this, path, title).toMarkdown()

/** Creates inlined link **/
fun link(text: String, path: String, title: String = "") = Link(text, path, title).toMarkdown()

/** Creates inlined link **/
fun link(content: () -> Triple<String, String, String>) {
    val contentTriple = content()
    Link(
        contentTriple.first,
        contentTriple.second,
        contentTriple.third
    ).toMarkdown()
}

/**
 * ## [Image](https://daringfireball.net/projects/markdown/syntax#img)
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     //Can be a link or a local path to the file
 *     image("text", "https://example.com")
 * }
 * ```
 * That produces:
 * ```
 * ![text](https://example.com)
 *```
 *
 * <br></br>
 *
 * @param text Textual content of this element
 * @param path Local path to the reference or a URL
 * @param title Optional Title of this element
 */
internal class Image(
    private val text: String,
    private val path: String,
    private val title: String = ""
) : MarkdownElement() {

    override fun toMarkdown(): String = "![$text]($path $title)"
}

/** Creates inlined image **/
@JvmName("toImage")
fun String.image(path: String, title: String = "") = Image(this, path, title).toMarkdown()

/** Creates inlined image **/
fun image(text: String, path: String, title: String = "") = Image(text, path, title).toMarkdown()

/** Creates inlined link **/
fun image(content: () -> Triple<String, String, String>) {
    val contentTriple = content()
    Image(
        contentTriple.first,
        contentTriple.second,
        contentTriple.third
    ).toMarkdown()
}

/**
 * Marker interface representing *parent* [element builders][ElementBuilder]
 * that should support [TextLine], [Bold], [Italic], [CodeSpan], [Link],[Image] as their nested elements.
 *
 * Implementations of this interface get all the idiomatic extensions registered
 * to the context of [TextLineContainerBuilder].
 *
 * Default implementation simply adds TextSpan element to the list of nested elements, which should be enough for
 * most of the parent implementations.
 */
interface TextLineContainerBuilder : ElementContainerBuilder {
    fun line(content: String) {
        addToContainer(TextLine(content))
    }

    fun bold(content: String) {
        addToContainer(Bold(content, EmphasisMarker.Underscore))
    }

    fun italic(content: String) {
        addToContainer(Italic(content, EmphasisMarker.Asterisks))
    }

    fun codeSpan(content: String) {
        addToContainer(CodeSpan(content))
    }

    fun link(text: String, url: String, title: String = "") {
        addToContainer(Link(text, url, title))
    }

    fun image(text: String, url: String, title: String = "") {
        addToContainer(Image(text, url, title))
    }
}

/** Constructs a new simple text line **/
inline fun TextLineContainerBuilder.line(
    content: () -> String
) {
    line(content())
}

/** Constructs a new simple text line **/
inline fun TextLineContainerBuilder.bold(
    content: () -> String
) {
    bold(content())
}

/** Constructs a new simple text line **/
inline fun TextLineContainerBuilder.italic(
    content: () -> String
) {
    italic(content())
}

