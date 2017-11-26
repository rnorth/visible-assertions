# Visible Assertions

> Alternative to JUnit assertions that provides a more insightful log narrative for your tests

<img src="screenshot.png" width="100%" alt="Screenshot showing example output"/>

[![Circle CI](https://circleci.com/gh/rnorth/visible-assertions/tree/master.svg?style=svg)](https://circleci.com/gh/rnorth/visible-assertions/tree/master)


## Table of Contents
<!-- MarkdownTOC autolink=true bracket=round depth=3 -->

- [Use Case](#use-case)
- [Usage summary](#usage-summary)
    - [Simple assertions](#simple-assertions)
    - [Hamcrest](#hamcrest)
    - [assertThrows](#assertthrows)
    - [General test narrative](#general-test-narrative)
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

Additionally, when something is perhaps equivalent in value but not in type, additional output will provide a hint.
This can be useful in cases where a simple type error has crept in to test code, e.g.:

<span style='color:red'>&nbsp;&nbsp;&nbsp;&nbsp;✘ The number of sales is returned correctly</span><br />
<span style='color:orange'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'42' \[java.lang.Integer] does not equal expected '42' \[java.math.BigInteger]</span>

Other simple assertion methods provided are:

* *assertTrue*
* *assertFalse*
* *assertNull*
* *assertNotNull*
* *assertNotEquals*
* *assertSame*

### Hamcrest

You can use Hamcrest matchers too:

    assertThat("the colour of the sky", sky.getColour(), is(equalTo(Colours.BLUE)));

yields:

<span style='color:green'>&nbsp;&nbsp;&nbsp;&nbsp;✔ the colour of the sky is BLUE</span>

### assertThrows

If you want to assert that a block of code definitely does throw a particular class of assertion:

    assertThrows("it blows up if the input is out of range", IllegalArgumentException.class, () -> {
        account.withdraw(-1);
    });

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
        <groupId>org.rnorth.visible-assertions</groupId>
        <artifactId>visible-assertions</artifactId>
        <version>2.2.0</version>
    </dependency>

## Controlling output

Output may be controlled via system properties:

* `visibleassertions.silence`: if set to `true`, assertions will still be enforced but no output will be produced.
* `visibleassertions.silence.passes`: if set to `true`, assertion passes will not be output.
* `visibleassertions.silence.failures`: if set to `true`, assertion failures will not be output.

The library will attempt to detect terminal capabilities to determine whether it is appropriate to use ANSI (coloured) output. The basic logic is:

* If STDOUT is a TTY, ANSI coloured output will be used. Otherwise, ANSI colour codes will only be output if:
* running under Maven (latest versions of Maven will strip/preserve as appropriate)
* running under Gradle (latest versions of Gradle will strip/preserve as appropriate)
* running under IntelliJ IDEA

If it is necessary to override this for some reason, the `visibleassertions.ansi.enabled` may be set:

* `-Dvisibleassertions.ansi.enabled=true`: ANSI output will always be produced 
* `-Dvisibleassertions.ansi.enabled=false`: ANSI output will never be produced 

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

Copyright (c) 2015-2017 Richard North.

See [AUTHORS](AUTHORS) for contributors.
