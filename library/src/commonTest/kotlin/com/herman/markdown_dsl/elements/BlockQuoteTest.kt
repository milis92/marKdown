package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BlockQuoteTest {

    @Test
    fun `when blockquote contains simple text than output blockquote is prefixed with correct tag`() {
        val actual = markdown {
            blockQuote("text")
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |> text
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when blockquote contains multiple text elements than output blockquote is properly separated`() {
        val actual = markdown {
            blockQuote {
                text(content = "text")
                text(content = "text")
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |> text
            |> text
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when blockquote contains text element with multiple lines than output blockquote is properly separated`() {
        val actual = markdown {
            blockQuote {
                text {
                    """
                    |First line.
                    |Second line.
                    """.trimMargin()
                }
                text(content = "text")
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |> First line.
            |> Second line.
            |> text
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when blockquote contains multiple paragraphs than output blockquote  is properly separated`() {
        val actual = markdown {
            blockQuote {
                paragraph {
                    text { "text" }
                    text { "text" }
                }
                paragraph {
                    text { "text" }
                    text { "text" }
                }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |> text  
            |> text  
            |>
            |> text  
            |> text  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when multiple blockquote elements are used than output blockquote  is properly separated`() {
        val actual = markdown {
            blockQuote {
                paragraph {
                    text { "text" }
                    text { "text" }
                }
                paragraph {
                    text { "text" }
                    text { "text" }
                }
            }

            blockQuote {
                paragraph {
                    text { "text" }
                    text { "text" }
                }
                paragraph {
                    text { "text" }
                    text { "text" }
                }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |> text  
            |> text  
            |>
            |> text  
            |> text  
            |
            |
            |> text  
            |> text  
            |>
            |> text  
            |> text  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when blockquote contains nested blockquotes than output blockquote is properly indented`() {
        val actual = markdown {
            blockQuote {
                blockQuote {
                    text(content = "text")
                    text(content = "text")
                }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |> > text
            |> > text
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when blockquote contains nested blockquotes with paragraph than output blockquote is properly indented`() {
        val actual = markdown {
            blockQuote {
                blockQuote {
                    paragraph {
                        text { "text" }
                        text { "text" }
                    }
                    paragraph {
                        text { "text" }
                        text { "text" }
                    }
                }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |> > text  
            |> > text  
            |> >
            |> > text  
            |> > text  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }
}