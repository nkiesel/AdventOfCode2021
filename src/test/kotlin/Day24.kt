import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day24 {

    @Test
    fun testOne(input: List<String>) {
        one(input) shouldBe 51939397989999L
    }

    fun one(input: List<String>): Long {
        val alu = Alu(input, 9L downTo 1L)
//        println(alu)
        alu.level(0, LongArray(4))
        return alu.solution.joinToString("") { it.toString() }.toLong()
    }

    @Test
    fun testTwo(input: List<String>) {
        // TODO: this fails: `two` never finds a valid model number
        two(input) shouldBe 11111111111111L
    }

    fun two(input: List<String>): Long {
        val alu = Alu(input, 1L..9L)
//        println(alu)
        alu.level(0, LongArray(4))
        return alu.solution.joinToString("") { it.toString() }.toLong()
    }

    sealed class Instruction(val a: Int) {
        abstract fun eval(registers: LongArray)
    }

    abstract class ConstantInstruction(a: Int, val amount: Long) : Instruction(a) {
        override fun toString(): String {
            return "${this::class.simpleName} ${'w' + a} $amount"
        }
    }

    abstract class RegisterInstruction(a: Int, val b: Int) : Instruction(a) {
        override fun toString(): String {
            return "${this::class.simpleName} ${'w' + a} ${'w' + b}"
        }

    }

    class AddConstant(a: Int, amount: Long) : ConstantInstruction(a, amount) {
        override fun eval(registers: LongArray) {
            registers[a] += amount
        }
    }

    class AddRegister(a: Int, b: Int) : RegisterInstruction(a, b) {
        override fun eval(registers: LongArray) {
            registers[a] += registers[b]
        }
    }

    class MulConstant(a: Int, amount: Long) : ConstantInstruction(a, amount) {
        override fun eval(registers: LongArray) {
            registers[a] *= amount
        }
    }

    class MulRegister(a: Int, b: Int) : RegisterInstruction(a, b) {
        override fun eval(registers: LongArray) {
            registers[a] *= registers[b]
        }
    }

    class DivConstant(a: Int, amount: Long) : ConstantInstruction(a, amount) {
        override fun eval(registers: LongArray) {
            registers[a] /= amount
        }
    }

    class DivRegister(a: Int, b: Int) : RegisterInstruction(a, b) {
        override fun eval(registers: LongArray) {
            registers[a] /= registers[b]
        }
    }

    class ModConstant(a: Int, amount: Long) : ConstantInstruction(a, amount) {
        override fun eval(registers: LongArray) {
            registers[a] %= amount
        }
    }

    class ModRegister(a: Int, b: Int) : RegisterInstruction(a, b) {
        override fun eval(registers: LongArray) {
            registers[a] %= registers[b]
        }
    }

    class EqlConstant(a: Int, amount: Long) : ConstantInstruction(a, amount) {
        override fun eval(registers: LongArray) {
            registers[a] = if (registers[a] == amount) 1 else 0
        }
    }

    class EqlRegister(a: Int, b: Int) : RegisterInstruction(a, b) {
        override fun eval(registers: LongArray) {
            registers[a] = if (registers[a] == registers[b]) 1 else 0
        }
    }

    class LevelState {
        private val states: MutableMap<Int, MutableList<LongArray>> = mutableMapOf()

        fun add(s: LongArray): Boolean {
            val h = s.contentHashCode()
            var l = states[h]
            if (l != null && l.any { it.contentEquals(s) }) return false
            if (l == null) {
                l = mutableListOf()
                states[h] = l
            }
            l += s
            return true
        }
    }

    class Alu(program: List<String>, private val digits: LongProgression) {
        private val levelPrograms = buildList {
            val reader = program.drop(1).iterator()
            var current = mutableListOf<Instruction>()
            while (reader.hasNext()) {
                val (op, args) = reader.next().split(" ", limit = 2)
                if (op == "inp") {
                    add(current)
                    current = mutableListOf()
                } else {
                    val a = args[0] - 'w'
                    val b = if (args[2].isLetter()) args[2] - 'w' else null
                    val amount = if (b == null) args.drop(2).toLong() else null
                    current.add(
                        when (op) {
                            "add" -> if (b == null) AddConstant(a, amount!!) else AddRegister(a, b)
                            "mul" -> if (b == null) MulConstant(a, amount!!) else MulRegister(a, b)
                            "div" -> if (b == null) DivConstant(a, amount!!) else DivRegister(a, b)
                            "mod" -> if (b == null) ModConstant(a, amount!!) else ModRegister(a, b)
                            "eql" -> if (b == null) EqlConstant(a, amount!!) else EqlRegister(a, b)
                            else -> error("Invalid instruction")
                        }
                    )
                }
            }
            add(current)
        }

        val solution = LongArray(14)
        private val levelState = Array(14) { LevelState() }

        fun level(level: Int, parentState: LongArray): Boolean {
            for (w in digits) {
                //                if (level < 3) println("${Instant.now()} working on $w for level $level")
                val registers = longArrayOf(w, 0, 0, parentState[3])
                solution[level] = w
                levelPrograms[level].forEach { it.eval(registers) }
                registers[1] = 0 // cheating
                registers[2] = 0 // cheating
                if (level == levelPrograms.lastIndex) {
                    return registers[3] == 0L
                } else if (levelState[level].add(registers)) {
                    if (level(level + 1, registers)) return true
                }
            }
            return false
        }

        override fun toString(): String {
            return "Alu(levelPrograms=${levelPrograms.size}, solution=${solution.contentToString()})\n" + levelPrograms[0].joinToString("\n")
        }
    }
}
