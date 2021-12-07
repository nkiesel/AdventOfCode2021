import kotlin.math.absoluteValue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day07 {
    private val sample = """16,1,2,0,4,2,7,1,2,14"""

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 37
        one(input.first()) shouldBe 325528
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample) shouldBe 168
        two(input.first()) shouldBe 85015836
    }

    private fun List<Int>.match1(n: Int) = sumOf { (it - n).absoluteValue }

    private fun one(input: String): Int {
        val l = input.split(",").map(String::toInt)
        return (l.minOf { it }..l.maxOf { it }).minOf { l.match1(it) }
    }

    private fun List<Int>.match2(n: Int) = sumOf { (it - n).absoluteValue.let { d -> d * (d + 1) / 2 } }

    private fun two(input: String): Int {
        val l = input.split(",").map(String::toInt)
        return (l.minOf { it }..l.maxOf { it }).minOf { l.match2(it) }
    }
}

// Clearly the best position must be somewhere between the min and max of the positions.
// I first thought of finding a nearly optimal position using some means, but then I
// decided to keep it very simple and iterate over all values between min and max. For each
// possible value, match1 computes the overall cost.
// Part 2 is very similar, just that the cost is not the difference between the selected value
// and the position but the sum of 1..d (where d is the difference).  And young Gauss figured
// out that the sum of 1..d is d * (d + 1) / 2, which is the formula used in match2.
