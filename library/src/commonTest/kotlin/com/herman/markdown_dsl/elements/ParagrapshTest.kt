package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ParagrapshTest {

    @Test
    fun `when single text is added than markdown prints a single line`() {
        val actual = markdown {
            text("text")
        }.content

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

        val expected =
            """
            |text
            |text
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when paragraph with single text is added than markdown prints a single paragraph`(){
        val actual = markdown {
            paragraph {
                text { "text" }
            }
        }.content

        val expected =
            """
            |text  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when paragraph with multiple lines is added than markdown prints a single paragraph with 2 lines`(){
        val actual = markdown {
            paragraph {
                text { "First sentence." }
                text { "Second sentence" }
            }
        }.content

        val expected =
            """
            |First sentence.  
            |Second sentence  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when multiple simple paragraphs added than markdown prints multiple paragraphs`(){
        val actual = markdown {
            paragraph {
                text { "First paragraph" }
            }
            paragraph {
                text { "Second paragraph" }
            }
        }.content

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
    fun `when multiple complex paragraphs added than markdown prints multiple paragraphs`(){
        val actual = markdown {
            paragraph {
                text { "First paragraph" }
                text { "First paragraph" }
            }
            paragraph {
                text { "Second paragraph" }
                text { "Second paragraph" }
            }
        }.content

        val expected =
            """
            |First paragraph  
            |First paragraph  
            |
            |Second paragraph  
            |Second paragraph  
            |
            """.trimMargin()

        assertEquals(expected, actual)
    }
}