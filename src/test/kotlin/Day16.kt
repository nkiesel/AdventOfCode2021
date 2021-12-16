import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day16 {
    private val toBits = mapOf(
        '0' to "0000",
        '1' to "0001",
        '2' to "0010",
        '3' to "0011",
        '4' to "0100",
        '5' to "0101",
        '6' to "0110",
        '7' to "0111",
        '8' to "1000",
        '9' to "1001",
        'A' to "1010",
        'B' to "1011",
        'C' to "1100",
        'D' to "1101",
        'E' to "1110",
        'F' to "1111",
    )

    @Test
    fun testOne(input: List<String>) {
        one("D2FE28") shouldBe 6
        one("8A004A801A8002F478") shouldBe 16
        one("620080001611562C8802118E34") shouldBe 12
        one("C0015000016115A2E0802F182340") shouldBe 23
        one("A0016C880162017C3686B18A3D4780") shouldBe 31
        one(input.first()) shouldBe 821
    }

    @Test
    fun testTwo(input: List<String>) {
        two("C200B40A82") shouldBe 3L
        two("880086C3E88112") shouldBe 7L
        two("CE00C43D881120") shouldBe 9L
        two("D8005AC2A8F0") shouldBe 1L
        two("F600BC2D8F") shouldBe 0L
        two("9C005AC2F8F0") shouldBe 0L
        two("9C0141080250320F1802104A08") shouldBe 1L
        two(input.first()) shouldBe 2056021084691L
    }

    class Data(private val data: String) {
        var offset = 0
        fun str(bits: Int) = data.substring(offset, offset + bits).also { offset += bits }
        fun int(bits: Int) = str(bits).toInt(2)
    }

    enum class Type(val id: Int) {
        SUM(0), PRODUCT(1), MINIMUM(2), MAXIMUM(3), LITERAL(4), GREATER_THAN(5), LESS_THAN(6), EQUAL_TO(7);

        companion object {
            private val lookup = enumValues<Type>().associateBy { it.id }
            fun fromInt(i: Int) = lookup.getValue(i)
        }
    }

    class Packet(
        private val version: Int,
        private val typeId: Type,
        val value: Long = 0L,
        val subPackets: List<Packet> = emptyList()
    ) {
        fun versionSum(): Int = version + subPackets.sumOf { it.versionSum() }

        fun compute(): Long {
            return when (typeId) {
                Type.SUM -> subPackets.map { it.compute() }.reduce(Long::plus)
                Type.PRODUCT -> subPackets.map { it.compute() }.reduce(Long::times)
                Type.MINIMUM -> subPackets.map { it.compute() }.minOf { it }
                Type.MAXIMUM -> subPackets.map { it.compute() }.maxOf { it }
                Type.LITERAL -> value
                Type.GREATER_THAN -> subPackets.map { it.compute() }.let { if (it[0] > it[1]) 1L else 0L }
                Type.LESS_THAN -> subPackets.map { it.compute() }.let { if (it[0] < it[1]) 1L else 0L }
                Type.EQUAL_TO -> subPackets.map { it.compute() }.let { if (it[0] == it[1]) 1L else 0L }
            }
        }
    }

    private fun parse(data: Data): Packet {
        val version = data.int(3)
        return when (val typeId = Type.fromInt(data.int(3))) {
            Type.LITERAL -> Packet(version, typeId, value = buildString {
                while (true) {
                    val last = data.int(1) == 0
                    append(data.str(4))
                    if (last) break
                }
            }.toLong(2))
            else -> Packet(version, typeId, subPackets = buildList {
                if (data.int(1) == 0) {
                    val length = data.int(15)
                    val end = data.offset + length
                    while (data.offset < end) {
                        add(parse(data))
                    }
                } else {
                    val times = data.int(11)
                    repeat(times) { add(parse(data)) }
                }
            })
        }
    }

    private fun one(input: String): Int {
        val data = Data(input.map { toBits[it] }.joinToString(""))
        return parse(data).versionSum()
    }

    private fun two(input: String): Long {
        val data = Data(input.map { toBits[it] }.joinToString(""))
        return parse(data).compute()
    }
}
