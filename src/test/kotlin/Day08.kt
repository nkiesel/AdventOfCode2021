import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day08 {
    private val sample = """
        be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
        edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
        fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
        fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
        aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
        fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
        dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
        bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
        egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
        gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
""".trimIndent()

    @Test
    fun testOne(input: List<String>) {
        one(sample.lines()) shouldBe 26
        one(input) shouldBe 412
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample.lines()) shouldBe 61229
        two(input) shouldBe 978171
    }

    private fun one(input: List<String>): Int {
        return input.map { l -> l.split(" | ")[1] }.flatMap { it.split(" ").map(String::length) }.count { it in setOf(2, 3, 4, 7) }
    }

    private fun two(input: List<String>): Int {
        return input.sumOf { value2(it) }
    }

    private fun value2(line: String): Int {
        val (signal, output) = line.split(" | ")
        val s = signal.split(" ").map { it.toSet() }.toMutableSet()
        val d1 = s.first { it.size == 2 }.also { s -= it }
        val d4 = s.first { it.size == 4 }.also { s -= it }
        val d7 = s.first { it.size == 3 }.also { s -= it }
        val d8 = s.find { it.size == 7 }?.also { s -= it }
        val d9 = s.find { it.size == 6 && it.containsAll(d4 + d7) }?.also { s -= it }
        val d0 = s.find { it.size == 6 && it.containsAll(d1) && !it.containsAll(d4) }?.also { s -= it }
        val d6 = s.find { it.size == 6 }
        val d3 = s.find { it.size == 5 && it.containsAll(d1) }?.also { s -= it }
        val d5 = s.find { it.size == 5 && (it - d4).size == 2 }?.also { s -= it }
        val d2 = s.find { it.size == 5 }
        return output.split(" ").map { it.toSet() }.joinToString("") {
            when (it) {
                d0 -> "0"
                d1 -> "1"
                d2 -> "2"
                d3 -> "3"
                d4 -> "4"
                d5 -> "5"
                d6 -> "6"
                d7 -> "7"
                d8 -> "8"
                d9 -> "9"
                else -> ""
            }
        }.toInt()
    }
}

// The solution for part 1 was very simple, but I struggled with part 2.  I first thought I should deduce a mapping
// from the reported letters to the real letters, but that went nowhere. I finally settled for assuming all signals
// always contain 1, 4, and 7, and then finding the remaining digits using the number of segments and comparisons with
// already found digits.  I'm sure there must be better solutions!
