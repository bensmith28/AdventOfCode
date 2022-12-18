package Year2022

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day16Test {
    val input = "/Year2022/Day16-sample.txt".asResourceFile().readLines()
    @Test
    fun parsing() {
        val valves = input.map { Day16.Valve.parse(it) }
        assertEquals(10, valves.size)
    }

    @Test
    fun `part 1`() {
        val valves = input.map { Day16.Valve.parse(it) }
        assertEquals(
            1651,
            Day16.findGreatestPressureRelease(valves)
        )
    }
}