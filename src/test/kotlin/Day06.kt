import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06 {

    @Test
    fun testOne(input: List<String>) {
        val fishes = input.first().split(",").map { Fish(it.toInt()) }.toMutableList()
        repeat(80) {
            fishes += fishes.mapNotNull { it.produce() }
        }
        fishes.size shouldBe 360268
    }

    @Test
    fun testTwo(input: List<String>) {
        val fishes = input.first()
            .split(",")
            .map { it.toInt() }
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }.toMutableMap()

        repeat(256) {
            val newFish = fishes.getOrDefault(0, 0L)
            val seven = fishes.getOrDefault(7, 0L)
            val zero = fishes.getOrDefault(0, 0L)
            for ( j in 0..8 ) {
                when(j) {
                    6 -> fishes[6] = seven + zero
                    8 -> fishes[8] = newFish
                    else -> fishes[j] = fishes.getOrDefault(j + 1, 0L)
                }
            }
        }

        fishes.values.sum() shouldBe 1632146183902
    }

    data class Fish(var life: Int) {
        fun produce(): Fish? {
            if (life == 0) {
                life = 6
                return Fish((8))
            }
            life--
            return null
        }
    }
}