package com.herman.markdown_dsl

import com.herman.markdown_dsl.elements.heading
import com.herman.markdown_dsl.elements.orderedList
import com.herman.markdown_dsl.elements.paragraph
import com.herman.markdown_dsl.elements.underlinedHeading
import org.junit.jupiter.api.Test

internal class MarkdownBuilderTest {

    @Test
    fun test() {
        markdown {
            paragraph {
                bold("asdf a")
                orderedList {
                    paragraph {

                    }
                }
            }
            orderedList {
                heading("")
            }
            heading { "" }
            underlinedHeading { "" }
        }
    }
}
