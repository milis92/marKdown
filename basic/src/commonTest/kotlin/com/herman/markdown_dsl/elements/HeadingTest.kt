package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HeadingTest {

    @Test
    fun `heading produces valid heading markdown`() {
        val actual = markdown {
            heading("Heading")
        }.content

        @Language("markdown")
        val expected =
            """
            |# Heading
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `underlined heading produces valid heading markdown`() {
        val actual = markdown {
            underlinedHeading("Heading 1")
        }.content

        @Language("markdown")
        val expected =
            """
            |Heading 1
            |=========
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `heading with multiple lines produces markdown heading with a single line`() {
        val actual = markdown {
            heading {
                """
                |Heading 1
                |
                |Heading 2
                |
                |Heading 3
                """.trimMargin()
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |# Heading 1 Heading 2 Heading 3
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `underlined heading with multiple lines produces markdown heading with a single line`() {
        val actual = markdown {
            underlinedHeading {
                """
                |Heading 1
                |
                |Heading 2
                |
                |Heading 3
                """.trimMargin()
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |Heading 1 Heading 2 Heading 3
            |=============================
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `heading with custom style produces markdown heading with correct tag`() {
        val actual = markdown {
            heading("Heading", HeadingTag.H1)
            heading("Heading", HeadingTag.H2)
            heading("Heading", HeadingTag.H3)
            heading("Heading", HeadingTag.H4)
            heading("Heading", HeadingTag.H5)
            heading("Heading", HeadingTag.H6)
        }.content

        @Language("markdown")
        val expected =
            """
            |# Heading
            |
            |## Heading
            |
            |### Heading
            |
            |#### Heading
            |
            |##### Heading
            |
            |###### Heading
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `underlined with custom style produces markdown heading with correct tag`() {
        val actual = markdown {
            underlinedHeading("Heading", UnderlinedHeadingTag.H1)
            underlinedHeading("Heading", UnderlinedHeadingTag.H2)
        }.content

        @Language("markdown")
        val expected =
            """
            |Heading
            |=======
            |
            |Heading
            |-------
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `all heading apis produce valid markdown heading`() {
        val actual = markdown {
            heading("Heading")
            heading { "Heading" }
            underlinedHeading("Heading")
            underlinedHeading { "Heading" }
        }.content

        @Language("markdown")
        val expected =
            """
            |# Heading
            |
            |# Heading
            |
            |Heading
            |=======
            |
            |Heading
            |=======
            """.trimMargin()

        assertEquals(expected, actual)
    }
}