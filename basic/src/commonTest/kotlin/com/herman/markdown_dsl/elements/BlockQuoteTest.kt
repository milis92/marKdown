package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BlockQuoteTest {

    @Test
    fun `blockquote with single line produces valid markdown blockquote`() {
        val actual = markdown {
            blockQuote("text")
        }.content

        @Language("markdown")
        val expected =
            """
            |> text
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `multiple blockquotes are separated by a blank line`() {
        val actual = markdown {
            blockQuote("Blockquote 1")
            blockQuote("Blockquote 2")
        }.content

        @Language("markdown")
        val expected =
            """
            |> Blockquote 1
            |
            |> Blockquote 2
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `blockquote with nested blockquote prefixes both with blockquote tag`() {
        val actual = markdown {
            blockQuote {
                blockQuote("Blockquote 1")
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |> > Blockquote 1
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `blockquote with other markdown elements produces valid markdown blockquote`(){
        val actual = markdown {
            blockQuote {
                heading("Heading 1")
                underlinedHeading("Underlined Heading")
                horizontalRule()
                orderedList(listOf("Item 1", "Item 2"))
                unorderedList(listOf("Item 1", "Item 2"))
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |> # Heading 1
            |>
            |> Underlined Heading
            |> ==================
            |>
            |> ---
            |>
            |> 1.  Item 1
            |> 2.  Item 2
            |>
            |> *  Item 1
            |> *  Item 2
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `blockquote with paragraph prefixes each paragraph line with blockquote tag`() {
        val actual = markdown {
            blockQuote {
                paragraph(listOf("Line 1", "Line 2"))
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |> Line 1  
            |> Line 2
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `blockquote with multiple paragraphs prefixes line between paragraphs with blockquote tag`() {
        val actual = markdown {
            blockQuote {
                paragraph(listOf("Line 1", "Line 2"))
                paragraph(listOf("Line 1", "Line 2"))
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |> Line 1  
            |> Line 2
            |>
            |> Line 1  
            |> Line 2
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `blockquote with nested blockquotes prefixes line between paragraphs with only parent blockquote tag`() {
        val actual = markdown {
            blockQuote {
                blockQuote {
                    line("Line 1")
                }
                blockQuote {
                    line("Line 2")
                }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |> > Line 1
            |>
            |> > Line 2
            """.trimMargin()
        assertEquals(expected, actual)
    }
}