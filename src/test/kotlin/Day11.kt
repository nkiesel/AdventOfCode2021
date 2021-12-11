import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day11 {
    private val sample = """
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
""".trimIndent()

    @Test
    fun testOne(input: List<String>) {
        one(sample.lines(), 100, false).flashes shouldBe 1656
        one(input, 100, false).flashes shouldBe 1702
    }

    @Test
    fun testTwo(input: List<String>) {
        one(sample.lines(), Int.MAX_VALUE, true).steps shouldBe 195
        one(input, Int.MAX_VALUE, true).steps shouldBe 251
    }

    class Result(val steps: Int, val flashes: Int)

    private fun one(input: List<String>, maxSteps: Int, firstAllZeros: Boolean): Result {
        val map = input.map { line -> line.toList().map { it.digitToInt() }.toIntArray() }.toTypedArray()
        var flashes = 0

        for (step in 1..maxSteps) {
            // Increment all and collect initial list of flashers
            var flashers = buildList {
                for (row in map.indices) for (col in map[row].indices) if (++map[row][col] == 10) add(Pair(row, col))
            }

            // Process all the flashers, collecting new ones along the way
            while (flashers.isNotEmpty()) {
                flashes += flashers.count()
                val new = buildList {
                    for ((row, col) in flashers) {
                        for ((nr, nc) in map.neighbors(row, col)) {
                            if (++map[nr][nc] == 10) add(Pair(nr, nc))
                        }
                    }
                }
                flashers = new
            }

            // Reset all flashers to 0.  At this point, values can be > 10
            var allZero = true
            for (row in map.indices) for (col in map[row].indices) if (map[row][col] >= 10) map[row][col] = 0 else allZero = false
            if (allZero && firstAllZeros) return Result(step, flashes)
        }

        return Result(maxSteps, flashes)
    }

    private fun Array<IntArray>.neighbors(x: Int, y: Int): List<Pair<Int, Int>> =
        listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
            .map { (dx, dy) -> x + dx to y + dy }
            .filter { (cx, cy) -> cx in this[0].indices && cy in this.indices }

}

// Not very elegant, but works. I really should have a proper 2D-map type for these puzzles.
