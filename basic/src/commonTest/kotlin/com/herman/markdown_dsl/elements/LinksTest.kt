package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.junit.jupiter.api.Test
import java.net.URI
import kotlin.test.assertEquals

internal class LinksTest {

    @Test
    fun `link with url produces correct markdown`() {
        val actual = markdown {
            link("Google", URI("https://www.google.com"))
        }.toString()

        val expected =
            """
            |[Google](https://www.google.com)
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `image with url produces correct markdown`() {
        val actual = markdown {
            image("Google", URI("https://www.google.com"))
        }.toString()

        val expected =
            """
            |![Google](https://www.google.com)
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `link with reference produces correct markdown`() {
        val actual = markdown {
            link("Link", URI("../README.MD"))
        }.toString()

        val expected =
            """
            |[Link](../README.MD)
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `image with reference produces correct markdown`() {
        val actual = markdown {
            image("Link", URI("../README.MD"))
        }.toString()

        val expected =
            """
            |![Link](../README.MD)
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `line with inline link produces correct span`() {
        val actual = markdown {
            line {
                "This is a link: " + "Google".link(URI("https://www.google.com"))
            }
        }.toString()

        val expected =
            """
            |This is a link: [Google](https://www.google.com)
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `line with inline image produces correct span`() {
        val actual = markdown {
            line {
                "This is an image: " + "Google".image(URI("https://www.google.com"))
            }
        }.toString()

        val expected =
            """
            |This is an image: ![Google](https://www.google.com)
            """.trimMargin()

        assertEquals(expected, actual)
    }
}