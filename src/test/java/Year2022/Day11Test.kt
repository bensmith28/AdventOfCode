package Year2022

import Year2022.Day11.doRound
import Year2022.Day11.getMonkeyBusiness
import Year2022.Day11.parseMonkeysPart1
import Year2022.Day11.parseMonkeysPart2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day11Test {
    private val monkeys
        get() = "/Year2022/Day11-sample.txt".asResourceFile().readLines()

    @Nested
    inner class Part1 {
        @Test
        fun `round 1`() {
            val uut = monkeys.parseMonkeysPart1()
            uut.doRound()
            assertEquals(
                listOf(20L, 23L, 27L, 26L),
                uut.single { it.id == 0 }.items
            )
            assertEquals(
                listOf(2080L, 25L, 167L, 207L, 401L, 1046L),
                uut.single { it.id == 1 }.items
            )
            assertEquals(
                emptyList<Long>(),
                uut.single { it.id == 2 }.items
            )
            assertEquals(
                emptyList<Long>(),
                uut.single { it.id == 3 }.items
            )
        }

        @Test
        fun `round 20`() {
            val uut = monkeys.parseMonkeysPart1()
            repeat(20) { uut.doRound() }
            assertEquals(
                10605,
                uut.getMonkeyBusiness()
            )
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun `round 1`() {
            val uut = monkeys.parseMonkeysPart2()
            uut.doRound()
            assertEquals(
                2, uut.single { it.id == 0 }.inspections
            )
            assertEquals(
                4, uut.single { it.id == 1 }.inspections
            )
            assertEquals(
                3, uut.single { it.id == 2 }.inspections
            )
            assertEquals(
                6, uut.single { it.id == 3 }.inspections
            )
        }

        @Test
        fun `round 20`() {
            val uut = monkeys.parseMonkeysPart2()
            repeat(20) { uut.doRound() }
            assertEquals(
                99, uut.single { it.id == 0 }.inspections
            )
            assertEquals(
                97, uut.single { it.id == 1 }.inspections
            )
            assertEquals(
                8, uut.single { it.id == 2 }.inspections
            )
            assertEquals(
                103, uut.single { it.id == 3 }.inspections
            )
        }

        @Test
        fun `round 1000`() {
            val uut = monkeys.parseMonkeysPart2()
            repeat(1000) { uut.doRound() }
            assertEquals(
                5204, uut.single { it.id == 0 }.inspections
            )
            assertEquals(
                4792, uut.single { it.id == 1 }.inspections
            )
            assertEquals(
                199, uut.single { it.id == 2 }.inspections
            )
            assertEquals(
                5192, uut.single { it.id == 3 }.inspections
            )
        }

        @Test
        fun `round 10000`() {
            val uut = monkeys.parseMonkeysPart2()
            repeat(10000) { uut.doRound() }
            assertEquals(
                2713310158,
                uut.getMonkeyBusiness()
            )
        }
    }
}