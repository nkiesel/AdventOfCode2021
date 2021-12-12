import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day12 {
    private val sample1 = """
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end
""".trimIndent()

    private val sample2 = """
        dc-end
        HN-start
        start-kj
        dc-start
        dc-HN
        LN-dc
        HN-end
        kj-sa
        kj-HN
        kj-dc
""".trimIndent()

    private val sample3 = """
        fs-end
        he-DX
        fs-he
        start-DX
        pj-DX
        end-zg
        zg-sl
        zg-pj
        pj-he
        RW-he
        fs-DX
        pj-RW
        zg-RW
        start-pj
        he-WI
        zg-he
        pj-fs
        start-RW
    """.trimIndent()

    @Test
    fun testOne(input: List<String>) {
        one(sample1.lines()) shouldBe 10
        one(sample2.lines()) shouldBe 19
        one(sample3.lines()) shouldBe 226
        one(input) shouldBe 4011
    }

    @Test
    fun testTwo(input: List<String>) {
        one(sample1.lines(), true) shouldBe 36
        one(sample2.lines(), true) shouldBe 103
        one(sample3.lines(), true) shouldBe 3509
        one(input, true) shouldBe 108035
    }

    data class Edge(val start: String, val end: String)

    private fun one(input: List<String>, allowDouble: Boolean = false): Int {
        val caves = input.map { line -> line.split("-").let { it[0] to it[1] } }
        val allEdges = (caves + caves.map { it.second to it.first })
            .filter { it.second != "start" && it.first != "end" }
            .map { Edge(it.first, it.second) }

        return paths(listOf("start"), allEdges, allowDouble).filter { it.last() == "end" }.size
    }

    private fun paths(start: List<String>, edges: List<Edge>, allowDouble: Boolean): List<List<String>> {
        val next = edges.filter { it.start == start.last() }.map { it.end }
        if (next.isEmpty()) return listOf(start)
        return next.flatMap { paths(start + it, reduce(edges, start, it, allowDouble), allowDouble) }
    }

    private fun reduce(edges: List<Edge>, start: List<String>, next: String, allowDouble: Boolean): List<Edge> {
        return when {
            next[0].isUpperCase() -> edges
            !start.contains(next) -> edges
            allowDouble && start.filter { it[0].isLowerCase() }.groupingBy { it }.eachCount().maxOf { it.value } == 1 -> edges
            else -> edges.filter { it.start != next && it.end != next }
        }
    }
}

// We can infer from the description that 2 big caves will never be directly connected. Otherwise,
// we could extend any path with visits any of these 2 big caves indefinitely by moving back and
// forth between them.
//
// This solution comes close to the "too slow for comfort" limit: testTwo takes 0.7 seconds.  Pretty
// sure I compute too many paths and the recursion is getting too deep. I guess there will be a
// much faster iterative solution, but I could not come up with it (yet).
