package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UnorderedListTest {

    @Test
    fun `unordered list with paragraph indents paragraphs properly`() {
        val actual = markdown {
            unorderedList {
                item {
                    paragraph {
                        line("First item")
                        line("Second line")
                    }
                }
                item("Second item")
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |*  First item  
            |   Second line  
            |
            |*  Second item
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `unordered list with a unordered sublist indents sublist properly`() {
        val actual = markdown {
            unorderedList {
                item {
                    paragraph {
                        line("First item")
                        line("Second line")
                    }
                    unorderedList {
                        item("First sub item")
                        item("Second sub item")
                    }
                }
                item("Second item")
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |*  First item  
            |   Second line  
            |   *  First sub item
            |   *  Second sub item
            |
            |*  Second item
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `unordered list with blockquote indents blockquote properly`() {
        val actual = markdown {
            blockQuote {
                unorderedList {
                    item("First item")
                    item("Second item")
                }
                line("Second sentence")
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |> *  First item
            |> *  Second item
            |>
            |> Second sentence
            """.trimMargin()

        assertEquals(expected, actual)
    }
}