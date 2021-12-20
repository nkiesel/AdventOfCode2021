import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day20 {
    val sample = """
        ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##
        #..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###
        .######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#.
        .#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#.....
        .#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#..
        ...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.....
        ..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#

        #..#.
        #....
        ##..#
        ..#..
        ..###
    """.trimIndent()

    @Test
    fun testOne(input: List<String>) {
        one(parse(sample.lines()), 2) shouldBe 35
        one(parse(input), 2) shouldBe 5354
    }

    @Test
    fun testTwo(input: List<String>) {
        one(parse(sample.lines()), 50) shouldBe 3351
        one(parse(input), 50) shouldBe 18269
    }

    class Input(val algorithm: String, val image: List<String>)

    private fun parse(input: List<String>): Input {
        val iterator = input.iterator()
        val algorithm = buildList {
            while (iterator.hasNext()) {
                val line = iterator.next()
                if (line.isEmpty()) break
                add(line)
            }
        }.joinToString("")

        val data = buildList { iterator.forEachRemaining { add(it) } }
        return Input(algorithm, data)
    }

    private fun List<String>.enlarge(n: Int, s: String): List<String> {
        val e = mutableListOf<String>()
        repeat(n) { e.add(s.repeat(this[0].length + 2 * n)) }
        this.forEach { line -> e.add(s.repeat(n) + line + s.repeat(n)) }
        repeat(n) { e.add(s.repeat(this[0].length + 2 * n)) }
        return e
    }

    private fun List<String>.compute(row: Int, col: Int): Int {
        return (-1..1).flatMap { dr -> (-1..1).map { dc -> if (this[row + dr][col + dc] == '#') "1" else "0" } }.joinToString("").toInt(2)
    }

    private fun List<String>.litPixels(): Int {
        return this.sumOf { line -> line.count { it == '#' } }
    }

    private fun one(input: Input, n: Int): Int {
        val background = sequence {
            val infiniteDark = input.algorithm[0] == '.'
            val allLitTurnsDark = input.algorithm[input.algorithm.lastIndex] == '.'
            yield(".")
            var lastWasLit = false
            while (true) {
                yield(if (infiniteDark || lastWasLit && allLitTurnsDark) "." else "#")
                lastWasLit = !lastWasLit
            }
        }.iterator()

        var current = input.image

        repeat(n) {
            current = current.enlarge(3, background.next())
            val next = (1 until current.lastIndex).map { row ->
                (1 until current[0].lastIndex).map { col ->
                    input.algorithm[current.compute(row, col)]
                }.joinToString("")
            }
            current = next
        }
        return current.litPixels()
    }
}

// The one tricky part of this is that - based in the algorithm - all dark areas can turn all lit. I
// coded that as an iterator over a sequence, but that looks too convoluted.  Also, I think that if
// an all-lit area does not turn all-dark again, the infinite universe will stay lit up forever.
