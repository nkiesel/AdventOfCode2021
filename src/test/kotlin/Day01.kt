import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01 {

    @Test
    fun testOne(input: List<String>) {
        val count = input.asSequence().map(String::toInt).zipWithNext().count { (prev, nxt) -> nxt > prev }
        count shouldBe 1316
    }

    @Test
    fun testTwo(input: List<String>) {
        val count = input.asSequence().map(String::toInt).windowed(3) { it.sum() }.zipWithNext().count { (prev, nxt) -> nxt > prev }
        count shouldBe 1344
    }
}