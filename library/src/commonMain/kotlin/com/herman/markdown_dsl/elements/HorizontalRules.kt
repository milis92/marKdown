package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.ElementContainerBuilder
import com.herman.markdown_dsl.MarkdownBuilder
import com.herman.markdown_dsl.MarkdownElement

enum class HorizontalRuleStyle(internal val tag: String) {
    Hyphen("-"),
    Asterisks("*"),
    Underscore("_")
}

/**
 * ## [Horizontal Rule](https://daringfireball.net/projects/markdown/syntax#hr)
 *
 * To ensure correctness and compatibility, every rule will be seperated
 * from previous and following content by a new line.
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
 *
 * ---
 *
 *```
 * _Note the blank lines before and after the rule_
 *
 * <br></br>
 *
 * By default, rule will be created with [hyphen tag][HorizontalRuleStyle.Hyphen].
 * If you want to use a different style, set a [style] to one of the values
 * specified in [HorizontalRuleStyle]:
 * ```
 * markdown {
 *     horizontalRule(HorizontalRuleStyle.Hyphen)
 *     horizontalRule(HorizontalRuleStyle.Asterisks)
 *     horizontalRule(HorizontalRuleStyle.Underscore)
 * }
 * ```
 * Will produce:
 * ```
 *
 * ---
 *
 *
 * ***
 *
 *
 * ___
 *
 *```
 *
 * <br></br>
 *
 * @param style tag for this horisontal rule
 */
internal class HorizontalRule(
    private val style: HorizontalRuleStyle
) : MarkdownElement() {

    private val requiredNumberOfTags = 3

    override fun toMarkdown(): String = buildString {
        appendLine()
        repeat(requiredNumberOfTags) {
            append(style.tag)
        }
        appendLine()
    }
}

interface HorizontalRuleContainerBuilder : ElementContainerBuilder {
    fun horizontalRule(style: HorizontalRuleStyle = HorizontalRuleStyle.Hyphen) {
        addToContainer(HorizontalRule(style))
    }
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
fun MarkdownBuilder.horizontalRule(
    style: HorizontalRuleStyle = HorizontalRuleStyle.Hyphen
) = horizontalRule(style)