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
        val fish = input.mapIndexed { i, f -> i to Fish(f) }.toMap()
        return fish.flatMap { (i, f) -> (fish - i).let { g -> g.values.map { f + it } } }.maxOf { it.magnitude() }
    }

    private fun sum(input: List<String>): Fish {
        val l = listOf(3,1,4)
        l.sumOf { it.toLong() }
        return input.map { Fish(it) }.reduce { acc, fish -> acc + fish }
    }

    class Fish(private var body: String) {
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
                        append(Regex("""(\d+)([^\d]+)$""").replaceFirst(before) { m -> m.destructured.let { (n, r) -> "${n.toInt() + left.toInt()}$r" }})
                        append("0")
                        append(Regex("""(\d+)""").replaceFirst(after) { m -> m.destructured.let { (n) -> (n.toInt() + right.toInt()).toString() }})
                    }
                    return true
                }
            }
            return false
        }

        private fun split(): Boolean {
            (Regex("""(.+?)(\d{2,})(.+)""").matchEntire(body) ?: return false).destructured.let { (before, num, after) ->
                val n = num.toInt()
                body = "$before[${n / 2},${(n + 1) / 2}]$after"
            }
            return true
        }

        fun magnitude(): Int {
            val r = Regex("""\[(\d+),(\d+)]""")
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

// This should really be part of stdlib, see https://youtrack.jetbrains.com/issue/KT-50372
fun Regex.replaceFirst(input: CharSequence, transform: (MatchResult) -> CharSequence): String {
    val length = input.length
    if (length == 0) return input.toString()

    val match: MatchResult = find(input) ?: return input.toString()

    val sb = StringBuilder(length)
    sb.append(input, 0, match.range.first)
    sb.append(transform(match))
    val lastStart = match.range.last + 1
    if (lastStart < length) {
        sb.append(input, lastStart, length)
    }

    return sb.toString()
}

// This was fun again.  I first wrote code to parse the fish strings into a tree.  But then I realized that this "modify
// the number left or right in the string" is much harder with the tree.  I then threw all of this away again and instead
// used regex to modify the body according to the instructions.  This went well (some minor regex errors along the way).
//
// Update 1: added the "missing" regex method and with that, code becomes nicer.  Would be even nicer if we also had a
// "replaceLast" method.
