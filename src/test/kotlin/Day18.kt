import kotlin.math.max
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day18 {

    val sample = """
        [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
        [[[5,[2,8]],4],[5,[[9,9],0]]]
        [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
        [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
        [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
        [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
        [[[[5,4],[7,7]],8],[[8,3],8]]
        [[9,3],[[9,9],[6,[4,9]]]]
        [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
        [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
    """.trimIndent()

    @Test
    fun testOne(input: List<String>) {
        sum(sample.lines()).toString() shouldBe "[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]"
        sum(sample.lines()).magnitude() shouldBe 4140
        sum(input).magnitude() shouldBe 3756
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample.lines()) shouldBe 3993
        two(input) shouldBe 4585
    }

    fun two(input: List<String>): Int {
        var maxMagnitude = 0
        input.forEachIndexed { index1, line1 ->
            input.forEachIndexed { index2, line2 ->
                if (index1 != index2) {
                    maxMagnitude = max(maxMagnitude, (Fish(line1) + Fish(line2)).magnitude())
                }
            }
        }
        return maxMagnitude
    }

    private fun sum(input: List<String>): Fish {
        return input.map { Fish(it) }.reduce { acc, fish -> acc + fish }
    }

    class Fish(var body: String) {
        infix operator fun plus(other: Fish) = Fish("[$this,$other]").reduced()

        override fun toString() = body

        private fun reduced(): Fish {
            var more: Boolean
            do {
                more = explode() || split()
            } while (more)
            return this
        }

        private fun explode(): Boolean {
            val iterator = body.iterator().withIndex()
            var level = 0
            while (iterator.hasNext()) {
                val i = iterator.next()
                when (i.value) {
                    '[' -> level++
                    ']' -> level--
                }
                if (level == 5) {
                    body = buildString {
                        val before = body.substring(0, i.index)
                        val (left, right, after) = Regex("""(\d+),(\d+)](.+)""").matchEntire(body.substring(i.index + 1))!!.destructured
                        val matchBefore = Regex("""(.+?)(\d+)([^\d]+)""").matchEntire(before)
                        if (matchBefore == null) {
                            append(before)
                        } else {
                            append(matchBefore.groupValues.let { "${it[1]}${it[2].toInt() + left.toInt()}${it[3]}" })
                        }
                        append("0")
                        val matchAfter = Regex("""(.+?)(\d+)(.+)""").matchEntire(after)
                        if (matchAfter == null) {
                            append(after)
                        } else {
                            append(matchAfter.groupValues.let { "${it[1]}${it[2].toInt() + right.toInt()}${it[3]}" })
                        }
                    }
                    return true
                }
            }
            return false
        }

        private fun split(): Boolean {
            val m = Regex("""(.+?)(\d{2,})(.+)""").matchEntire(body) ?: return false
            val num = m.groupValues[2].toInt()
            body = "${m.groupValues[1]}[${num / 2},${(num + 1) / 2}]${m.groupValues[3]}"
            return true
        }

        fun magnitude(): Int {
            val r = Regex("""\[(\d+),(\d+)\]""")
            var after = body
            var before: String
            do {
                before = after
                after = r.replace(before) { m -> m.destructured.let { (l, r) -> (l.toInt() * 3 + r.toInt() * 2).toString() }}
            } while (before != after)
            return after.toInt()
        }
    }
}

// This was fun again.  I first wrote code to parse the fish strings into a tree.  But then I realized that this "modify
// the number left or right in the string" is much harder with the tree.  I then threw all of this away again and instead
// used regex to modify the body according to the instructions.  This went well (some minor regex errors along the way).
