import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03 {

    @Test
    fun testOne(input: List<String>) {
        val data = readData(input)
        val indices = data.first().indices
        val gamma = indices.map { data.mostCommonBitAtPosition(it) }.joinToString("")
        val epsilon = indices.map { data.leastCommonBitAtPosition(it) }.joinToString("")
        val power = (gamma.toInt(2) * epsilon.toInt(2))
        power shouldBe 1307354
    }

    @Test
    fun testTwo(input: List<String>) {
        val data = readData(input)
        var oxygen = data
        var co2 = data
        val indices = data.first().indices
        for (idx in indices) {
            val bit = oxygen.mostCommonBitAtPosition(idx)
            oxygen = oxygen.filter { it[idx] == bit }
            if (oxygen.count() == 1)
                break
        }

        for (idx in indices) {
            val bit = co2.leastCommonBitAtPosition(idx)
            co2 = co2.filter { bit == it[idx] }
            if (co2.count() == 1)
                break
        }

        val o = oxygen.single().joinToString("").toInt(2)
        val c = co2.single().joinToString("").toInt(2)
        (o*c) shouldBe 482500
    }

    private fun readData(input: List<String>): Sequence<List<Int>> {
        return input.asSequence().map { s -> s.toCharArray().map { it.digitToInt() } }
    }

    private fun Sequence<List<Int>>.bitCountInPosition(n: Int): Map<Int, Int> = groupingBy { it[n] }.eachCount()

    private fun Sequence<List<Int>>.mostCommonBitAtPosition(n: Int): Int {
        val v = bitCountInPosition(n)
        return if (v[1]!! >= v[0]!!) 1 else 0
    }

    private fun Sequence<List<Int>>.leastCommonBitAtPosition(n: Int): Int {
        val v = bitCountInPosition(n)
        return if (v[1]!! >= v[0]!!) 0 else 1
    }
}