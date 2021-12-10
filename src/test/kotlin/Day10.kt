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
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample.lines()) shouldBe 288957L
        two(input) shouldBe 1152088313L
    }

    private fun one(input: List<String>): Int {
        return input.map {
            var line = it
            while (line.isNotEmpty()) {
                val n = line.replace("{}", "").replace("[]", "").replace("<>", "").replace("()", "")
                if (line == n) break
                line = n
            }
            when (line.replace("""^[\[<({]+""".toRegex(), "").firstOrNull() ?: ' ') {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> 0
            }
        }.sumOf { it }
    }

    private fun two(input: List<String>): Long {
        val scores = input.mapNotNull {
            var line = it
            while (line.isNotEmpty()) {
                val n = line.replace("{}", "").replace("[]", "").replace("<>", "").replace("()", "")
                if (line == n) break
                line = n
            }
            if (line.matches("""^[\[<({]+$""".toRegex())) {
                line.toList().foldRight(0L) { c, acc ->
                    acc * 5 + when (c) {
                        '(' -> 1
                        '[' -> 2
                        '{' -> 3
                        '<' -> 4
                        else -> 0
                    }
                }
            } else null
        }
        return scores.sorted()[scores.size / 2]
    }
}

// This was pretty simple. I first thought of counting nesting levels, but then decided to simply remove
// all direct pairs.  This either results in an empty string - for a well-formed line - or the unmatched
// remainder.  Part 1 then simply removes all the opening characters, and the first remaining is the
// character we are looking for. Part 2 is equally simple: to complete the line, we need to emit the
// matching closing characters in reverse order.
