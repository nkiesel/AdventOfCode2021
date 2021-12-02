import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02 {

    @Test
    fun testOneA(input: List<String>) {
        var h = 0
        var d = 0
        readData(input).forEach { (first, second) ->
            when(first) {
                "forward" -> h += second
                "up" -> d -= second
                "down" -> d += second
            }
        }
        (h * d) shouldBe 1714950
    }

    @Test
    fun testTwoA(input: List<String>) {
        var h = 0
        var d = 0
        var a = 0
        readData(input).forEach { (first, second) ->
            when(first) {
                "down" -> a += second
                "up" -> a -= second
                "forward" -> {
                    h += second
                    d += (a * second)
                }
            }
        }
        (h * d) shouldBe 1281977850
    }

    @Test
    fun testOneB(input: List<String>) {
        val commands = readCommands(input)
        commands.fold(Pair(0, 0)) {acc, command ->
            when (command) {
                is Command.Forward -> acc.copy(acc.first + command.unit, acc.second)
                is Command.Down -> acc.copy(acc.first, command.unit + acc.second)
                is Command.Up -> acc.copy(acc.first, acc.second - command.unit)
            }
        }.let {
            (it.first * it.second) shouldBe 1714950
        }
    }

    @Test
    fun testTwoB(input: List<String>) {
        val commands = readCommands(input)
        commands.fold(Triple(0, 0, 0)) { acc, command ->
            when (command) {
                is Command.Down -> acc.copy(acc.first, acc.second, acc.third + command.unit)
                is Command.Up -> acc.copy(acc.first, acc.second, acc.third - command.unit)
                is Command.Forward -> {
                    acc.copy(acc.first + command.unit, (acc.second + acc.third * command.unit), acc.third )
                }
            }
        }.let {
            (it.first * it.second) shouldBe 1281977850
        }
    }

    private fun readData(input: List<String>): Sequence<Pair<String, Int>> {
        return input.asSequence().map { s -> s.split(" ") }.map { it[0] to it[1].toInt() }
    }

    private fun readCommands(input: List<String>): Sequence<Command> {
        return input.asSequence().map { s -> s.split(" ") }.map {
            val unit = it[1].toInt()
            when(it[0]) {
                "forward" -> Command.Forward(unit)
                "down" -> Command.Down(unit)
                "up" -> Command.Up(unit)
                else -> error("Unknown Command")
            }
        }
    }

    sealed class Command(val unit: Int) {
        class Forward(unit: Int): Command(unit)
        class Down(unit: Int): Command(unit)
        class Up(unit: Int): Command(unit)
    }
}