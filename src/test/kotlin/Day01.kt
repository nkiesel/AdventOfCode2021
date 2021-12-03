import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01 {
    @Test
    fun testOne(input: List<String>) {
        // provide explicit lists for testing other cases than the actual test input
        one(listOf("199", "200", "208", "210", "200", "207", "240", "269", "260", "263")) shouldBe 7
        one(input) shouldBe 1288
    }

    @Test
    fun testTwo(input: List<String>) {
        two(listOf("607", "618", "618", "617", "647", "716", "769", "792")) shouldBe 5
        two(input) shouldBe 1311
        twoK(input) shouldBe 1311
    }

    private fun one(input: List<String>): Int = input.asSequence()
        .map(String::toInt)
        .zipWithNext()
        .count { (a, b) -> b > a }

    private fun two(input: List<String>): Int = input.asSequence()
        .map(String::toInt)
        .windowed(3) { it.sum() }
        .zipWithNext()
        .count { (a, b) -> b > a }

    // Solution from JetBrains video. This realizes that when we compare 2 windows, it always ends up
    // doing (A + B + C) <=> (B + C + D). Thus, we can eliminate "B + C" and simply use A <=> D
    private fun twoK(input: List<String>): Int = input.asSequence()
        .map(String::toInt)
        .windowed(4)
        .count { it[3] > it[0] }
}
