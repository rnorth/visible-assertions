# Visible Assertions

> Visible Assertions is a Java library that provides a more insightful, fun, narrative for automated test suites.

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

Visible Assertions is designed to be used in place of the standard JUnit `Assert` class, providing an implementation of many of the core `assert*` methods.

The key difference is that every assertion is also accompanied by coloured and formatted log output which describes the progress of the tests in a fun, informative way.

## Usage summary


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
