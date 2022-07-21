package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class HorizontalRuleTest {

    @Test
    fun `horizontal rule produces valid markdown horizontal rule`() {
        val actual = markdown {
            horizontalRule()
        }.content

        @Language("Markdown")
        val expected = "---"

        assertEquals(expected, actual)
    }

    @Test
    fun `multiple horizontal rules are divided by a single new line`() {
        val actual = markdown {
            horizontalRule()
            horizontalRule()
        }.content

        @Language("markdown")
        val expected =
            """
            |---
            |
            |---
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `horizontal rule with custom style uses correct tag`(){
        val actual = markdown {
            horizontalRule(style = HorizontalRuleTag.Asterisks)
            horizontalRule(style = HorizontalRuleTag.Hyphen)
            horizontalRule(style = HorizontalRuleTag.Underscore)
        }.content

        @Language("markdown")
        val expected =
            """
            |***
            |
            |---
            |
            |___
            """.trimMargin()

        assertEquals(expected, actual)
    }
}