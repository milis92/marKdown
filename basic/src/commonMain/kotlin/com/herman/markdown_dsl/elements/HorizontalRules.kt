package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementBuilder
import com.herman.markdown_dsl.ElementContainerBuilder
import com.herman.markdown_dsl.MarkdownBuilder
import com.herman.markdown_dsl.MarkdownElement

enum class HorizontalRuleTag(internal val tag: String) {
    Hyphen("-"),
    Asterisks("*"),
    Underscore("_")
}

/**
 * ## [Horizontal Rule](https://daringfireball.net/projects/markdown/syntax#hr)
 *
 *
 * <br></br>
 *
 * ### Usage:
 *
 * ```
 * markdown {
 *     horizontalRule()
 * }
 * ```
 * That will produce:
 *```
 * ---
 *```
 *
 * By default, rule will be created with [hyphen tag][HorizontalRuleTag.Hyphen].
 * If you want to use a different tag, set a [tag] to one of the values
 * specified in [HorizontalRuleTag]:
 * ```
 * markdown {
 *     horizontalRule(HorizontalRuleStyle.Hyphen)
 *     horizontalRule(HorizontalRuleStyle.Asterisks)
 *     horizontalRule(HorizontalRuleStyle.Underscore)
 * }
 * ```
 * Will produce:
 * ```
 * ---
 *
 * ***
 *
 * ___
 *```
 *
 *
 * @param tag tag for this horizontal rule
 */
internal class HorizontalRule(
    private val tag: HorizontalRuleTag
) : MarkdownElement() {

    private val requiredNumberOfTags = 3

    override fun toMarkdown(): String = buildString {
        repeat(requiredNumberOfTags) {
            append(tag.tag)
        }
    }
}

/**
 * Marker interface representing *parent* [element builders][ElementBuilder]
 * that should support [HorizontalRule] element as their nested elements.
 *
 * Implementations of this interface get all the idiomatic extensions registered
 * to the context of [HorizontalRuleContainerBuilder].
 *
 * Default implementation adds [HorizontalRule] to the list of nested elements,
 * which should be enough for most of the parents.
 */
interface HorizontalRuleContainerBuilder : ElementContainerBuilder {
    fun horizontalRule(style: HorizontalRuleTag = HorizontalRuleTag.Hyphen) {
        addToContainer(HorizontalRule(style))
    }
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
fun MarkdownBuilder.horizontalRule(
    style: HorizontalRuleTag = HorizontalRuleTag.Hyphen
) = horizontalRule(style)