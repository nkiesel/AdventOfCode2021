import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day09 {
    private val sample = """
2199943210
3987894921
9856789892
8767896789
9899965678
""".trimIndent()

    @Test
    fun testOne(input: List<String>) {
        one(sample.lines()) shouldBe 15
        one(input) shouldBe 562
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample.lines()) shouldBe 1134
        two(input) shouldBe 1076922
    }

    private fun one(input: List<String>): Int {
        val map = input.map { line -> line.toList().map { it.digitToInt() }.toIntArray() }.toTypedArray()
        var lowPoints = 0
        for (x in map[0].indices) {
            for (y in map.indices) {
                val p = map[y][x]
                if (p != 9 && map.lowPoint(x, y)) {
                    lowPoints += p + 1
                }
            }
        }
        return lowPoints
    }

    private fun Array<IntArray>.lowPoint(x: Int, y: Int): Boolean {
        val p = this[y][x]
        return neighbors(x, y).none { (cx, cy) -> this[cy][cx] <= p }
    }

    private fun Array<IntArray>.neighbors(x: Int, y: Int) =
        listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
            .map { (dx, dy) -> x + dx to y + dy }
            .filter { (cx, cy) -> cx in this[0].indices && cy in this.indices }

    private fun two(input: List<String>): Int {
        val map = input.map { line -> line.toList().map { it.digitToInt() }.toIntArray() }.toTypedArray()
        return buildList {
            for (x in map[0].indices) {
                for (y in map.indices) {
                    if (map[y][x] != 9) {
                        add(map.basinSize(x, y))
                    }
                }
            }
        }.sortedDescending().take(3).reduce { product, i -> product * i }
    }

    private fun Array<IntArray>.basinSize(x: Int, y: Int): Int {
        var size = 1
        this[y][x] = 9
        for ((cx, cy) in neighbors(x, y)) {
            if (this[cy][cx] != 9) {
                size += basinSize(cx, cy)
            }
        }
        return size
    }
}

// This was pretty straight-forward: walk over the map and look at surroundings. For part 2,
// I set cells to 9 while measuring a basin to avoid looking at the same cell more than once.
// I only had one hick-up: computation of the neighbors.  I initially iterated over -1..1 for
// dx and dy and missed excluding the center.  I then simply explicitly listed the 4 pairs of
// offsets.
// Update: I created a "neighbors" helper method to isolate the neighbors' computation.
//
// It would be interesting to implement this using matrix libraries like
// https://github.com/Kotlin/multik or https://github.com/JetBrains-Research/viktor
