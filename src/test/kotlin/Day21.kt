import kotlin.math.max
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day21 {
    val sample = """
        Player 1 starting position: 4
        Player 2 starting position: 8
    """.trimIndent()

    @Test
    fun testOne(input: List<String>) {
        one(parse(sample.lines())) shouldBe 739785
        one(parse(input)) shouldBe 571032
    }

    @Test
    fun testTwo(input: List<String>) {
        two(parse(sample.lines())) shouldBe 444356092776315L
        two(parse(input)) shouldBe 49975322685009L
    }

    class Player(val n: Int, var position: Int, var points: Int = 0) {
        fun score(rolled: Int): Boolean {
            position = (position + rolled) % 10
            points += position + 1
            return points >= 1000
        }
    }

    private fun parse(input: List<String>): List<Player> {
        return input.map { line -> Regex("""Player (\d+) starting position: (\d+)""")
            .matchEntire(line)!!.let { Player(it.groupValues[1].toInt(), it.groupValues[2].toInt() - 1) } }
    }

    class Die {
        var value = 0
        var rolled = 0
        val die = sequence {
            while (true) yield (value++ % 100 + 1)
        }
        fun roll(n: Int = 1) = die.take(n).sum().also { rolled += n}
    }

    private fun one(players: List<Player>): Int {
        val die = Die()
        while (true) {
            players.forEachIndexed { index, player ->
                if (player.score(die.roll(3))) {
                    return players[if (index == 0) 1 else 0].points * die.rolled
                }
            }
        }
    }

    data class Universe(var score1: Int, var score2: Int, var position1: Int, var position2: Int, var turn: Int, var count: Long) {
        fun score(n: Long) = outcomes.map { s -> copy(count = n).score(s) }

        private fun score(s: Int): Universe {
            if (turn == 1) {
                position1 = (position1 + s) % 10
                score1 += position1 + 1
                turn = 2
            } else {
                position2 = (position2 + s) % 10
                score2 += position2 + 1
                turn = 1
            }
            return this
        }

        companion object {
            val outcomes = listOf(1, 2, 3).let { x -> x.flatMap { a -> x.flatMap { b -> x.map { c -> a + b + c } } } }
        }
    }


    private fun two(players: List<Player>): Long {
        val first = Universe(0, 0, players[0].position, players[1].position, 1, 0L)
        var universes = mapOf(first to 1L)
        var w1 = 0L
        var w2 = 0L
        while (universes.isNotEmpty()) {
//            println("w1: $w1 w2: $w2 u: ${universes.size}")
            val nextUniverses = universes.entries.flatMap { (u, n) -> u.score(n) }.mapNotNull { u ->
                when {
                    u.score1 >= 21 -> { w1 += u.count; null }
                    u.score2 >= 21 -> { w2 += u.count; null }
                    else -> u
                }
            }
            universes = nextUniverses.groupingBy { it }.fold(0L) { acc, u -> acc + u.count }
        }
        return max(w1, w2)
    }
}
