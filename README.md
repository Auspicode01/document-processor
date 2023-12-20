## Table of Contents

1) [Description](#Description)
2) [API](#Api)
3) [Technology Stack](#Stack)
4) [Use the Library](#Use)
5) [License](#License)

<a id="Description"></a>
## Description

This library's main goal is to help developers locate specific pieces of information in a multi line text.
It was originally developed to extract data from pdf files.<br>
The idea is to expand its functionalities as needed so suggestions are welcomed :)

<a id="Api"></a>
## API

At the moment there are two classes with public methods: SearchLine and ProcessLine.<br>

#### SearchLine
- withKeyword<br>
  Arguments: String text, String keyword.<br>
  Description: searches entire text and returns the first line that contains a given keyword.<br>

- withOffset<br>
  Arguments: String text, String keyword, int offset.<br>
  Description: searches entire text for the first line that contains a given keyword and returns the line that is found at a given offset from the one containing the keyword.<br> The offset can be a positive or negative whole number.<br>

#### ProcessLine
- extractValueAfterKeyword<br>
  Arguments: String line, String keyword, int index.<br>
  Return type: String.<br>
  Description: searches previously selected line for the keyword.<br> Splits the line in two parts being keyword the last word of the first part.<br> Second part is turned into an array in which each word is its own element.<br> Retrieves whatever word corresponds to the index argument of that array.<br>
- extractAllValuesAfterKeyword<br>
  Arguments: String line, String keyword.<br>
  Return type: String.<br>
  Description: searches previously selected line for the keyword.<br> Splits the line in two parts being keyword the last word of the first part.<br> Retrieves the entire second part.<br>
- extractAllValuesAfterIndex<br>
  Arguments: String line, int index.<br>
  Return type: String.<br>
  Description: line is turned into an array in which each word is its own element.<br> Retrieves every element after index in a single string.<br>
- extractValue<br>
  Arguments: String line, int index.<br>
  Return type: String.<br>
  Description: line is turned into an array in which each word is its own element.<br> Retrieves whatever word corresponds to the index argument of that array.<br>
- extractSeveralValues<br>
  Arguments: String line, int... index.<br>
  Return type: String.<br>
  Description: line is turned into an array in which each word is its own element.<br> Retrieves whatever words corresponds to the indexes passed as the method's second argument.<br>
- excludeLastValue<br>
  Arguments: String line.<br>
  Return type: String.<br>
  Description: retrieves every word present on the line but the last.<br>
- extractAmount<br>
  Arguments: String line.<br>
  Return type: Double.<br>
  Description: retrieves the first occurrence of four numeric values separated by a coma or a dot in groups of two.<br>
- extractMonth<br>
  Arguments: String line, String dateFormat, int iterations.<br>
  Return type: Month.<br>
  Description: iterates over the search of the occurrence of a date value that meets the date format passed as the second argument for how many times user has defined in the third argument.<br> Retrieves the month value of that date.<br>
- extractYear<br>
  Arguments: String line, String dateFormat, int iterations.<br>
  Return type: Year.<br>
  Description: iterates over the search of the occurrence of a date value that meets the date format passed as the second argument for how many times user has defined in the third argument.<br> Retrieves the year value of that date.<br>
- extractDate<br>
  Arguments: String line, String dateFormat, int iterations.<br>
  Return type: LocalDate.<br>
  Description: iterates over the search of the occurrence of a date value that meets the date format passed as the second argument for how many times user has defined in the third argument. Retrieves the date value.

<a id="Stack"></a>
## Technology Stack

Technologies used in this project are:
- Java 17
- Maven
- Lombok
- Junit
- Mockito
- AssertJ

<a id="Use"></a>
## Use the Library

To use this library please refer to the Maven Central page:<br>
[Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.auspicode01/document-processor)

<a id="License"></a>
## License

This project is licensed under [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0).