package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TextSpansTest {

    @Test
    fun `line text spans produce correct markdown`() {
        val actual = markdown {
            line("This is a line")
            italic("This is italic")
            bold("This is bold")
            codeSpan("This is code")
        }.content

        @Language("Markdown")
        val expected =
            """
            |This is a line
            |
            |_This is italic_
            |
            |**This is bold**
            |
            |`This is code`
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `inline text spans produce correct markdown`() {
        val actual = markdown {
            line {
                "This is " + "italic".italic() + " and " + "bold".bold() + " and " + "code".codeSpan()
            }
        }.content

        @Language("Markdown")
        val expected =
            """
            |This is _italic_ and **bold** and `code`
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `inline bold and italic spans with custom marker use correct tag`() {
        val actual = markdown {
            line {
                "This is " +
                    "italic".italic(EmphasisMarker.Asterisks) + " and " + "bold".bold(EmphasisMarker.Underscore)
            }
        }.content

        @Language("Markdown")
        val expected =
            """
            |This is *italic* and __bold__
            """.trimMargin()

        assertEquals(expected, actual)
    }
}