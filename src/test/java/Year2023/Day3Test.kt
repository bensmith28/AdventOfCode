package Year2023

import Year2023.Day3.findPartNumbers
import Year2023.Day3.findSymbols
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day3Test {
    private val sample =
            """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
            """.trimIndent()

    @Test fun testPartNumbers() {
        val expected = listOf(
                Day3.PartNumber(number = 467, location = ((0 to 0) to (2 to 0))),
                Day3.PartNumber(number=114, location=((5 to 0) to (7 to 0))),
                Day3.PartNumber(number=35, location=((2 to 2) to (3 to 2))),
                Day3.PartNumber(number=633, location=((6 to 2) to (8 to 2))),
                Day3.PartNumber(number=617, location=((0 to 4) to (2 to 4))),
                Day3.PartNumber(number=58, location=((7 to 5) to (8 to 5))),
                Day3.PartNumber(number=592, location=((2 to 6) to (4 to 6))),
                Day3.PartNumber(number=755, location=((6 to 7) to (8 to 7))),
                Day3.PartNumber(number=664, location=((1 to 9) to (3 to 9))),
                Day3.PartNumber(number=598, location=((5 to 9) to (7 to 9)))
        )
        val actual = sample.findPartNumbers()
        assertEquals(expected, actual)
    }

    @Test fun testFindSymbols() {
        val expected = listOf(
                3 to 1,
                6 to 3,
                3 to 4,
                5 to 5,
                3 to 8,
                5 to 8
        )
        val actual = sample.findSymbols()
        assertEquals(expected, actual)
    }

    @Test fun testAdjacentTo() {
        val part = Day3.PartNumber(0, (3 to 1) to (5 to 1))

        assertTrue(part.adjacentTo(2 to 1))
        assertFalse(part.adjacentTo(1 to 1))
        assertTrue(part.adjacentTo(6 to 1))
        assertFalse(part.adjacentTo(7 to 1))
        assertTrue(part.adjacentTo(2 to 0))
        assertTrue(part.adjacentTo(2 to 2))
        assertFalse(part.adjacentTo(0 to 1))
        assertTrue(part.adjacentTo(4 to 0))
    }

    @Test fun samplePart1() {
        val parts = sample.findPartNumbers()
        val symbols = sample.findSymbols()

        val sum = parts.filter { p -> symbols.any { s -> p.adjacentTo(s) } }.sumOf { it.number }

        assertEquals(4361, sum)
    }

    @Test fun samplePart2() {
        val parts = sample.findPartNumbers()
        val symbols = sample.findSymbols()

        val gearsRatios = symbols.mapNotNull { s ->
            val adj = parts.filter { p -> p.adjacentTo(s) }

            if(adj.size == 2) {
                adj.first().number * adj.last().number
            } else null
        }

        val expected = 467835
        val actual = gearsRatios.sum()
        assertEquals(expected, actual)
    }
}