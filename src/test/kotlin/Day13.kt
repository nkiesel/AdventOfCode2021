import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day13 {
    private val sample = """
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0

        fold along y=7
        fold along x=5
""".trimIndent()


    @Test
    fun testOne(input: List<String>) {
        one(sample.lines(), 1) shouldBe 17
        one(input, 1) shouldBe 610
    }

    @Test
    fun testTwo(input: List<String>) {
        one(sample.lines()) shouldBe 0
        one(input) shouldBe 0
    }

    data class Dot(val x: Int, val y: Int)

    data class Instruction(val axis: String, val amount: Int)

    private fun parse(input: List<String>): Pair<Set<Dot>, List<Instruction>> {
        val dots = mutableSetOf<Dot>()
        val instructions = mutableListOf<Instruction>()
        for (line in input) {
            when {
                line.contains(",") -> dots += line.split(",").map(String::toInt).let { Dot(it[0], it[1]) }
                line.startsWith("fold") -> instructions += line.split(" ")[2].split("=").let { Instruction(it[0], it[1].toInt())}
            }
        }
        return dots to instructions
    }

    private fun one(input: List<String>, folds: Int = Int.MAX_VALUE): Int {
        var (dots, instructions) = parse(input)
        for (instruction in instructions.take(folds)) {
            dots = when (instruction.axis) {
                "x" -> foldLeft(dots, instruction.amount)
                "y" -> foldUp(dots, instruction.amount)
                else -> dots
            }
        }

        if (folds != Int.MAX_VALUE) return dots.size
        display(dots)
        return 0
    }

    private fun foldLeft(dots: Set<Dot>, amount: Int): Set<Dot> {
        return buildSet {
            for (dot in dots) {
                when {
                    dot.x < amount -> add(dot)
                    else -> add(Dot(2 * amount - dot.x, dot.y))
                }
            }
        }
    }

    private fun foldUp(dots: Set<Dot>, amount: Int): Set<Dot> {
        return buildSet {
            for (dot in dots) {
                when {
                    dot.y < amount -> add(dot)
                    else -> add(Dot(dot.x, 2 * amount - dot.y))
                }
            }
        }
    }

    private fun display(dots: Set<Dot>) {
        val minX = dots.minOf { it.x }
        val maxX = dots.maxOf { it.x }
        val minY = dots.minOf { it.y }
        val maxY = dots.maxOf { it.y }
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                print(if (Dot(x, y) in dots) "#" else " ")
            }
            println()
        }
    }
}

// This was pretty simple (compared to yesterday). Looking at the input, it
// was obvious (again) that storing the matrix as a collection of dots was
// the right choice.  The only other revelation was that it was perfectly ok
// for the folding to produce negative coordinates. Thus, no shifting was
// required.
// This was (AFAIK) the first puzzle where the result was not immediately
// computable. I was tempted to implement some OCR, but then realized that
// I do not really know the shape of all the letters.
