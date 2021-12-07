import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue

class Day07 {

    @Test
    fun testOne(input: List<String>) {
        minFuel(input) shouldBe 355764
    }

    @Test
    fun testTwo(input: List<String>) {
        minFuel(input, true) shouldBe 99634572L
    }

    private fun minFuel(input: List<String>, gauss: Boolean = false): Long {
        val positions = input.first().split(",").map { it.toInt() }
        val pos = (positions.minOf { it }..positions.maxOf { it }).toList()
        val posToFuel = pos.map { it to  positions.fold(0L) { sum, pos ->
            val s = (it - pos).absoluteValue
            sum + (if (gauss) ((s * (s + 1))/2) else s)
        }
        }
        return posToFuel.minByOrNull { it.second }!!.second
    }

    //used Gauss formula to find sum
}