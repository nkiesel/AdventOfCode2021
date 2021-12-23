import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day23 {

/*
        #############
        #...........#
        ###B#C#B#D###
          #A#D#C#A#
          #########

        #############
        #...........#
        ###B#B#D#D###  
          #C#C#A#A#
          #########
*/

    @Test
    fun testOne(input: List<String>) {
        solveOne(
            state("BCBD", "ADCA")
        ) shouldBe 12521
        solveOne(
            state("BBDD", "CCAA")
        ) shouldBe 10411
    }

    class State(private val rooms: Array<ArrayDeque<Char>>, private val hallway: CharArray = CharArray(11) { '.' }) {
        var cost = 0
        var count = 0

        companion object {
            private val stepCost = mapOf('A' to 1, 'B' to 10, 'C' to 100, 'D' to 1000)
            private val toNumbers = mapOf('A' to 1, 'B' to 2, 'C' to 3, 'D' to 4)
            private val roomGoals = listOf('A', 'B', 'C', 'D')
            var bestCostSoFar = Int.MAX_VALUE
            var bestStepsSoFar = emptyList<String>()
        }

        fun deepCopy() = State(rooms.map { ArrayDeque(it.toList()) }.toTypedArray(), hallway.toList().toCharArray()).also { it.cost = cost }

        private fun possibleFromRooms() = rooms.withIndex().filterNot { (index, room) -> room.all { it == roomGoals[index] } }

        private fun possibleToRoom(toNumber: Int) = rooms[toNumber - 1].takeIf { r -> r.all { it == roomGoals[toNumber - 1] } }

        fun done() = hallway.all { it == '.' } && rooms.withIndex().all { (index, room) -> room.all { it == roomGoals[index] } }

        private fun freeHallway(from: Int, to: Int) = hallway.slice(min(from, to)..max(from, to)).all { it == '.' }

        fun next(): List<String> {
            return buildList {
                for (r in possibleFromRooms()) {
                    val fromNumber = r.index + 1
                    val move = r.value.last()
                    val toNumber = toNumbers[move]!!
                    val hi = fromNumber * 2
                    if (hallway[hi] != '.') break

                    val toRoom = possibleToRoom(toNumber)
                    if (toRoom != null && freeHallway(hi, toNumber * 2)) {
                        add("$fromNumber$toNumber")
                    }

                    for (i in hi - 1 downTo 0) {
                        if (hallway[i] != '.') break
                        if (i != 2 && i != 4 && i != 6 && i != 8) add("${fromNumber}l${hi - i}")
                    }
                    for (i in hi + 1 until hallway.size) {
                        if (hallway[i] != '.') break
                        if (i != 2 && i != 4 && i != 6 && i != 8) add("${fromNumber}r${i - hi}")
                    }
                }

                for (h in hallway.indices) {
                    val move = hallway[h]
                    if (move == '.') continue
                    val toNumber = toNumbers[move]!!
                    val toHallwayIndex = toNumber * 2
                    val toRoom = possibleToRoom(toNumber)
                    if (toRoom != null) {
                        if (toHallwayIndex < h && freeHallway(toHallwayIndex, h - 1)) {
                            add("r$toNumber")
                        } else if (toHallwayIndex > h && freeHallway(h + 1, toHallwayIndex)) {
                            add("l$toNumber")
                        }
                    }
                }
            }
        }

        fun step(s: String, print: Boolean = true): State {
            val possible = next()
            if (print) println(possible)
            require(s in possible) { "impossible move $s" }

            val from = s[0]
            val to = s[1]
            val horizontal = s.drop(2).takeUnless { it.isEmpty() }?.toInt() ?: 0

            val (move, actualSteps) = when (from) {
                'l' -> left(to)
                'r' -> right(to)
                else -> room(from, to, horizontal)
            }
            val stepCost = stepCost[move]!! * actualSteps
            cost += stepCost
            count++
            if (print) {
                render("$count: $move $from$to${if (to == 'l' || to == 'r') "$actualSteps" else ""}")
                println("cost step=$stepCost total=$cost")
                println()
            }
            return this
        }

        private fun left(to: Char): Pair<Char, Int> {
            val toNumber = to.digitToInt()
            val toRoom = rooms[toNumber - 1]
            val stepsIntoRoom = if (toRoom.isEmpty()) 2 else 1
            var pos = toNumber * 2
            while (hallway[pos] == '.') pos--
            val move = hallway[pos]
            hallway[pos] = '.'
            toRoom.addLast(move)

            return Pair(move, toNumber * 2 - pos + stepsIntoRoom)
        }

        private fun right(to: Char): Pair<Char, Int> {
            val toNumber = to.digitToInt()
            val toRoom = rooms[toNumber - 1]
            val stepsIntoRoom = if (toRoom.isEmpty()) 2 else 1
            var pos = toNumber * 2
            while (hallway[pos] == '.') pos++
            val move = hallway[pos]
            hallway[pos] = '.'
            toRoom.addLast(move)
            return Pair(move, pos - toNumber * 2 + stepsIntoRoom)
        }

        private fun room(from: Char, to: Char, horizontal: Int = 0): Pair<Char, Int> {
            val fromNumber = from.digitToInt()
            val toNumber = to.digitToIntOrNull() ?: 1
            val fromRoom = rooms[fromNumber - 1]
            val toRoom = rooms[toNumber - 1]
            val move = fromRoom.removeLast()
            val stepsToHallway = if (fromRoom.isEmpty()) 2 else 1
            return Pair(move, stepsToHallway + when (to) {
                'l' -> (horizontal).also { hallway[fromNumber * 2 - horizontal] = move }
                'r' -> (horizontal).also { hallway[fromNumber * 2 + horizontal] = move }
                else -> (abs(fromNumber - toNumber) * 2 + (if (toRoom.isEmpty()) 2 else 1)).also { toRoom.addLast(move) }
            })
        }

        fun render(steps: String? = null) {
            steps?.let { println("  $steps") }
            println("#############")
            println("#${hallway.joinToString("")}#")
            println("###${top(rooms[0])}#${top(rooms[1])}#${top(rooms[2])}#${top(rooms[3])}###")
            println("  #${bottom(rooms[0])}#${bottom(rooms[1])}#${bottom(rooms[2])}#${bottom(rooms[3])}#")
            println("  #########")
        }

        fun bottom(q: ArrayDeque<Char>) = if (q.size >= 1) q.first() else "."
        fun top(q: ArrayDeque<Char>) = if (q.size >= 2) q.last() else "."
    }

    fun one(state: State, steps: List<String>, print: Boolean = true): Int {
        with(state) {
            if (print) {
                render()
                println()
            }
            steps.forEach { s -> step(s) }
            return cost
        }
    }

    private fun solveOne(state: State): Int {
        val steps = listOf<String>()
        findAll(state, steps)
        println("-".repeat(60))
        return State.bestCostSoFar
    }

    private fun state(top: String, bottom: String) = State(
        arrayOf(
            ArrayDeque(listOf(bottom[0], top[0])),
            ArrayDeque(listOf(bottom[1], top[1])),
            ArrayDeque(listOf(bottom[2], top[2])),
            ArrayDeque(listOf(bottom[3], top[3])),
        )
    )

    private fun findAll(state: State, previous: List<String>): List<List<String>>? {
        require(previous.size <= 16) { "Too long steps: $previous" }
        if (state.cost >= State.bestCostSoFar) return null

        if (state.done()) {
            if (state.cost < State.bestCostSoFar) {
                println("${state.cost}: $previous")
                State.bestCostSoFar = state.cost
                State.bestStepsSoFar = previous
            }
            return null
        }

        val next = state.next()
        if (next.isEmpty()) return null

        return next.mapNotNull { s -> findAll(state.deepCopy().step(s, false), previous + s) }.flatten()
    }
}
