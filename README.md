# Visible Assertions

> Visible Assertions is a Java library that provides a more insightful, fun, log narrative for automated test suites.

[![Circle CI](https://circleci.com/gh/rnorth/visible-assertions/tree/master.svg?style=svg)](https://circleci.com/gh/rnorth/visible-assertions/tree/master)


## Table of Contents
<!-- MarkdownTOC autolink=true bracket=round depth=3 -->

- [Use Case](#use-case)
- [Usage summary](#usage-summary)
- [Maven dependency](#maven-dependency)
- [License](#license)
- [Attributions](#attributions)
- [Contributing](#contributing)
- [Copyright](#copyright)

<!-- /MarkdownTOC -->

## Use Case

Visible Assertions is designed to be used in place of the standard JUnit `Assert` class, providing an implementation of many of the core `assert*` methods. Each method takes an additional parameter which developers can use to describe exactly what the assertion is checking.

The key difference is that every assertion is also accompanied by coloured and formatted log output which describes the progress of the tests in a fun, informative way.

This helps with:

* giving a useful description of which assertions passed/failed during a test run
* 'comments as code' - your assertions provide a meaningful description of what they're doing, visible to both people looking at the code and those looking at the test output
* through contextual markers, symbols* and use of colour**, it's quick to spot where tests have gone awry

(* requires unicode support)

(** coloured output requires a terminal that supports ANSI escape codes. If the terminal doesn't support ANSI, output will fall back to monochrome.)

## Usage summary

### Simple assertions

A simple example:

    assertEquals("The sky is blue", Colours.BLUE, theSkyColour);

yields:

<span style='color:green'>&nbsp;&nbsp;&nbsp;&nbsp;✔ The sky is blue</span>

unless it isn't:

<span style='color:red'>&nbsp;&nbsp;&nbsp;&nbsp;✘ The sky is blue</span><br />
<span style='color:orange'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'PURPLE' does not equal expected 'BLUE'</span>

Other simple assertion methods provided are:

* *assertTrue*
* *assertFalse*
* *assertNull*
* *assertNotNull*
* *assertSame*

### Hamcrest

You can use Hamcrest matchers too:

    assertThat("the colour of the sky", sky.getColour(), is(equalTo(Colours.BLUE)));

yields:

<span style='color:green'>&nbsp;&nbsp;&nbsp;&nbsp;✔ the colour of the sky is BLUE</span>

### assertThrows

If you want to assert that a block of code definitely does throw a particular class of assertion:

    assertThrows("it blows up if the input is out of range", IllegalArgumentException.class, () -> {
        account.withdraw(-1);    });

This will fail if no exception is thrown or if the wrong type of exception is thrown.

### General test narrative

While not strictly assertions, Visible Assertions also provides a handful of log-like methods to allow you to describe what's going on in each test:

* *fail(String message)*: Just fail for a specified reason, which will be shown in red text.
* *pass(String message)*: Indicate that some external check passed, with a message that will be shown in green.
* *info(String message)*: Print a message with an 'info' symbol
* *warn(String message)*: Print a message with a warning symbol, in yellow
* *context(String message)*: Plots a message and horizontal line across the terminal to demarcate sections of a test

## Maven dependency

    <dependency>
        <groupId>org.rnorth</groupId>
        <artifactId>visible-assertions</artifactId>
        <version>1.0.0</version>
    </dependency>

## License

See [LICENSE](LICENSE).

## Attributions

This project is extracted from [TestPackage](http://testpackage.org) by the same author.

## Contributing

* Star the project on Github and help spread the word :)
* [Post an issue](https://github.com/rnorth/visible-assertions/issues) if you find any bugs
* Contribute improvements or fixes using a [Pull Request](https://github.com/rnorth/visible-assertions/pulls). If you're going to contribute, thank you! Please just be sure to:
	* discuss with the authors on an issue ticket prior to doing anything big
	* follow the style, naming and structure conventions of the rest of the project
	* make commits atomic and easy to merge
	* verify all tests are passing. Build the project with `mvn clean install` to do this.

## Copyright

Copyright (c) 2015 Richard North.

See [AUTHORS](AUTHORS) for contributors.
