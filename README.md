<h1 align="center">
  <p>Kotlin Markdown DSL</p>
</h1>

<h3 align="center">
  <p>Kotlin ❤️ Markdown</p>
</h3>

A Kotlin DSL for effortless creation of beautiful Markdown pages,
supporting both basic and extended syntax (Coming soon™️).

---

# ☁️ Setup

> Coming soon

# 🧾 Usage

### Basic syntax

> Basic implementation follows [official Markdown syntax](https://daringfireball.net/projects/markdown/syntax)
> and should be supported by almost all Markdown processors.

<details open>
<summary style="font-size:18px">Text</summary>

##### To create a simple line of text:

```kotlin
markdown {
    line("This is a simple line of text")
}
```

##### Or if you have a text with multiple lines:

```kotlin
markdown {
    line {
        """
        |A Kotlin DSL for effortless creation of beautiful Markdown pages,
        |supporting both basic and extended syntax (Coming soon™️).
        """.trimMargin()
    }
}
```

##### If you need custom emphasis, you can use the `italic`, `bold` or `codeSpan` spans:

```kotlin
markdown {
    line("This is a" + "simple".bold() + "line".italic() + "of code".codeSpan())
    //Or you can use spans directly if you want to have full lines emphasised:
    bold("This is a bold text")
    italic("This is an italic text")
    codeSpan("This is a code span")
}
```

##### You can configure emphasis markers as well:

```kotlin
markdown {
    bold("This is a bold text", EmphasisMarker.Underscore)
    italic("This is an italic text", EmphasisMarker.Asterisks)
}
```

</details>

<details>
<summary style="font-size:18px;">Paragraph</summary>

Every paragraph is consisted of multiple `lines` and each line (except the last one) will be terminated
by a line break (2 space characters + new empty line)

##### To create a simple paragraph:

```kotlin
markdown {
    paragraph {
        line { "First paragraph" }
        line { "Second paragraph" }
    }
}
```

</details>

<details>
<summary style="font-size:18px;">Horizontal rules</summary>

##### To create a simple horizontal rule:

```kotlin
markdown {
    horizontalRule()
}
```

##### If you want to use a custom style for your horizontal rule:

```kotlin
markdown {
    horizontalRule(style = HorizontalRuleStyle.Hyphen)
}
```

</details>

<details>
<summary style="font-size:18px;">Headings</summary>

Markdown supports two styles of headings:

- ATX Styled Headings
- Setext Styled Headings

##### Creating ATX Styled Heading:

```kotlin

markdown {
    heading("This is an ATX styled heading")
}
```

##### Creating Setext Styled Heading:

```kotlin

markdown {
    underlinedHeading("This is an Setext styled heading")
}
```

_Note_ Markdown headings support only single line text as headings,
so content will be automatically stripped of any new lihnes

#### If you want to use a custom style for your heading:

```kotlin
markdown {
    heading("This is an ATX styled heading", H1)
    underlinedHeading("This is an Setext styled heading", H2)
}
```

#### Headings support text spans as well:

```kotlin
markdown {
    heading("This is an" + bold("ATX") + "styled heading", H1)
    underlinedHeading("This is an" + italic("Setext") + "styled heading", H2)
}
```

</details>

<details>
<summary style="font-size:18px;">Blockquotes</summary>

##### Creating Simple blockquote:

```kotlin

markdown {
    blockQuote("Simple single line blocquote")
}
```

##### Creating Blockquote with nested elements:

Blockquote can hold any other markdown element, including blocquote as well

```kotlin
markdown {
    blockQuote {
        heading("Heading 1")
        underlinedHeading("Underlined Heading")
        horizontalRule()
        orderedList(listOf("Item 1", "Item 2"))
        unorderedList(listOf("Item 1", "Item 2"))
    }
}
```

</details>

<details>
<summary style="font-size:18px;">Code Blocks</summary>

##### Creating a simple code block:

```kotlin
markdown {
    codeBlock {
        @Language("kotlin")
        val block =
            """
            |val hello = "Hello World"
            """.trimMargin()
        block
    }
}
```

Note that you can
use [IJ Language injection](https://www.jetbrains.com/help/idea/using-language-injections.html#language_annotation) to
get a bit of help from your IDE

</details>

<details>
<summary style="font-size:18px;">Image and Url Spans</summary>

##### Creating a simple URL:

```kotlin
markdown {
    link("Google", URI("https://www.google.com"))
}
```

##### Creating a simple Image:

```kotlin
markdown {
    image("Google", URI("https://www.google.com"))
}
```

##### Using URL Element as Line span :

```kotlin
markdown {
    line {
        "This is a link: " + "Google".link(URI("https://www.google.com"))
    }
}
```

##### Using Image Element as Line span :

```kotlin
markdown {
    line {
        "This is an image: " + "Google".image(URI("https://www.google.com"))
    }
}
```

</details>

<details>
<summary style="font-size:18px;">Lists</summary>

Markdown supports 2 types of lists:

- Ordered lists (list with numbers)
- Unordered lists (list with bullets)

##### Creating an ordered list:

```kotlin
markdown {
    orderedList(listOf("Item 1", "Item 2"))
}
```

##### Creating an unordered list:

```kotlin
markdown {
    unorderedList(listOf("Item 1", "Item 2"))
}
```

##### Creating a list with complex items:

```kotlin
markdown {
    //Works the same for unordered list
    orderedList {
        item {
            line { "First paragraph" }
            line { "Second paragraph" }
        }
        item("Second item")
    }
}
```

##### Creating a list with nested list:

```kotlin
markdown {
    //Works the same for unordered list
    orderedList {
        item {
            line { "First item" }
            unorderedList {
                item("First sub item")
                item("Second sub item")
            }
        }
        item("Second item")
    }
}
```

</details>

# 🖨️ Exports

You can easily export Markdown content to a file using line writer of your choosing.  
For example:

```kotlin
val markdown = markdown {
    //your markdown content
}

File("../README").writeText(markdown.content)
```

---

This library is licensed under the Apache Version 2.0 License -
see the [License](LICENSE.txt) file for details.