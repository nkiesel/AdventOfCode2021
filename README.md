# AdventOfCode2021

This is the code developed for [Advent of Code 2021](https://adventofcode.com/2021).  My primary goals are:
  - have fun
  - learn something new about Kotlin
  - don't spend more than 1 hour each day
 
I thus favor short and readable code over optimized code. As a rule of thumb, any code which finishes in under 1 minute
is "good enough".  I also will not try to handle possible corner cases if the provided input file does not contain them.
 
AoC provides 2 coding challenges for every day in December until Christmas. Every daily challenge also has one file
containing the data for the challenges.

# Principles

This project setup uses a unit test class per day to provide the solution code. Every class will contain 2 unit tests
(one per challenge), and one or more functions which implement the solution. I chose the unit test approach because that
allows to code the expected/correct result in a standardized way, and because IntelliJ can directly execute either all
or individual tests.

To simplify the test setup, this project provides a Junit5 extension which automates the passing the content of the
daily input file to the unit tests. For this to work, the input files must be named exactly like the classes which
contain the test which consume them. A straight-forward approach is thus to create an input file `input/Day12` and a
test class `Day12` for the challenge of day 12.

Given the day range, it is advised to use 0-padded 2 digits for the day number (e.g. the input for file day 3 should be
named `Day03`).

# Implementation Details

The input test data handling is implemented using a Junit5 extension for test parameters, implemented in
`InputParameterResolver`. To avoid having to annotate test cases, this extension is automatically enabled by using the
files `resources/META-INF/services/org.junit.jupiter.api.extension.Extension` which lists the extension and
`resources/junit-platform.properties` which enables the auto-detection of extensions.

We assume that individual collaborators will commit their solutions on a "private" branch (i.e. every such branch should only contain commits from one contributor). That way, we can easily review solutions from others simply by switching branches.

However, we will add all the input files to the `main` branch.  That way, collaborators can simply run `git switch main; git pull --all; git switch -; git merge --commit --no-edit main` on their "private" branch to get the available input files. Input files will be added to the `main` branch using
1. download a new input file into `input/Day<XX>` (replace `<XX>` with the 2-digit day number)
2. run
   1. `git switch main`
   2. `git add input/Day<XX>`
   3. `git commit -m 'Another day!'`
   4. `git push`
   5. `git switch -`


# Example / Template

This repository contains the input file `input/Day00` and the solution file `src/test/kotlin/Day00.kt` as an example.
This `Day00.kt` is a good candidate to be copied for the daily challenges.

- `input/Day00`
```
1
2
3
4
5
```

- `src/test/kotlin/Day00.kt`
```kotlin
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day00 {
    @Test
    fun testOne(input: List<String>) {
        // provide explicit lists for testing other cases than the actual test input
        one(listOf("3", "1", "4")) shouldBe 8
        one(input) shouldBe 15
    }

    @Test
    fun testTwo(input: List<String>) {
        two(input) shouldBe 120L
    }

    // This should return the sum of the input
    private fun one(input: List<String>): Int = input.map(String::toInt).sumOf { it }

    // This should return the product of the input
    private fun two(input: List<String>): Long {
        return input.map(String::toLong).reduce { acc, i -> acc * i }
    }
}
```
