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
