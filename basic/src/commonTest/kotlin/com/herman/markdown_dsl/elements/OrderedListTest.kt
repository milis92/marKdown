package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class OrderedListTest {

    @Test
    fun `list with many items indents all items properly`() {
        val numberOfItems = 100
        val actual = markdown {
            orderedList {
                repeat(numberOfItems) {
                    item("Item")
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
        }.trim()

        assertEquals(expected, actual)
    }

    @Test
    fun `ordered list with paragraph indents paragraphs properly`() {
        val actual = markdown {
            orderedList {
                item {
                    paragraph {
                        line { "First item" }
                        line { "Second line" }
                    }
                }
                item("Second item")
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |1.  First item  
            |    Second line  
            |
            |2.  Second item
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `ordered list with a ordered sublist indents sublist properly`() {
        val actual = markdown {
            orderedList {
                item {
                    line("First item")
                    orderedList {
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
            |1.  First item
            |    1.  First sub item
            |    2.  Second sub item
            |
            |2.  Second item
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `ordered list with a unordered sublist indents sublist properly`() {
        val actual = markdown {
            orderedList {
                item {
                    paragraph {
                        line { "First item" }
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
            |1.  First item  
            |    *  First sub item
            |    *  Second sub item
            |
            |2.  Second item
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `ordered list with blockquote indents blockquote properly`() {
        val actual = markdown {
            blockQuote {
                orderedList {
                    item("First item")
                    item("Second item")
                }
                line("Second sentence")
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |> 1.  First item
            |> 2.  Second item
            |>
            |> Second sentence
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `ordered list`(){
        val actual = markdown {
            blockQuote {
                orderedList {
                    item("First item")
                    item("Second item")
                }
                line("Second sentence")
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |> 1.  First item
            |> 2.  Second item
            |>
            |> Second sentence
            """.trimMargin()

        assertEquals(expected, actual)
    }
}