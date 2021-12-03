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

    private fun oneBits(list: List<Int>, mask: Int): Int {
        return list.count { (it and mask) != 0 }
    }

    private fun reduce(input: List<String>, a: Int, b: Int): Int {
        val bits = input[0].length

        var candidates = input.map { it.toInt(2) }
        var pos = bits
        while (candidates.size > 1) {
            pos--
            val mask = 1 shl pos
            val bit = if (oneBits(candidates, mask) * 2 >= candidates.size) a else b
            val wanted = bit shl pos
            candidates = candidates.filter { (it and mask) == wanted }
        }
        return candidates[0]
    }

    private fun two(input: List<String>): Int {
        val oxygen = reduce(input, 1, 0)
        val co2 = reduce(input, 0, 1)
        return oxygen * co2
    }
}

// This was a brute-force day with quiet a few errors along the way.  Bit manipulation is clearly not one of my strength.
// I avoided that completely in part 1 and instead used string manipulations to count. That did not work for part 2 though.
// Pretty sure there are much more elegant solutions for this.
//
// Note: as usual, I did not try to code for robustness.  One concrete example: I assume all lines have the same length,
// although the instructions do not guarantee that.
//
// Update: combined the repeated code for part 2 into a single helper method.
