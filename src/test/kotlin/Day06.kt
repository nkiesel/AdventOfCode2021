import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06 {
    private val sample = """3,4,3,1,2"""

    @Test
    fun testOne(input: List<String>) {
        one(sample, 18) shouldBe 26
        one(sample, 80) shouldBe 5934
        one(input.first(), 80) shouldBe 388739
    }

    @Test
    fun testTwo(input: List<String>) {
        two(sample, 18) shouldBe 26L
        two(sample, 80) shouldBe 5934L
        two(sample, 256) shouldBe 26984457539L
        two(input.first(), 80) shouldBe 388739L
        two(input.first(), 256) shouldBe 1741362314973L
    }

    private fun one(input: String, iterations: Int): Int {
        var fish = input.split(",").map(String::toInt)
        repeat(iterations) {
            var count = 0
            fish = fish.map{ if (it == 0) 6.also { count++ } else it - 1 } + List(count) { 8 }
        }
        return fish.size
    }

    private fun two(input: String, iterations: Int): Long {
        val fish = input.split(",").map(String::toInt).groupingBy { it }.eachCount()
        // we know that the maximum "age" of a fish is 8, and thus only need an array of 9
        var g = LongArray(9) { fish[it]?.toLong() ?: 0L }
        repeat(iterations) {
            val n = LongArray(9)
            for (i in g.indices) {
                if (i == 0) {
                    n[6] = g[0]
                    n[8] = g[0]
                } else {
                    // n[6] is the only possible non-0 value, but no reason for another "if" here
                    n[i - 1] += g[i]
                }
            }
            g = n
        }
        return g.sum()
    }
}

// I solved part 1 in the most straight-forward way I could imagine: simply compute the next list
// from the current one.  But I quickly realized that this would not work for part 2 because the
// solution for the much shorter sample input already results in a number which is greater than
// Int.MAX_VALUE.  Lists in Kotlin can only have Int.MAX_VALUE values, and clearly this would
// take way too long.  I then first tried to come up with an exponential formula but while I still
// think that this should be possible, I could not wrap my head around that.  Then I realized that
// all fish of the same generation will produce the same number of offsprings.  This quickly lead
// to the solution where I count the number of fish in every generation and iterate on that.
// I could of course have replaced the solution for part 1 with the solution for part 2, but I want
// the code to reflect my thought process.
