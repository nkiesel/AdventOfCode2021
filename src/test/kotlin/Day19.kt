import kotlin.math.absoluteValue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day19 {

    val sample = """
        --- scanner 0 ---
        404,-588,-901
        528,-643,409
        -838,591,734
        390,-675,-793
        -537,-823,-458
        -485,-357,347
        -345,-311,381
        -661,-816,-575
        -876,649,763
        -618,-824,-621
        553,345,-567
        474,580,667
        -447,-329,318
        -584,868,-557
        544,-627,-890
        564,392,-477
        455,729,728
        -892,524,684
        -689,845,-530
        423,-701,434
        7,-33,-71
        630,319,-379
        443,580,662
        -789,900,-551
        459,-707,401

        --- scanner 1 ---
        686,422,578
        605,423,415
        515,917,-361
        -336,658,858
        95,138,22
        -476,619,847
        -340,-569,-846
        567,-361,727
        -460,603,-452
        669,-402,600
        729,430,532
        -500,-761,534
        -322,571,750
        -466,-666,-811
        -429,-592,574
        -355,545,-477
        703,-491,-529
        -328,-685,520
        413,935,-424
        -391,539,-444
        586,-435,557
        -364,-763,-893
        807,-499,-711
        755,-354,-619
        553,889,-390

        --- scanner 2 ---
        649,640,665
        682,-795,504
        -784,533,-524
        -644,584,-595
        -588,-843,648
        -30,6,44
        -674,560,763
        500,723,-460
        609,671,-379
        -555,-800,653
        -675,-892,-343
        697,-426,-610
        578,704,681
        493,664,-388
        -671,-858,530
        -667,343,800
        571,-461,-707
        -138,-166,112
        -889,563,-600
        646,-828,498
        640,759,510
        -630,509,768
        -681,-892,-333
        673,-379,-804
        -742,-814,-386
        577,-820,562

        --- scanner 3 ---
        -589,542,597
        605,-692,669
        -500,565,-823
        -660,373,557
        -458,-679,-417
        -488,449,543
        -626,468,-788
        338,-750,-386
        528,-832,-391
        562,-778,733
        -938,-730,414
        543,643,-506
        -524,371,-870
        407,773,750
        -104,29,83
        378,-903,-323
        -778,-728,485
        426,699,580
        -438,-605,-362
        -469,-447,-387
        509,732,623
        647,635,-688
        -868,-804,481
        614,-800,639
        595,780,-596

        --- scanner 4 ---
        727,592,562
        -293,-554,779
        441,611,-461
        -714,465,-776
        -743,427,-804
        -660,-479,-426
        832,-632,460
        927,-485,-438
        408,393,-506
        466,436,-512
        110,16,151
        -258,-428,682
        -393,719,612
        -211,-452,876
        808,-476,-593
        -575,615,604
        -485,667,467
        -680,325,-822
        -627,-443,-432
        872,-547,-609
        833,512,582
        807,604,487
        839,-516,451
        891,-625,532
        -652,-548,-490
        30,-46,-14
    """.trimIndent()

    data class Position(val x: Int, val y: Int, val z: Int) {
        infix operator fun plus(p: Position) = Position(x + p.x, y + p.y, z + p.z)
        infix operator fun minus(p: Position) = Position(x - p.x, y - p.y, z - p.z)

        fun distance(p: Position) = (x - p.x).absoluteValue + (y -p.y).absoluteValue + (z -p.z).absoluteValue
    }

    class Scanner(val beacons: List<Position>) {
        val allVisible = mutableSetOf<Position>()
        var position = Position(0, 0, 0)
        private val transformations = 0..23

        private fun transform(index: Int, beacons: List<Position>): List<Position> {
            return beacons.map { b -> transform(index, b) }
        }

        fun beacons(): List<List<Position>> {
            return transformations.map { transform(it, beacons) }
        }

        private fun transform(index: Int, position: Position): Position {
            val (x, y, z) = position
            return when (index) {
                0 -> Position(x, y, z)
                1 -> Position(x, z, -y)
                2 -> Position(x, -y, -z)
                3 -> Position(x, -z, y)

                4 -> Position(y, -x, z)
                5 -> Position(y, z, x)
                6 -> Position(y, x, -z)
                7 -> Position(y, -z, -x)

                8 -> Position(-x, -y, z)
                9 -> Position(-x, -z, -y)
                10 -> Position(-x, y, -z)
                11 -> Position(-x, z, y)

                12 -> Position(-y, x, z)
                13 -> Position(-y, -z, x)
                14 -> Position(-y, -x, -z)
                15 -> Position(-y, z, -x)

                16 -> Position(z, y, -x)
                17 -> Position(z, x, y)
                18 -> Position(z, -y, x)
                19 -> Position(z, -x, -y)

                20 -> Position(-z, -y, -x)
                21 -> Position(-z, -x, y)
                22 -> Position(-z, y, x)
                23 -> Position(-z, x, -y)
                else -> throw error("not supported")
            }
        }
    }

    private fun parse(input: List<String>): List<Scanner> {
        val scanner = Regex("--- scanner \\d+ ---")
        return buildList {
            var beacons: MutableList<Position> = mutableListOf()
            for (line in input.filter { it.isNotEmpty() }) {
                val m = scanner.matchEntire(line)
                if (m != null) {
                    beacons = mutableListOf()
                    add(beacons)
                } else {
                    beacons.add(line.split(",").map(String::toInt).let { Position(it[0], it[1], it[2]) })
                }
            }
        }.map { Scanner(it) }
    }

    private fun overlaps(a: Set<Position>, offset: Position, b: List<Position>): List<Position>? {
        val transformed = b.map { it + offset }
        val matching = a intersect transformed.toSet()
        return if (matching.size < 12) null else transformed - matching
    }

    private fun overlaps(sa: Scanner, sb: Scanner): Boolean {
        for (a in sa.allVisible) {
            for (bl in sb.beacons()) {
                for (b in bl) {
                    val offset = a - b
                    val matching = overlaps(sa.allVisible, offset, bl)
                    if (matching != null) {
                        sa.allVisible += matching
                        sb.position = offset
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun beaconCount(scanners: List<Scanner>): Int {
        val remaining = scanners.toMutableList()
        val first = remaining.removeAt(0)
        first.allVisible += first.beacons

        while (remaining.isNotEmpty()) {
            var removed = false
            for (b in remaining) {
                if (overlaps(first, b)) {
                    remaining.remove(b)
                    removed = true
                    break
                }
            }
            if (!removed) {
                error("no more overlaps")
            }
        }
        return first.allVisible.size
    }

    @Test
    fun testOneAndTwo(input: List<String>) {
        val sampleScanners = parse(sample.lines())
        beaconCount(sampleScanners) shouldBe 79
        distance(sampleScanners) shouldBe 3621

        val scanners = parse(input)
        beaconCount(scanners) shouldBe 355
        distance(scanners) shouldBe 10842
    }

    private fun distance(scanners: List<Scanner>) = buildList {
        for (i in scanners.indices) {
            for (j in scanners.indices) {
                if (i != j) {
                    add(scanners[i].position.distance(scanners[j].position))
                }
            }
        }
    }.maxOf { it }

}

// Oh man, this was difficult!!!  I spend much time thinking how to find a "smart" solution, but could not come
// up with anything.  Thus, the solution above is a brute-force solution which runs for nearly 2 minutes.  Part
// 2 is using the result of part 1, and I thus combined both tests so that I do not have to recompute part 1.
// One of the "hints" I did not use is that every scanner sees everything in a 1000 position distance.  That
// must be usable to limit the search space, but I have not yet figured out how to do so.
