package Year2021

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day3Test {
    private val sampleInput = "/Year2021/Day3-sample.txt".asResourceFile().readLines()

    @Test fun `part 1 gamma sample`() {
        assertEquals(
            "10110",
            Day3.gamma(sampleInput)
        )
    }

    @Test fun `part 1 epsilon sample`() {
        assertEquals(
            "01001",
            Day3.epsilon(Day3.gamma(sampleInput))
        )
    }

    @Test fun `part 1 product sample`() {
        val gamma = Day3.gamma(sampleInput)
        assertEquals(
            198,
            Day3.product(gamma, Day3.epsilon(gamma))
        )
    }

    @Test fun `part 2 broken gamma`() {
        val input = listOf(
            "11110",
            "10110",
            "10111",
            "10101",
            "11100",
            "10000",
            "11001"
        )
        assertEquals(
            '0',
            Day3.gamma(input)[1]
        )
    }

    @Test fun `part 2 oxy`() {
        assertEquals(
            "10111",
            Day3.findOxygenGeneratorRating(sampleInput)
        )
    }

    @Test fun `part 2 CO2`() {
        assertEquals(
            "01010",
            Day3.findCO2ScrubberRating(sampleInput)
        )
    }
}