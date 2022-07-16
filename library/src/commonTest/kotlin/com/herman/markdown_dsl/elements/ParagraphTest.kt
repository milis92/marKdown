package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ParagraphTest {

    @Test
    fun `when text element is added than output text is a single line`() {
        val actual = markdown {
            text { "text" }
        }.content

        @Language("markdown")
        val expected =
            """
            |text
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when multiple texts elements are added than output text is in multiple lines`() {
        val actual = markdown {
            text(content = "text")
            bold { "bold" }
            italic { "italic" }
            boldItalic { "boldItalic" }
        }.content

        @Language("markdown")
        val expected =
            """
            |text
            |**bold**
            |_italic_
            |***boldItalic***
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when bold and italics are used than output is properly formatted`() {
        val actual = markdown {
            bold { "bold" }
            italic { "italic" }
            boldItalic { "boldItalic" }
        }.content

        @Language("markdown")
        val expected =
            """
            |**bold**
            |_italic_
            |***boldItalic***
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when bold and italics are used in paragraph than output is properly formatted`() {
        val actual = markdown {
            paragraph {
                bold { "bold" }
                italic { "italic" }
                boldItalic { "boldItalic" }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |**bold**  
            |_italic_  
            |***boldItalic***  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when paragraph element with single text is added than output is valid paragraph`() {
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
    fun `when paragraph element with multiple lines is added than output is a single paragraph with 2 lines`() {
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
    fun `when multiple paragraphs elements are added than output is multiple valid paragraphs`() {
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
    fun `when multiple complex paragraphs are added than than output contains multiple paragraphs`() {
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
    fun `when paragraph with multiline text is added than output contains multiple paragraphs`() {
        val actual = markdown {
            paragraph {
                text {
                    """
                                |First line.
                                |Second line.
                                """.trimMargin()
                }
                text { "Third line." }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |First line.
            |Second line.  
            |Third line.  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }
}