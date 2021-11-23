# AdventOfCode2021

This is the code developed for [Advent of Code 2021](https://adventofcode.com/2021).  My primary goals are:

  - have fun
  - learn something new about Kotlin
  - don't spend more than 1 hour each day
 
 I thus favor short and readable code over optimized code. As a rule of thumb, any code which finishes in under 1 minute
 is "good enough".  I also will not try to handle possible corner cases if the provided input file does not contain them.
 
 Every day contains 2 sub-problems. The code is organized to use an input file per day, and have mostly standalone
 functions for each of the 2 sub-problems. The code uses unit tests to make it easier to document the expected result
 and also because that allows to run the functions directly in IntelliJ.

 # Principles

 AoC (at least in the past and there is no reason to believe this year will be different), offers 2 challenges per day.
 Each day will also provide an input file which contains the dat for both challenges.

 This project setup use a unit test per day to provide the solution code. We chose unit test because that allows to code
 the expected/correct result in a standardized way, and because IntelliJ can directly execute these tests.

 To simplify the test setup, this project provides a Junit5 extension which automates the passing of the daily
 input file to the tests. For this to work, the input files must be named exactly like the classes which contain the
 test which consume them. A straight-forward approach is thus to create an input file `input/Day12` and a test class
 `Day12` for the challenge of day 12.

 Given the day range, it is advised to use 0-padded 2 digits for the day number (e.g. the input for file day 3 should be
 named `Day03`).

 # Implementation Details

 The input test data handling is implemented using a Junit5 extension for test parameters, implemented in
 `InputParameterResolver`. To avoid having to annotate test cases, this extension is automatically enabled by using the
 files `resources/META-INF/services/org.junit.jupiter.api.extension.Extension` which list the extension and
 `resources/junit-platform.properties` which enables the auto-detection of extensions.

# Example

This repository contains the input file `input/Day00` and the solution file `src/test/kotlin/Day00.kt` as an example.
This `Day00.kt` is a good candidate to be copied for the daily challenges.
