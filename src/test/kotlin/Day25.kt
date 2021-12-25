import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day25 {

    val sample = """
        v...>>.vv>
        .vv>>.vv..
        >>.>v>...v
        >>v>>.>.v.
        v>v.vv.v..
        >.>>..v...
        .vv..>.>v.
        v.v..>>v.v
        ....v..v.>
    """.trimIndent()

    @Test
    fun testOne(input: List<String>) {
        one(sample.lines()) shouldBe 58
        one(input) shouldBe 400
    }

    fun one(input: List<String>): Int {
        var floor = parse(input)
        var steps = 0
        var moved: Boolean
        do {
            moved = false
            steps++

            var new = floor.map { it.copyOf() }.toTypedArray()

            for (ri in floor.indices) {
                for (ci in floor[ri].indices) {
                    if (floor[ri][ci] == '>') {
                        val ni = (ci + 1) % floor[ri].size
                        if (floor[ri][ni] == '.') {
                            new[ri][ci] = '.'
                            new[ri][ni] = '>'
                            moved = true
                        }
                    }
                }
            }

            floor = new
            new = floor.map { it.copyOf() }.toTypedArray()

            for (ci in floor[0].indices) {
                for (ri in floor.indices) {
                    if (floor[ri][ci] == 'v') {
                        val ni = (ri + 1) % floor.size
                        if (floor[ni][ci] == '.') {
                            new[ri][ci] = '.'
                            new[ni][ci] = 'v'
                            moved = true
                        }
                    }
                }
            }

            floor = new
        } while (moved)

        return steps
    }

    fun parse(input: List<String>): Array<CharArray> {
        return input.map { it.toCharArray() }.toTypedArray()
    }

}
