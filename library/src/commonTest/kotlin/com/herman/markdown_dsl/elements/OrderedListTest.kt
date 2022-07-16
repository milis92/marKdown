package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class OrderedListTest {

    @Test
    fun `when list contais multiple items than output contains properly formatted list`() {
        val numberOfItems = 100
        val actual = markdown {
            orderedList {
                repeat(numberOfItems) {
                    text { "Item" }
                }

            }
        }.content

        @Language("markdown")
        val expected = buildString {
            repeat(numberOfItems) {
                append(it + 1)
                append(".")
                append("  ")
                append("Item")
                appendLine()
            }
        }

        assertEquals(expected, actual)
    }

    @Test
    fun `when list contains paragraphs than items in the output are properly indented`() {
        val actual = markdown {
            orderedList {
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
            |1.  First item  
            |    Second line  
            |
            |2.  Second item
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when list contains a ordered sublist than items in the output are properly indented`() {
        val actual = markdown {
            orderedList {
                paragraph {
                    text { "First item" }
                    orderedList {
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
            |1.  First item  
            |    1.  First sub item
            |    2.  Second sub item  
            |
            |2.  Second item
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when list contains a unordered sublist than items in the output are properly indented`() {
        val actual = markdown {
            orderedList {
                paragraph {
                    text { "First item" }
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
            |1.  First item  
            |    *  First sub item
            |    *  Second sub item  
            |
            |2.  Second item
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }


    @Test
    fun `when list is inside a blockquote than items in the output are properly indented`() {
        val actual = markdown {
            blockQuote {
                orderedList {
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
            |> 1.  First item
            |> 2.  Second item
            |>
            |> Second sentence
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }


}