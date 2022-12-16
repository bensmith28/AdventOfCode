package Year2022

import Year2022.Day13.compareOrders
import Year2022.Day13.decoderKey
import Year2022.Day13.parseInput
import Year2022.Day13.parsePacket
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day13Test {
    val input = "/Year2022/Day13-sample.txt".asResourceFile().readLines()

    private fun List<Any?>.toPacket(): Day13.Packet = Day13.ListPacket(
        this.map {
            when (it) {
                is List<*> -> it.toPacket()
                is Int -> Day13.ConcretePacket(it)
                else -> error("Bad type of $it")
            }
        }
    )

    @Nested
    inner class Parsing {
        @Test
        fun `flat 1`() {
            val expected = listOf(1, 1, 3, 1, 1).toPacket()
            val actual = "[1,1,3,1,1]".parsePacket()
            assertEquals(expected, actual)
        }

        @Test
        fun `flat 2`() {
            val expected = listOf(1,1,5,1,1).toPacket()
            val actual = "[1,1,5,1,1]".parsePacket()
            assertEquals(expected, actual)
        }

        @Test
        fun `double nested`() {
            val expected = listOf(listOf(1), listOf(2,3,4)).toPacket()
            val actual = "[[1],[2,3,4]]".parsePacket()
            assertEquals(expected, actual)
        }

        @Test
        fun `mix nested`() {
            val expected = listOf(listOf(1), 4).toPacket()
            val actual = "[[1],4]".parsePacket()
            assertEquals(expected, actual)
        }

        @Test
        fun singleton() {
            val expected = listOf(9).toPacket()
            val actual = "[9]".parsePacket()
            assertEquals(expected, actual)
        }

        @Test
        fun `singleton outer`() {
            val expected = listOf(listOf(8,7,6)).toPacket()
            val actual = "[[8,7,6]]".parsePacket()
            assertEquals(expected, actual)
        }

        @Test
        fun empty() {
            val expected = emptyList<Int>().toPacket()
            val actual = "[]".parsePacket()
            assertEquals(expected, actual)
        }

        @Test
        fun `triple nested empty`() {
            val expected = listOf(listOf(emptyList<Int>())).toPacket()
            val actual = "[[[]]]".parsePacket()
            assertEquals(expected, actual)
        }
    }

    @Nested
    inner class Part1 {
        @Test
        fun `pair 1`() {
            assertTrue(
                "[1,1,3,1,1]".parsePacket() < "[1,1,5,1,1]".parsePacket()
            )
        }

        @Test
        fun `pair 2`() {
            assertTrue(
                "[[1],[2,3,4]]".parsePacket() < "[[1],4]".parsePacket()
            )
        }

        @Test
        fun `pair 3`() {
            assertTrue(
                "[9]".parsePacket() > "[[8,7,6]]".parsePacket()
            )
        }

        @Test
        fun `pair 4`() {
            assertTrue(
                "[[4,4],4,4]".parsePacket() < "[[4,4],4,4,4]".parsePacket()
            )
        }

        @Test
        fun `pair 5`() {
            assertTrue(
                "[7,7,7,7]".parsePacket() > "[7,7,7]".parsePacket()
            )
        }

        @Test
        fun `pair 6`() {
            assertTrue(
                "[]".parsePacket() < "[3]".parsePacket()
            )
        }

        @Test
        fun `pair 7`() {
            assertTrue(
                "[[[]]]".parsePacket() > "[[]]".parsePacket()
            )
        }

        @Test
        fun `pair 8`() {
            assertTrue(
                "[1,[2,[3,[4,[5,6,7]]]],8,9]".parsePacket() > "[1,[2,[3,[4,[5,6,0]]]],8,9]".parsePacket()
            )
        }

        @Test
        fun `part 1`() {
            assertEquals(
                13,
                compareOrders(parseInput(input))
            )
        }
    }

    @Test
    fun `part 2`() {
        assertEquals(
            140,
            decoderKey(parseInput(input))
        )
    }
}