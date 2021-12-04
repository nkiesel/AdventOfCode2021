import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03 {

    @Test
    fun testOne(input: List<String>) {
        val indices = input.first().indices
        val gamma = indices.map { input.mostCommonBitAtPosition(it) }.joinToString("")
        val epsilon = indices.map { input.leastCommonBitAtPosition(it) }.joinToString("")
        val power = (gamma.toInt(2) * epsilon.toInt(2))
        power shouldBe 1307354
    }

    @Test
    fun testTwo(input: List<String>) {
        val indices = input.first().indices
        var oxygen = input
        var co2 = input
        for (idx in indices) {
            val bit = oxygen.mostCommonBitAtPosition(idx)
            oxygen = oxygen.filter { it[idx] == bit }
            if (oxygen.size == 1)
                break
        }

        for (idx in indices) {
            val bit = co2.leastCommonBitAtPosition(idx)
            co2 = co2.filter { bit == it[idx] }
            if (co2.size == 1)
                break
        }

        val o = oxygen.single().toInt(2)
        val c = co2.single().toInt(2)
        (o*c) shouldBe 482500
    }

    private fun List<String>.bitCountInPosition(n: Int): Map<Char, Int> = groupingBy { it[n] }.eachCount()

    private fun List<String>.mostCommonBitAtPosition(n: Int): Char {
        val v = bitCountInPosition(n)
        return if (v[oneBit]!! >= v[zeroBit]!!) oneBit else zeroBit
    }

    private fun List<String>.leastCommonBitAtPosition(n: Int): Char {
        val v = bitCountInPosition(n)
        return if (v[oneBit]!! >= v[zeroBit]!!) zeroBit else oneBit
    }

    companion object {
        const val oneBit = '1'
        const val zeroBit = '0'
    }
}
