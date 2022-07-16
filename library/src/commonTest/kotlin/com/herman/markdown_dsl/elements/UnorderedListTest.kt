package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UnorderedListTest {

    @Test
    fun `when list contains paragraphs than items in the output are properly indented`() {
        val actual = markdown {
            unorderedList {
                paragraph {
                    text { "First item" }
                    text { "Second line" }
                }
                text { "Second item" }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |*  First item  
            |   Second line  
            |   
            |*  Second item
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when list contains sublist than items in the output are properly indented`() {
        val actual = markdown {
            unorderedList {
                paragraph {
                    text { "First item" }
                    text { "Second line" }
                    unorderedList {
                        text { "First sub item" }
                        text { "Second sub item" }
                    }
                }
                text { "Second item" }
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
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when list is inside a blockquote than items in the output are properly indented`() {
        val actual = markdown {
            blockQuote {
                unorderedList {
                    text { "First item" }
                    text { "Second item" }
                }
                text { "Second sentence" }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |> *  First item
            |> *  Second item
            |>
            |> Second sentence
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }
}