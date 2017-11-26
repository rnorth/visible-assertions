
## [2.2.0] - 2017-11-26

* Add special case behaviour for tests running in Gradle, which is also capable of correctly handling/filtering ANSI output.

## [2.1.0] - 2017-11-11

* Switch TTY detection to be based on JNA, for smaller dependency chain. Special case behaviour is in place for tests running in IntelliJ IDEA and Maven, which are both capable of correctly handling ANSI output even though there is not a TTY.

## [2.0.0] - 2017-07-13

* Remove Jansi to reduce likelihood of native lib class conflicts when used in Gradle projects. ANSI output is implemented within the library now, with pnr-posix providing isatty support.
* Allow system properties to be used to control visibility of output (see [README](README.md)).
* Add `assertVisiblyEquals` and `assertRoughlyEquals` methods for approximate matching.

## [1.0.5] - 2016-04-28

* Remove version ranges for dependencies and make versions explicit

## [1.0.4] - 2015-12-17

* Improve output for equals assertions where objects are string-equal but different types
* Improve output for equals assertions where either value is null

## [1.0.3] - 2015-08-28

* Add assertThrows for Runnables as well as Callables

## [1.0.2] - 2015-08-03

* Add assertNotEquals

## [1.0.1] - 2015-08-02

* Initial release

[2.2.0]: https://github.com/rnorth/visible-assertions/releases/tag/visible-assertions-2.2.0
[2.1.0]: https://github.com/rnorth/visible-assertions/releases/tag/visible-assertions-2.1.0
[2.0.0]: https://github.com/rnorth/visible-assertions/releases/tag/visible-assertions-2.0.0
[1.0.4]: https://github.com/rnorth/visible-assertions/releases/tag/visible-assertions-1.0.4
[1.0.3]: https://github.com/rnorth/visible-assertions/releases/tag/visible-assertions-1.0.3
[1.0.2]: https://github.com/rnorth/visible-assertions/releases/tag/visible-assertions-1.0.2
[1.0.1]: https://github.com/rnorth/visible-assertions/releases/tag/visible-assertions-1.0.1