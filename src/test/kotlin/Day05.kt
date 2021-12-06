import io.kotest.matchers.shouldBe
import kotlin.math.absoluteValue

import org.junit.jupiter.api.Test

class Day05 {

    @Test
    fun testOne(input: List<String>) {
        val lines = readData(input)
        val grid = Array(1000) { _ -> IntArray(1000) { _ -> 0 } }
        lines.map { line ->
            drawLine(grid, line)
        }
        grid.map { it.count { it > 1 } }.sumOf { it } shouldBe 4421
    }

    @Test
    fun testTwo(input: List<String>) {
        val lines = readData(input)
        val grid = Array(1000) { _ -> IntArray(1000) { _ -> 0 } }
        lines.map { line ->
            drawLine(grid, line, true)
        }
        grid.map { it.count { it > 1 } }.sumOf { it } shouldBe 18674
    }

    private fun drawLine(grid: Array<IntArray>, line: Line, diagonal: Boolean = false) {
        if (line.x1 == line.x2) {
            val r = if (line.y1 < line.y2) (line.y1..line.y2) else (line.y2..line.y1)
            r.forEach { y ->
                grid[y][line.x1] += 1
            }
        }
        if (line.y1 == line.y2) {
            val r = if (line.x1 < line.x2) (line.x1..line.x2) else (line.x2..line.x1)
            r.forEach { x ->
                grid[line.y1][x] += 1
            }
        }

        if (diagonal && line.x1 != line.x2 && line.y1 != line.y2) {
            val upto = (line.y2 - line.y1).absoluteValue
            var x = line.x1
            var y = line.y1
            for (i in 0..upto) {
                grid[y][x] += 1
                x += if (line.x2 > x) 1 else -1
                y += if (line.y2 > y) 1 else -1
            }
        }
    }

    private fun readData(input: List<String>): List<Line> {
        return buildList {
            input.map {
                val (x1, y1, x2, y2) = it.split(",", " -> ")
                add(Line(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt()))
            }
        }
    }

    class Line(val x1: Int, val y1:Int, val x2: Int, val y2: Int)

    // I use large enough array for grid to draw the lines. But realised that no need of grid from Norbert solution
}