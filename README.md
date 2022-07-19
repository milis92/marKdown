<h1 align="center">
  <p>Kotlin Markdown DSL</p>
</h1>

<h3 align="center">
  <p>Kotlin ❤️ Markdown</p>
</h3>

A Kotlin DSL for effortless creation of beautiful Markdown pages,
supporting both basic and extended syntax (Coming soon™️).

---

## ☁️ Setup

> Coming soon

## 📝 Usage

### Basic syntax

> Basic implementation follows
> [official Markdown syntax](https://daringfireball.net/projects/markdown/syntax)
> and should be supported by almost all Markdown processors.  
> For detailed guide and more usage examples refer to Wiki.

###### This readme page created with Kotlin DSL Markdown

```kotlin
val markdown = markdown {
    //TODO Insert raw html content

    paragraph {
        line {
            """
            |A Kotlin DSL for effortless creation of beautiful Markdown pages, 
            |supporting both basic and extended syntax (Coming soon™️).
            """.trimMargin()
        }
    }

    horizontalRule()

    heading(HeadingStyleMarker.H2) { "☁️ Setup" }

    blockQuote("Coming soon")

    heading(HeadingStyleMarker.H2) { "\uD83D\uDCDD Usage" }

    heading(HeadingStyleMarker.H3) { "Basic syntax" }

    blockQuote {
        paragraph {
            //TODO Provide api to insert links and other in-line styles
            line {
                """
                |Basic implementation follows 
                |[official Markdown syntax](https://daringfireball.net/projects/markdown/syntax) 
                |and should be supported by almost all Markdown processors.
                """.trimMargin()
            }
            line { "For detailed guide and more usage examples refer to Wiki." }
        }
    }

    heading(HeadingStyleMarker.H6) { "This readme page created with Kotlin DSL Markdown" }
    //TODO Insert code block

    heading(HeadingStyleMarker.H4) { "Exports" }

    horizontalRule()

    paragraph("This library is licensed under the Apache Version 2.0 License - see the [License](LICENSE.txt) file for details.")
}
```

#### Exports

---

This library is licensed under the Apache Version 2.0 License - see the [License](LICENSE.txt) file for details.