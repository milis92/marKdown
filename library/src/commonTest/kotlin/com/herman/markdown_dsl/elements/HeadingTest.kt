package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HeadingTest {

    @Test
    fun `when heading element is used than output heading is prefixed with correct tag`() {
        val actual = markdown {
            heading { "Heading" }
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |# Heading 
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when heading element is after other element then output heading is separated by a new line`() {
        val actual = markdown {
            heading { "Heading 1" }
            heading { "Heading 2" }
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |# Heading 1 
            |
            |# Heading 2 
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when heading element contains text with multiple lines then output heading is single line`() {
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
            |
            |# Heading 1 Heading 2 Heading 3 
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when heading with custom style is used than markdown applies a proper tag`() {
        val actual = markdown {
            heading(HeadingSizeMarker.H1) { "Heading" }
            heading(HeadingSizeMarker.H2) { "Heading" }
            heading(HeadingSizeMarker.H3) { "Heading" }
            heading(HeadingSizeMarker.H4) { "Heading" }
            heading(HeadingSizeMarker.H5) { "Heading" }
            heading(HeadingSizeMarker.H6) { "Heading" }
        }.content

        @Language("markdown")
        val expected =
            """
            |
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
    fun `when underlinedHeading element is used than output heading is underlined with correct tag`() {
        val actual = markdown {
            underlinedHeading { "Heading" }
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |Heading 
            |=======
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when underlinedHeading element is after other element then output heading is separated by a new line`() {
        val actual = markdown {
            underlinedHeading { "Heading 1" }
            underlinedHeading { "Heading 2" }
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |Heading 1 
            |=========
            |
            |Heading 2 
            |=========
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when underlinedHeading element contains text with multiple lines then output heading is single line`() {
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
            |
            |Heading 1 Heading 2 Heading 3 
            |=============================
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `when underlinedHeading with custom style is used than markdown applies a proper tag`() {
        val actual = markdown {
            underlinedHeading(UnderlinedHeadingStyle.H1) { "Heading 1" }
            underlinedHeading(UnderlinedHeadingStyle.H2) { "Heading 2" }
        }.content

        @Language("markdown")
        val expected =
            """
            |
            |Heading 1 
            |=========
            |
            |Heading 2 
            |---------
            """.trimMargin()

        assertEquals(expected, actual)
    }
}