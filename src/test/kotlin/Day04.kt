import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04 {
    private val sample = """
7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7
""".trimIndent()

    @Test
    fun testOne(input: List<String>) {
        one(sample.lines()) shouldBe 4512
        one(input) shouldBe 72770
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample.lines()) shouldBe 1924
        two(input) shouldBe 13912
    }

    class Board(numbers: List<Int>) {
        private val nums = numbers.toMutableSet()
        // we precompute the rows and cols
        private val rows = (0..4).map { x -> MutableList(5) { y -> numbers[x * 5 + y] } }
        private val cols = (0..4).map { x -> MutableList(5) { y -> numbers[x + 5 * y] } }

        fun winner(n: Int): Boolean {
            return if (nums.remove(n)) {
                // replace matching numbers with 0 in rows and cols
                rows.forEach { r -> r.replaceAll { if (it == n) 0 else it } }
                cols.forEach { c -> c.replaceAll { if (it == n) 0 else it } }
                // we have a wining board if any row or col only contains 0s
                rows.any { it.sum() == 0 } || cols.any { it.sum() == 0 }
            } else {
                false
            }
        }

        // we only need to add up either all the rows or all the cols
        fun unmarkedSum() = rows.sumOf { it.sum() }
    }

    private fun one(input: List<String>): Int {
        val numbers = input[0].split(",").map(String::toInt)
        val boards = input
            .drop(1) // drop number line
            .chunked(6) // break into groups of 6 lines
                // find all numbers in the group and create a board from the resulting list
            .map { board -> Board(Regex("""\d+""").findAll(board.joinToString(" "))
                .map { it.groupValues[0].toInt() }.toList()) }

        for (n in numbers) {
            for (board in boards) {
                if (board.winner(n)) return board.unmarkedSum() * n
            }
        }
        return 0
    }

    private fun two(input: List<String>): Int {
        val numbers = input[0].split(",").map(String::toInt)
        val boards = input
            .drop(1)
            .chunked(6)
            .map { board -> Board(Regex("""\d+""").findAll(board.joinToString(" "))
                .map { it.groupValues[0].toInt() }.toList()) }
            .toMutableList()

        for (n in numbers) {
            val winners = boards.filter { it.winner(n) }
            boards.removeAll(winners)
            if (boards.isEmpty()) return winners.last().unmarkedSum() * n
        }
        return 0
    }

    class SmartBoard(numbers: List<Int>) {
        private val nums = numbers.toMutableSet()
        // we precompute the rows and cols
        private val rows = (0..4).map { x -> MutableList(5) { y -> numbers[x * 5 + y] } }
        private val cols = (0..4).map { x -> MutableList(5) { y -> numbers[x + 5 * y] } }
        private var winner = false

        fun winner(n: Int): Boolean {
            if (winner) return true
            if (!nums.remove(n)) return false
            // replace matching numbers with 0 in rows and cols
            rows.forEach { r -> r.replaceAll { if (it == n) 0 else it } }
            cols.forEach { c -> c.replaceAll { if (it == n) 0 else it } }
            // we have a wining board if any row or col only contains 0s
            winner = rows.any { it.sum() == 0 } || cols.any { it.sum() == 0 }
            return winner
        }

        // we only need to add up either all the rows or all the cols
        fun unmarkedSum() = rows.sumOf { it.sum() }
    }

}

// I quickly came up with the idea to store both rows and cols for every board and to replace struck out numbers with 0.
// That drastically simplifies detecting winning boards.  I spent most of the development time converting the input into
// boards. The break-through was to use "findAll" which avoids empty strings etc.
//
// After submitting the results, I thought about avoiding to process boards which do not contain the drawn number.
// Does not really make a difference for the runtime of the given input, but looks nicer.  Modified code is the unused
// "SmartBoard" class.
