import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04 {

    @Test
    fun testOne(input: List<String>) {
        one(input) shouldBe 31424
    }

    private fun one(input: List<String>): Int {
        val numbers: List<Int> = input.first().split(",").map(String::toInt).toList()
        val boards = boards(input)
        for (n in numbers) {
            for (board in boards) {
                if(board.win(n)) {
                    return board.score(n)
                }
            }
        }
        return 0
    }

    @Test
    fun testTwo(input: List<String>) {
        two(input) shouldBe 23042
    }

    private fun two(input: List<String>): Int {
        val numbers: List<Int> = input.first().split(",").map(String::toInt).toList()
        val boards = boards(input).toMutableList()
        for (n in numbers) {
            val winners = boards.filter { board -> board.win(n) }
            boards.removeAll(winners)
            if (boards.isEmpty()) return winners.last().score(n)
        }
        return 0
    }

    private fun boards(input: List<String>): List<Board> {
        val w = input.drop(1).filter { it.isNotEmpty() }.windowed(5, 5)
        return buildList {
            w.forEach {
                val cells = buildList {
                    it.forEach { str ->
                        add(str.split(" ").filter { it.isNotEmpty() }.map { Cell(it.toInt())}.toMutableList())
                    }
                }
                add(Board(cells))
            }
        }
    }

    data class Cell(val num: Int, var sel: Boolean = false)

    class Board(private val cells: List<List<Cell>>) {

        private fun selectNumber(n: Int) {
            cells.map { it.map { if (it.num == n) it.sel = true } }
        }

        fun win(n: Int): Boolean {
            selectNumber(n)
            fun checkRow() = cells.any { it.all { it.sel } }
            fun checkCol(): Boolean {
                val colSize = cells.first().size
                val rowSize = cells.size
                for (i in 0 until colSize) {
                    val selected = (0 until rowSize).all { cells[it][i].sel }
                    if (selected) return true
                }
                return false
            }
            return checkRow() || checkCol()
        }

        fun score(n: Int): Int {
            val sum = cells.flatten().filter { !it.sel }.sumOf { it.num }
            return sum * n
        }
    }
}