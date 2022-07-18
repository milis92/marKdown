package com.herman.markdown_dsl

import com.herman.markdown_dsl.elements.blockQuote
import com.herman.markdown_dsl.elements.bold
import com.herman.markdown_dsl.elements.heading
import com.herman.markdown_dsl.elements.item
import com.herman.markdown_dsl.elements.line
import com.herman.markdown_dsl.elements.orderedList
import com.herman.markdown_dsl.elements.paragraph
import com.herman.markdown_dsl.elements.regular
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class MarkdownBuilderTest {

    @Test
    fun test() {
        val content = markdown {
          //  heading { "Heading" + italic("1") + bold("Neki bold") }

            paragraph("")
            paragraph {
                line("")
                line { "" }
                line {
                    """
                    |
                    |It is possible to directly invoke Gradle  
                    | 
                    |in your workflow, and the actions/setup-java@v3 action provides a s
                    |imple way to cache Gradle dependencies.""".trimMargin()
                }
                line {
                    "Ut congue est quis est tempus congue. Aliquam eu consequat elit."
                }
                line {
                    "Mauris ipsum tellus, dignissim eget libero at, scelerisque iaculis tortor. Duis dictum turpis quis nunc placerat, eget feugiat quam consequat. Etiam eget"
                }
            }

            blockQuote {
                paragraph {

                }
            }

            orderedList {
                item {
                    paragraph {

                    }
                    orderedList {
                    }
                }
            }

            orderedList {
                item {
                    paragraph {

                    }
                    heading { "" }
                }
            }
            horizontalRule()
            paragraph {

            }
        }

        assertEquals("", content.content)
    }
}
