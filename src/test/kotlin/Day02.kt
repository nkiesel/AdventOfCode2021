import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02 {

    @Test
    fun testOne(input: List<String>) {
        var h = 0
        var d = 0
        readData(input).forEach { (first, second) ->
            when(first) {
                "forward" -> h += second
                "up" -> d -= second
                "down" -> d += second
            }
        }
        (h * d) shouldBe 1714950
    }

    @Test
    fun testTwo(input: List<String>) {
        var h = 0
        var d = 0
        var a = 0
        readData(input).forEach { (first, second) ->
            when(first) {
                "down" -> a += second
                "up" -> a -= second
                "forward" -> {
                    h += second
                    d += (a * second)
                }
            }
        }
        (h * d) shouldBe 1281977850
    }

    private fun readData(input: List<String>): Sequence<Pair<String, Int>> {
        return input.asSequence().map { s -> s.split(Regex(" ")) }.map { it[0] to it[1].toInt() }
    }
}