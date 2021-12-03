import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02 {
    private val sample = listOf("forward 5", "down 5", "forward 8", "up 3", "down 8", "forward 2")

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 150L

        one(input) shouldBe 1524750L

        oneA(input) shouldBe 1524750L
        oneB(input) shouldBe 1524750L
        oneC(input) shouldBe 1524750L
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample) shouldBe 900L

        two(input) shouldBe 1592426537L

        twoA(input) shouldBe 1592426537L
        twoB(input) shouldBe 1592426537L
        twoC(input) shouldBe 1592426537L
        twoOverEngineered(input) shouldBe 1592426537L
    }

    // Straight-forward loop
    private fun one(input: List<String>): Long {
        var horizontal = 0L
        var depth = 0L
        for (line in input) {
            val (command, amount) = line.split(" ").let { Pair(it[0], it[1].toInt()) }
            when (command) {
                "up" -> depth -= amount
                "down" -> depth += amount
                "forward" -> horizontal += amount
            }
        }
        return horizontal * depth
    }

    // Straight-forward loop
    private fun two(input: List<String>): Long {
        var horizontal = 0L
        var depth = 0L
        var aim = 0L
        for (line in input) {
            val (command, amount) = line.split(" ").let { Pair(it[0], it[1].toInt()) }
            when (command) {
                "up" -> aim -= amount
                "down" -> aim += amount
                "forward" -> {
                    horizontal += amount; depth += aim * amount
                }
            }
        }
        return horizontal * depth
    }

    // Using an "operation map". However, the state is not managed in that map
    private fun oneA(input: List<String>): Long {
        var horizontal = 0L
        var depth = 0L
        val ops = mapOf(
            "up" to fun(amount: Int) { depth -= amount },
            "down" to fun(amount: Int) { depth += amount },
            "forward" to fun(amount: Int) { horizontal += amount },
        )
        input.map { it.split(" ") }.forEach { (command, amount) -> ops[command]?.invoke(amount.toInt()) }
        return horizontal * depth
    }

    // Using an "operation map". However, the state is not managed in that map
    private fun twoA(input: List<String>): Long {
        var horizontal = 0L
        var depth = 0L
        var aim = 0L
        val ops = mapOf(
            "up" to { amount: Int -> aim -= amount },
            "down" to { amount: Int -> aim += amount },
            "forward" to { amount: Int -> horizontal += amount; depth += aim * amount },
        )
        input.map { it.split(" ") }.forEach { (command, amount) -> ops[command]?.invoke(amount.toInt()) }
        return horizontal * depth
    }

    // Using an immutable state together with an operation map which computes the new state
    private fun oneB(input: List<String>): Long {
        data class State(val horizontal: Long, val depth: Long) {
            fun compute() = horizontal * depth
        }

        val ops = mapOf(
            "up" to fun(state: State, amount: Int) = state.copy(depth = state.depth - amount),
            "down" to fun(state: State, amount: Int) = state.copy(depth = state.depth + amount),
            "forward" to fun(state: State, amount: Int) = state.copy(horizontal = state.horizontal + amount),
        )
        return input.map { it.split(" ") }.fold(State(0L, 0L)) { acc, line -> ops[line[0]]?.invoke(acc, line[1].toInt()) ?: acc }.compute()
    }

    // Using an immutable state together with an operation map which computes the new state
    private fun twoB(input: List<String>): Long {
        data class State(val horizontal: Long, val depth: Long, val aim: Long) {
            fun compute() = horizontal * depth
        }

        val ops = mapOf(
            "up" to fun(state: State, amount: Int) = state.copy(aim = state.aim - amount),
            "down" to fun(state: State, amount: Int) = state.copy(aim = state.aim + amount),
            "forward" to fun(state: State, amount: Int) = state.copy(horizontal = state.horizontal + amount, depth = state.depth + state.aim * amount),
        )
        return input.map { it.split(" ") }.fold(State(0L, 0L, 0L)) { acc, line -> ops[line[0]]?.invoke(acc, line[1].toInt()) ?: acc }.compute()
    }

    // Using a mutable state with a "process" method to update it
    private fun oneC(input: List<String>): Long {
        class State(var horizontal: Long, var depth: Long) {
            fun process(command: String, amount: Int): State {
                when (command) {
                    "up" -> depth -= amount
                    "down" -> depth += amount
                    "forward" -> horizontal += amount
                }
                return this
            }

            fun compute() = horizontal * depth
        }
        return input.map { it.split(" ") }.fold(State(0L, 0L)) { acc, line -> acc.process(line[0], line[1].toInt()) }.compute()
    }

    // Using a mutable state with a "process" method to update it
    private fun twoC(input: List<String>): Long {
        class State(var horizontal: Long, var depth: Long, var aim: Long) {
            fun process(command: String, amount: Int): State {
                when (command) {
                    "up" -> aim -= amount
                    "down" -> aim += amount
                    "forward" -> {
                        horizontal += amount; depth += aim * amount
                    }
                }
                return this
            }

            fun compute() = horizontal * depth
        }
        return input.map { it.split(" ") }.fold(State(0L, 0L, 0L)) { acc, line -> acc.process(line[0], line[1].toInt()) }.compute()
    }

    // Below is a completely over-engineered solution combining my idea of an explicit State
    // object with Prasad's idea of a Command class. Only implemented for part 2.

    class State(var horizontal: Long, var depth: Long, var aim: Long) {
        fun compute() = horizontal * depth
    }
    private fun initialState() = State(0L, 0L, 0L)

    sealed class Command {
        abstract fun process(state: State): State
    }

    class Up(private val amount: Int) : Command() {
        constructor(parameter: String) : this(parameter.toInt())
        override fun process(state: State) = state.apply { aim -= amount }
    }

    class Down(private val amount: Int) : Command() {
        constructor(parameter: String) : this(parameter.toInt())
        override fun process(state: State) = state.apply { aim += amount }
    }

    class Forward(private val amount: Int) : Command() {
        constructor(parameter: String) : this(parameter.toInt())
        override fun process(state: State) = state.apply {
            horizontal += amount
            depth += aim * amount
        }
    }

    private fun List<String>.toCommands() = this
        .map { it.split(" ", limit = 2) }
        .mapNotNull { (command, parameter) ->
            when (command) {
                "up" -> Up(parameter)
                "down" -> Down(parameter)
                "forward" -> Forward(parameter)
                else -> null
            }
        }

    private fun twoOverEngineered(input: List<String>): Long {
        return input.toCommands().fold(initialState()) { state, cmd -> cmd.process(state) }.compute()
    }
}

// The explicit loop solution was the most intuitive to me, but then I thought that having a way to provide an "operation map"
// might be nicer, leading to the xxxA variants. The xxxB and xxxC variants use "fold" to compute a final state from an initial
// state and the input. What I like about them is that they nicely encapsulate the submarine state and methods to update it.
//
// Learning from previous puzzles, I tend to always use "Long" instead of "Int" for computations to reduce the risk of value
// overruns.
//
// After seeing Prasad's solution, I thought about a more abstract problem description: our submarine has a state (initially depth
// and forward progress, and then also aim), and a sequence of commands which manipulate that state.  This lead to the completely
// over-engineered solution. It might pay off if future puzzles pick up on this problem and add more state and commands.
