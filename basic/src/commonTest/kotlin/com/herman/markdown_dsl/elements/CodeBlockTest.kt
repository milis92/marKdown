package com.herman.markdown_dsl.elements

import com.herman.markdown_dsl.markdown
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CodeBlockTest {

    @Test
    fun `code block properly indents content`() {
        val actual = markdown {
            codeBlock {
                @Language("kotlin")
                val code =
                    """
                    |fun main() {
                    |    println("Hello, World!")
                    |}
                    """.trimMargin()
                code
            }
        }.content

        @Language("kotlin")
        val expected =
            """
            |    fun main() {
            |        println("Hello, World!")
            |    }
            """.trimMargin()

        assertEquals(expected, actual)
    }

    @Test
    fun `blockquote with nested code block produces valid markdown blockquote`(){
        val actual = markdown {
            blockQuote {
                codeBlock {
                    @Language("kotlin")
                    val code =
                        """
                        |fun main() {
                        |    println("Hello, World!")
                        |}
                        """.trimMargin()
                    code
                }
            }
        }.content

        @Language("markdown")
        val expected =
            """
            |>     fun main() {
            |>         println("Hello, World!")
            |>     }
            """.trimMargin()

        assertEquals(expected, actual)
    }
}