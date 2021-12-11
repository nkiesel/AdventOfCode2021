import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day10 {
    private val sample = """
[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]
""".trimIndent()

    @Test
    fun testOne(input: List<String>) {
        one(sample.lines()) shouldBe 26397
        one(input) shouldBe 166191

        oneK(sample.lines()) shouldBe 26397
        oneK(input) shouldBe 166191
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample.lines()) shouldBe 288957L
        two(input) shouldBe 1152088313L

        twoK(sample.lines()) shouldBe 288957L
        twoK(input) shouldBe 1152088313L
    }

    private fun one(input: List<String>): Int {
        val regex2 = """^[\[<({]+""".toRegex()
        return input.mapNotNull { reduce(it).replace(regex2, "").firstOrNull() }
            .map {
                when (it) {
                    ')' -> 3
                    ']' -> 57
                    '}' -> 1197
                    '>' -> 25137
                    else -> 0
                }
            }
            .sumOf { it }
    }

    private val regex1 = """\{}|\[]|<>|\(\)""".toRegex()

    private fun reduce(line: String): String {
        var l = line
        while (l.isNotEmpty()) {
            val n = l.replace(regex1, "")
            if (l == n) break
            l = n
        }
        return l
    }

    private fun two(input: List<String>): Long {
        val regex2 = """^[\[<({]+$""".toRegex()

        val scores = input.map { reduce(it) }.filter { it.matches(regex2) }.map { line ->
            line.toList().foldRight(0L) { c, acc ->
                acc * 5 + when (c) {
                    '(' -> 1
                    '[' -> 2
                    '{' -> 3
                    '<' -> 4
                    else -> 0
                }
            }
        }
        return scores.sorted()[scores.size / 2]
    }

    private fun oneK(input: List<String>): Int {
        val pairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
        val scores = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
        return input.sumOf { line ->
            val stack = mutableListOf<Char>()
            var score = 0
            for (c in line) {
                when (c) {
                    in pairs.keys -> stack.add(c)
                    pairs[stack.lastOrNull()] -> stack.removeLast()
                    else -> {
                        score = scores[c] ?: 0
                        break
                    }
                }
            }
            score
        }
    }

    private fun twoK(input: List<String>): Long {
        val pairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
        return input.map { line ->
            val stack = mutableListOf<Char>()
            for (c in line) {
                when (c) {
                    in pairs.keys -> stack.add(c)
                    pairs[stack.lastOrNull()] -> stack.removeLast()
                    else -> {
                        stack.clear()
                        break
                    }
                }
            }
            stack.foldRight(0L) { c, acc -> 5 * acc + pairs.keys.indexOf(c) + 1 }
        }.filter { it != 0L }.sorted().let { it[it.size / 2] }
    }
}

// This was pretty simple. I first thought of counting nesting levels, but then decided to simply remove
// all direct pairs.  This either results in an empty string - for a well-formed line - or the unmatched
// remainder.  Part 1 then simply removes all the opening characters, and the first remaining is the
// character we are looking for. Part 2 is equally simple: to complete the line, we need to emit the
// matching closing characters in reverse order.
//
// Update: Inspired by Avinash and some solutions from Slack, I added 2 more traditional stack-based
// solutions.  Pretty sure they are faster than my original solution.
