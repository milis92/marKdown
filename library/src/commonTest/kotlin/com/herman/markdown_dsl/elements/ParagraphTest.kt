package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ParagraphTest {

    @Test
    fun `paragraph with single line ends line with paragraph line break`() {
        val actual = markdown {
            paragraph("Line 1")
        }.content

        @Language("markdown")
        val expected =
            """
            |Line 1
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `paragraph with multiple lines ends every line with paragraph line break`() {
        val actual = markdown {
            paragraph {
                line("Line 1")
                line("Line 2")
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |Line 1  
            |Line 2
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `multiple paragraphs are separated by a blank line`() {
        val actual = markdown {
            paragraph("Paragraph 1")
            paragraph("Paragraph 2")
        }.content

        @Language("markdown")
        val expected =
            """
            |Paragraph 1  
            |
            |Paragraph 2
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `paragraph properly sanitises invalid content`() {
        val actual = markdown {
            paragraph {
                line {
                    """|Line 1 ending with paragraph line break  
                       |
                   """.trimMargin()
                }
                line {
                    """|
                       |Line 2 starts with new line
                   """.trimMargin()
                }
                line {
                    ""
                }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |Line 1 ending with paragraph line break  
            |Line 2 starts with new line
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `all paragraph apis produce valid markdown paragraph`() {
        val actual = markdown {
            paragraph("Line 1")
            paragraph(listOf("Line 1", "Line 2"))
            paragraph {
                line("Line 1")
                line("Line 2")
            }
            paragraph {
                line { "Line 3" }
                line { "Line 3" }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |Line 1  
            |
            |Line 1  
            |Line 2  
            |
            |Line 1  
            |Line 2  
            |
            |Line 3  
            |Line 3
            """.trimMargin()

        assertEquals(expected, actual)
    }
}