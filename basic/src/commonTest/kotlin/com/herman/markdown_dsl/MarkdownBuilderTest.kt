package com.herman.markdown_dsl

import com.herman.markdown_dsl.elements.HeadingTag
import com.herman.markdown_dsl.elements.blockQuote
import com.herman.markdown_dsl.elements.codeBlock
import com.herman.markdown_dsl.elements.heading
import com.herman.markdown_dsl.elements.line
import com.herman.markdown_dsl.elements.paragraph
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

@Disabled("TODO - Raw html and links are not supported yet")
internal class MarkdownBuilderTest {

    @Test
    fun test() {
        val markdown = markdown {
            //TODO Insert raw html content

            line {
                """
                |A Kotlin DSL for effortless creation of beautiful Markdown pages,
                |supporting both basic and extended syntax (Coming soon™️).
                """.trimMargin()
            }

            "An example of" + italic("Inlined italic") + " text"

            horizontalRule()

            heading(HeadingTag.H2) { "☁️ Setup" }

            blockQuote("Coming soon")

            heading(HeadingTag.H2) { "\uD83D\uDCDD Usage" }

            heading(HeadingTag.H3) { "Basic syntax" }

            blockQuote {
                paragraph {
                    line {
                        """
                        |Basic implementation follows
                        |[official Markdown syntax](https://daringfireball.net/projects/markdown/syntax)
                        |and should be supported by almost all Markdown processors.
                        """.trimMargin()
                    }
                    //TODO Provide api to insert links and other in-line styles
                    line { "For detailed guide and more usage examples refer to Wiki." }
                }
            }

            heading(HeadingTag.H6) { "This readme page created with Kotlin DSL Markdown" }

            codeBlock {
                @Language("kotlin")
                val block =
                    """
                    |val markdown = markdown {
                    |    //TODO Insert raw html content
                    |
                    |    line {
                    |        ""${'"'}
                    |        |A Kotlin DSL for effortless creation of beautiful Markdown pages, 
                    |        |supporting both basic and extended syntax (Coming soon™️).
                    |        ""${'"'}.trimMargin()
                    |    }
                    |
                    |    horizontalRule()
                    |
                    |    heading(HeadingStyleMarker.H2) { "☁️ Setup" }
                    |
                    |    blockQuote("Coming soon")
                    |
                    |    heading(HeadingStyleMarker.H2) { "\uD83D\uDCDD Usage" }
                    |
                    |    heading(HeadingStyleMarker.H3) { "Basic syntax" }
                    |
                    |    blockQuote {
                    |        paragraph {
                    |            line {
                    |                ""${'"'}
                    |                |Basic implementation follows 
                    |                |[official Markdown syntax](https://daringfireball.net/projects/markdown/syntax) 
                    |                |and should be supported by almost all Markdown processors.
                    |                ""${'"'}.trimMargin()
                    |            }
                    |            //TODO Provide api to insert links and other in-line styles
                    |            line { "For detailed guide and more usage examples refer to Wiki." }
                    |        }
                    |    }
                    |
                    |    heading(HeadingStyleMarker.H4) { "This readme page created with Kotlin DSL Markdown" }
                    |    //TODO Insert code block
                    |
                    |    heading(HeadingStyleMarker.H4) { "Exports" }
                    |
                    |    horizontalRule()
                    |
                    |    line {
                    |        ""${'"'}
                    |        |This library is licensed under the Apache Version 2.0 License
                    |        |- see the [License](LICENSE.txt) file for details.
                    |        ""${'"'}.trimMargin()
                    |    }
                    |}
                    """.trimMargin()
                block
            }

            heading(HeadingTag.H4) { "Exports" }

            horizontalRule()

            line {
                """
                |This library is licensed under the Apache Version 2.0 License -
                |see the [License](LICENSE.txt) file for details.
                """.trimMargin()
            }
        }

        val readme = File("../README.md").readText()

        assertEquals(readme, markdown.content)
    }
}
