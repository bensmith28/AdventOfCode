package Year2021

import org.junit.jupiter.api.Test
import util.asResourceFile
import kotlin.test.assertEquals

class Day7Test {
    private val sampleFleet =
        "/Year2021/Day7-sample.txt".asResourceFile().readLines().single().split(",").map { it.toInt() }

    @Test fun `fuel to location 2`() {
        assertEquals(
            37,
            Day7.linearFuelToLocation(sampleFleet, 2)
        )
    }

    @Test fun `fuel to location 1`() {
        assertEquals(
            41,
            Day7.linearFuelToLocation(sampleFleet, 1)
        )
    }

    @Test fun `fuel to location 3`() {
        assertEquals(
            39,
            Day7.linearFuelToLocation(sampleFleet, 3)
        )
    }

    @Test fun `fuel to location 10`() {
        assertEquals(
            71,
            Day7.linearFuelToLocation(sampleFleet, 10)
        )
    }

    @Test fun `part 1`() {
        assertEquals(
            37,
            Day7.minFuelToLineUp(sampleFleet)
        )
    }

    @Test fun `increasing fuel to location 5`() {
        assertEquals(
            168,
            Day7.increasingFuelToLocation(sampleFleet, 5)
        )
    }

    @Test fun `increasing fuel to location 2`() {
        assertEquals(
            206,
            Day7.increasingFuelToLocation(sampleFleet, 2)
        )
    }

    @Test fun `part 2`() {
        assertEquals(
            168,
            Day7.minFuelToLineUp(sampleFleet, Day7::increasingFuelToLocation)
        )
    }
}