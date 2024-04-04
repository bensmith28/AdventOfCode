package Year2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class Day18Test {
    @Test fun `test parse 1`() {
        val input = "1 + 2 * 3 + 4 * 5 + 6"
        val exp = Day18.Statement.parse(input)

        val expected = 231L
        val actual = exp.value

        assertEquals(expected, actual)
    }

    @Test fun `test parse 2`() {
        val input = "1 + (2 * 3) + (4 * (5 + 6))"
        val exp = Day18.Statement.parse(input)

        val expected = 51L
        val actual = exp.value

        assertEquals(expected, actual)
    }

    @Test fun `test parse 3`() {
        val input = "2 * 3 + (4 * 5)"
        val exp = Day18.Statement.parse(input)

        val expected = 46L
        val actual = exp.value

        assertEquals(expected, actual)
    }

    @Test fun `test parse 4`() {
        val input = "5 + (8 * 3 + 9 + 3 * 4 * 3)"
        val exp = Day18.Statement.parse(input)

        val expected = 1445L
        val actual = exp.value

        assertEquals(expected, actual)
    }

    @Test fun `test parse 5`() {
        val input = "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"
        val exp = Day18.Statement.parse(input)

        val expected = 669060L
        val actual = exp.value

        assertEquals(expected, actual)
    }

    @Test fun `test parse 6`() {
        val input = "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"
        val exp = Day18.Statement.parse(input)

        val expected = 23340L
        val actual = exp.value

        assertEquals(expected, actual)
    }

    @Test fun `sum from file`() {
        val file = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day18-sample.txt")

        val expected = 231L + 51 + 46 + 1445 + 669060 + 23340
        val actual = file.readLines().fold(0L) { sum, line ->
            sum + Day18.Statement.parse(line).value
        }

        assertEquals(expected, actual)
    }
}