import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03 {
    private val sample = listOf("00100", "11110", "10110", "10111", "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010")

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 198
        one(input) shouldBe 2954600
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample) shouldBe 230
        two(input) shouldBe 1662846
    }

    private fun one(input: List<String>): Int {
        val ones = IntArray(input[0].length)
        val zeros = IntArray(input[0].length)
        for (line in input) {
            line.forEachIndexed { index, c -> if (c == '0') zeros[index]++ else ones[index]++ }
        }
        val gamma = ones.zip(zeros).map { (o, z) -> if (o > z) '1' else '0' }.joinToString("").toInt(2)
        val epsilon = ones.zip(zeros).map { (o, z) -> if (o < z) '1' else '0' }.joinToString("").toInt(2)
        return gamma * epsilon
    }

    private fun oneBits(list: List<Int>, pos: Int): Int {
        val mask = 1 shl pos
        return list.count { (it and mask) != 0 }
    }

    private fun two(input: List<String>): Int {
        val bits = input[0].length

        var oxygen = input.map { it.toInt(2) }
        var pos = bits - 1
        while (oxygen.size > 1) {
            val mostCommon = if (oneBits(oxygen, pos) * 2 >= oxygen.size) 1 else 0
            val mask = 1 shl pos
            val value = mostCommon shl pos
            oxygen = oxygen.filter { (it and mask) == value }
            pos--
        }

        var co2 = input.map { it.toInt(2) }
        pos = bits - 1
        while (co2.size > 1) {
            val leastCommon = if (oneBits(co2, pos) * 2 < co2.size) 1 else 0
            val mask = 1 shl pos
            val value = leastCommon shl pos
            co2 = co2.filter { (it and mask) == value }
            pos--
        }

        return oxygen[0] * co2[0]
    }
}

// This was a brute-force day with quiet a few errors along the way.  Bit manipulation is clearly not one of my strength.
// I avoided that completely in part 1 and instead used string manipulations to count. That did not work for part 2 though.
// Pretty sure there are much more elegant solutions for this.
//
// Note: as usual, I did not try to code for robustness.  One concrete example: I assume all lines have the same length,
// although the instructions do not guarantee that.
