import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day15 {
    private val sample = """
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581
""".trimIndent()

    @Test
    fun testOne(input: List<String>) {
        one(sample.lines()) shouldBe 40
        one(input) shouldBe 581
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample.lines()) shouldBe 315
        two(input) shouldBe 2916
    }

    private fun one(input: List<String>): Int {
        val data = input.map { line -> line.toList().map { it.digitToInt() } }
        return Graph(data).computeShortestPath()
    }

    class Node(
        val x: Int,
        val y: Int,
        val weight: Int,
        var distance: Int = if (x == 0 && y == 0) 0 else Int.MAX_VALUE
    ) {
        lateinit var neighbors: List<Node>
    }

    class Graph(data: List<List<Int>>) {
        private val xMax = data[0].lastIndex
        private val yMax = data.lastIndex
        private val xIndices = data[0].indices
        private val yIndices = data.indices
        private val nodes = yIndices.map { y -> xIndices.map { x -> Node(x, y, data[y][x]) } }

        init {
            nodes.flatten().forEach {
                it.run {
                    neighbors = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
                        .map { (dx, dy) -> x + dx to y + dy }
                        .filter { (nx, ny) -> nx in xIndices && ny in yIndices }
                        .map { (nx, ny) -> nodes[ny][nx] }
                }
            }
        }

        fun computeShortestPath(): Int {
            val queue = mutableSetOf(nodes[0][0])
            while (queue.isNotEmpty()) {
                val best = queue.minByOrNull { it.distance }!!
                best.neighbors.forEach { node ->
                    val distance = best.distance + node.weight
                    if (distance < node.distance) {
                        node.distance = distance
                        queue.add(node)
                    }
                }
                queue.remove(best)
            }

            return nodes[yMax][xMax].distance
        }
    }

    private fun two(input: List<String>): Int {
        val data = input.map { line -> line.toList().map { it.digitToInt() } }
        val xSize = data[0].size
        val ySize = data.size

        val big = (0 until 5 * ySize).map { y ->
            (0 until 5 * xSize).map { x ->
                data[y % ySize][x % xSize].inc(x / xSize + y / ySize)
            }
        }
        return Graph(big).computeShortestPath()
    }

    fun Int.inc(count: Int) = (this + count).let { if (it > 9) it - 9 else it }
}

// An iteration of the earlier "shortest path" puzzle.  Part 1 was pretty simple (thanks, Google!), but
// then I tried to get fancy for part 2 by computing the shortest path in sections: compute all the edge
// values for the original input, then add them as starting values in the connecting edge nodes of adjacent
// incremented maps.  I got lost there for a bit, and then took another turn when I wrongly thought that
// next steps are always either down or to the right.  The latter would allow a very fast iterative approach,
// but unfortunately the path can go up and/or left as well.  I then decided to first brute-force it by
// computing the big map and find the path in that while I find the "right" solution. To my surprise, that
// big map took less than a second!  Conclusion is that for part 2, the challenge really was the computation
// of the big map and not finding a better approach for the path computation.  The code got a bit more
// polished than usual in the process, because I spent more time than usual.
