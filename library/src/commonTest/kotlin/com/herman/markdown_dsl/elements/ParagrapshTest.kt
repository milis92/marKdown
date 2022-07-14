package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ParagrapshTest {

    @Test
    fun `when single text is added than markdown prints a single line`() {
        val actual = markdown {
            text("text")
        }.content

        @Language("markdown")
        val expected =
            """
            |text
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when multiple texts are added than markdown prints a multiple lines`() {
        val actual = markdown {
            text("text")
            text("text")
        }.content

        @Language("markdown")
        val expected =
            """
            |text
            |text
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when paragraph with single text is added than markdown prints a single paragraph`() {
        val actual = markdown {
            paragraph {
                text { "text" }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |text  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when paragraph with multiple lines is added than markdown prints a single paragraph with 2 lines`() {
        val actual = markdown {
            paragraph {
                text { "First sentence." }
                text { "Second sentence." }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |First sentence.  
            |Second sentence.  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when multiple simple paragraphs added than markdown prints multiple paragraphs`() {
        val actual = markdown {
            paragraph {
                text { "First paragraph" }
            }
            paragraph {
                text { "Second paragraph" }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |First paragraph  
            |
            |Second paragraph  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when multiple complex paragraphs added than markdown prints multiple paragraphs`() {
        val actual = markdown {
            paragraph {
                text { "First line" }
                text { "Second line" }
            }
            paragraph {
                text { "Fourth line" }
                text { "Fifth line" }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |First line  
            |Second line  
            |
            |Fourth line  
            |Fifth line  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when bold and italics are used than markdown uses proper formatting`() {
        val actual = markdown {
            bold { "Bold" }
            italic("Italic")
        }.content

        @Language("markdown")
        val expected =
            """
            |**Bold**
            |_Italic_
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when bold and italics are used in paragraph than markdown uses proper formatting`() {
        val actual = markdown {
            paragraph {
                bold { "Bold" }
                italic { "Italic" }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |**Bold**  
            |_Italic_  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }
}