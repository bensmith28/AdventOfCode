package Year2021

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import util.asResourceFile

internal class Day1Test {
    private val input = "/Year2021/Day1-sample.txt".asResourceFile().readLines().map { it.trim().toInt() }

    @Test
    fun `part 1 - with window size 1`() {
        assertEquals(
            7,
            Day1.howManyIncreases(input)
        )
    }

    @Test
    fun `part 2 - with window size 3`() {
        assertEquals(
            5,
            Day1.howManyIncreases(input, 3)
        )
    }
}