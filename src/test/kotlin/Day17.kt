import kotlin.math.max
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day17 {
    @Test
    fun testOne(input: List<String>) {
        one("target area: x=20..30, y=-10..-5") shouldBe 45
        one(input.first()) shouldBe 17766
    }

    @Test
    fun testTwo(input: List<String>) {
        two("target area: x=20..30, y=-10..-5") shouldBe 112
        two(input.first()) shouldBe 1733
    }

    private fun one(input: String): Int {
        val (xRange, yRange) = parse(input)
        var xFirst = 1
        while (xFirst * (xFirst + 1) / 2 < xRange.first) xFirst++
        var xLast = xFirst
        while (xLast * (xLast + 1) / 2 <= xRange.last) xLast++
        var maxY = Int.MIN_VALUE
        for (x in xFirst until xLast) {
            val yFirst = (yRange.last + (x * (x + 1) / 2)) / x
            for (y in (yFirst..yFirst + 10000)) {
                shoot(x, y, xRange, yRange)?.let {  maxY = max(maxY, it) }
            }
        }
        return maxY
    }

    private fun shoot(x: Int, y: Int, xRange: IntRange, yRange: IntRange): Int? {
        var px = 0
        var py = 0
        var vx = x
        var vy = y
        var maxY = 0

        while (py >= yRange.first) {
            if (px in xRange && py in yRange) return maxY
            px += vx
            py += vy
            if (py > maxY) maxY = py
            if (vx > 0) vx -= 1
            vy -= 1
        }
        return null
    }

    private fun parse(input: String): Pair<IntRange, IntRange> {
        return Regex("""-?\d+""").findAll(input).map { it.groupValues[0].toInt() }.toList().let { it[0]..it[1] to it[2]..it[3] }
    }

    private fun two(input: String): Int {
        val (xRange, yRange) = parse(input)
        var xFirst = 1
        while (xFirst * (xFirst + 1) / 2 < xRange.first) xFirst++
        var result = 0
        for (x in xFirst..xRange.last) {
            for (y in (yRange.first..yRange.last + 10000)) {
                if (shoot(x, y, xRange, yRange) != null) {
                    result++
                }
            }
        }
        return result
    }
}

// Definitely not proud of this solution!  This is a brute-force solution combined with
// guessing upper bounds (esp. the "10000"!).
//
// The thought into part 1 was that a maximum y will always happen if the probe no longer
// moves forward when it hits the target area. Therefore, xFirst is the smallest number
// which will make the probe ever reach the target area, and xLast is the smallest number
// which will make it overshoot the target area when forward velocity is 0.
// We can then calculate the minimum vertical start velocity required to hit the target
// area.  However, I still have no idea how to compute the upper bound for this value. In
// theory, a really large number should be able to still hot the target area on the way
// down.
// The second part is similar.  However, now direct shots are possible as well. Thus, we
// iterate over the range from the minimum x to the far edge of the target area, because
// a larger initial x velocity will then overshoot the target area on the first shot. The
// smallest y initial value cannot be smaller than the lower y target area because otherwise
// the fist shot already end up under the target area.  Again, I have no idea how to compute
// (or even guess with confidence) the upper bound.
