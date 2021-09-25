package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day2Test {
    val samplePatterns = listOf(
        "ULL",
        "RRDDD",
        "LURDL",
        "UUUUD"
    )

    @Test fun `sample`() {
        val expected = listOf('1', '9', '8', '5')
        val actual = Day2.findButtons(samplePatterns, Day2.Keypad.Standard)

        println(actual.joinToString(""))

        assertEquals(expected, actual)
    }

    @Test fun `sample 2`() {
        val expected = listOf('5', 'D', 'B', '3')
        val actual = Day2.findButtons(samplePatterns, Day2.Keypad.Actual)
        println(actual.joinToString(""))

        assertEquals(expected, actual)
    }

}