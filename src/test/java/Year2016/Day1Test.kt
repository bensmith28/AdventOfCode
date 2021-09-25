package Year2016

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day1Test {

    @Test fun `reject repeated destination`() {
        val dest1 = Day1.Destination(0, 3, 0)
        val dest2 = Day1.Destination(0, 3, 0)

        val target = mutableSetOf(Day1.Destination())
        assertTrue(target.add(dest1))
        assertFalse(target.add(dest2))
    }

    @Test
    fun `find correct repeat`() {
        val ins = listOf("R8", "R4", "R4", "R8").map { Day1.Instruction.parse(it) }

        assertEquals(
            Day1.Destination(0, 0, 4),
            Day1.findRepeatedDestination(ins)
        )
    }
}