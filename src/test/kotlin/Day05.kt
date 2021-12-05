import kotlin.math.absoluteValue
import kotlin.math.sign
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day05 {
    private val sample = """
0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2
""".trimIndent()

    @Test
    fun testOne(input: List<String>) {
        one(sample.lines()) shouldBe 5
        one(input) shouldBe 5585
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample.lines()) shouldBe 12
        two(input) shouldBe 17193
    }

    class Line(private val sx: Int, private val sy: Int, private val ex: Int, private val ey: Int) {
        constructor(v: List<Int>) : this(v[0], v[1], v[2], v[3])

        fun straight() = sx == ex || sy == ey

        // this only works for straight or diagonal lines
        fun covered(): List<Pair<Int, Int>> {
            val dx = (ex - sx).sign
            val dy = (ey - sy).sign
            val num = (if (dx == 0) ey - sy else ex - sx).absoluteValue
            return (0..num).map { i -> sx + i * dx to sy + i * dy }
        }
    }

    private val digits = Regex("""\d+""")

    private fun findNumbers(value: String) = digits.findAll(value).map { it.groupValues[0].toInt() }.toList()

    private fun one(input: List<String>): Int {
        val lines = input.map { Line(findNumbers(it)) }.filter { it.straight() }
        return lines.flatMap { it.covered() }.groupingBy { it }.eachCount().count { it.value > 1 }
    }

    private fun two(input: List<String>): Int {
        val lines = input.map { Line(findNumbers(it)) }
        return lines.flatMap { it.covered() }.groupingBy { it }.eachCount().count { it.value > 1 }
    }
}

// I initially tried to solve this by iterating though all the possible points and asking every line
// if it contains that point.  However, that turned out too expensive.  I then decided to rather ask
// every line for the list of points it covers and then count points appearing more than once.
