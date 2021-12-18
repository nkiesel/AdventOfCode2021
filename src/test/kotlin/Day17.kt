import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.sign
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

    private fun parse(input: String): Pair<IntRange, IntRange> {
        val (xRange, yRange) = Regex("""-?\d+""").findAll(input).map { it.groupValues[0].toInt() }.toList().let { it[0]..it[1] to it[2]..it[3] }
        require(!xRange.isEmpty()) { "empty target area not supported" }
        require(!yRange.isEmpty()) { "empty target area not supported" }
        require(xRange.first > 0) { "target area must be to the right of the start" }
        require(yRange.last < 0) { "target area must be below the start" }
        return Pair(xRange, yRange)
    }

    private fun one(input: String) = hits(input).maxOf { it }

    private fun two(input: String) = hits(input).count()

    private fun hits(input: String): List<Int> {
        val (xRange, yRange) = parse(input)
        return (1..xRange.last).flatMap { x ->
            (yRange.first..(yRange.first.absoluteValue + x)).mapNotNull { y ->
                shoot(x, y, xRange, yRange)
            }
        }
    }

    private fun shoot(x: Int, y: Int, xRange: IntRange, yRange: IntRange): Int? {
        var px = 0
        var py = 0
        var maxY = 0
        var vx = x
        var vy = y

        while (px <= xRange.last && py >= yRange.first) {
            if (px in xRange && py in yRange) return maxY
            px += vx
            py += vy
            maxY = max(py, maxY)
            vx -= vx.sign
            vy -= 1
        }
        return null
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
//
// Update 1: after reading a bit on Slack, someone claimed that - as long as the initial
// vertical velocity is positive - the probe will always hit the starting line again.
// Therefore, the next step must be less than the distance to the lower y target area
// boundary.  That reduced the execution times by a factor of 100.
//
// Update 2: Simplified the possible ranges further:
//  1. assure that target area is to the right and below to starting point
//  2. initial x velocity must be
//    a) at least 1 or else probe will never reach the target area
//    b) less than the right boundary of the target area because else the first shot will already overshoot
//  3. initial y velocity must be
//    a) at least the lower boundary of the target area because else the first shot will already undershoot
//    b) less than the absolute value of the lower boundary of the target + x because this will be
//       a positive number thus the probe will initially fly upward. This will ensure that the probe in
//       one of it steps end up at vertical position 0 again. The next step after that must be smaller than
//       the lower boundary of the target area or else we will miss the target area for sure. The
//       vertical velocity at this point must be negative (the probe is falling), and thus its vertical
//       velocity will increase with evey step.  We took at most "x" steps at this point, and thus the
//       initial velocity was decreased by at most "x".  Therefore, any initial vertical velocity which
//       is greater than the distance from 0 (the start) to the lower boundary of the target area plus "x"
//       will overshoot the target area with the next step.
// All of this resulted in the "final" solution which uses a common "hits" function for both parts.
