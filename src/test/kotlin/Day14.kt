import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day14 {
    private val sample = """
        NNCB

        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C
""".trimIndent()

    @Test
    fun testOne(input: List<String>) {
        one(sample.lines()) shouldBe 1588
        one(input) shouldBe 4244

        two(sample.lines(), 10) shouldBe 1588L
        two(input, 10) shouldBe 4244L
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample.lines(), 40) shouldBe 2188189693529L
        two(input, 40) shouldBe 4807056953866L
    }

    data class Input(val template: String, val rules: Map<String, Char>)

    private fun parse(input: List<String>): Input {
        val template = input[0]
        val rules = input.drop(2).associate { line -> line.split(" -> ").let { it[0] to it[1][0] } }
        return Input(template, rules)
    }

    private fun one(input: List<String>): Int {
        val (template, rules) = parse(input)
        val rules1 = rules.mapValues { e -> e.key.toList().let { (a, b) -> "$a${e.value}$b" } }
        var polymer = template
        repeat(10) {
           polymer = polymer.expand(rules1)
        }
        val counts = polymer.toList().groupingBy { it }.eachCount().values
        return counts.maxOf { it } - counts.minOf { it }
    }

    private fun String.expand(rules: Map<String, String>): String {
        return windowed(2, partialWindows = false).map { rules[it] ?: it }.reduce { acc, s -> acc.dropLast(1) + s }
    }

    class CountingMap<T>(
        l: List<T> = emptyList(),
        private val m: MutableMap<T, MutableLong> = mutableMapOf()
    ) : MutableMap<T, CountingMap.MutableLong> by m {
        init {
            l.forEach { inc(it) }
        }

        class MutableLong(var value: Long)

        fun inc(k: T, amount: Long = 1L) {
            m.getOrPut(k) { MutableLong(0L) }.value += amount
        }
    }

    class Rule(k: String, val char: Char) {
        val keys = k.toList().let { (a, b) -> listOf("$a$char", "$char$b") }
    }

    private fun two(input: List<String>, runs: Int): Long {
        val (template, rules) = parse(input)
        val rules1 = rules.mapValues { (k, v) -> Rule(k, v) }
        val chars = CountingMap(template.toList())
        var parts = CountingMap(template.windowed(2, partialWindows = false))

        repeat(runs) {
            val next = CountingMap<String>()
            for ((key, count) in parts) {
                rules1[key]?.let { r ->
                    r.keys.forEach { next.inc(it, count.value) }
                    chars.inc(r.char, count.value)
                }
            }
            parts = next
        }

        return chars.values.maxOf { it.value } - chars.values.minOf { it.value }
    }
}

// This was a very typical AoC puzzle: part 1 could be implemented directly following
// the rules, but part 2 will blow up with the same approach.  I relatively quickly
// came up with the "windowed" approach for part 1. I then initially tried to just
// use the windowed parts with a count for part 2, but could not figure out how to
// compute the char counts from the parts and their counts.  The break-through was the
// realization that I know the initial count of chars and how many new chars are added
// in every expansion.
// I obviously could have then replaced the approach for part 2 to solve part 1, but I
// decided to keep the initial solution to keep myself honest.
// Update 1: used `.withDefault { 0L }` and `getValue` in `CountingMap`.
// Update 2: added `class Rule` to isolate the key calculations
// Update 3: used `MutableLong` in `CountingMap`
// Update 4: also added calls of `two` to `testOne` to emphasize that `two` is a faster
// version of `one`
