package Year2021

import org.junit.jupiter.api.Test
import util.asResourceFile
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day9Test {
    val sample = "/Year2021/Day9-sample.txt".asResourceFile().readLines().let { Day9.CaveMap.parse(it) }

    @Test fun parsing() {
        assertEquals(10, sample.grid.keys.size) // columns
        assertEquals(5, sample.grid[0]?.keys?.size) // rows
        assertEquals(1, sample.grid[1]?.get(0)) // sample tile
    }

    @Test fun `get low points`() {
        val expected = listOf(1, 0, 5, 5)
        val actual = sample.getLowHeights()

        assertTrue(expected.containsAll(actual) && actual.containsAll(expected))
    }

    @Test fun `part 1`() {
        assertEquals(
            15,
            sample.getRisk()
        )
    }

    @Test fun `measure basins`() {
        val expected = listOf(3, 9, 14, 9)
        val actual = sample.findBasinSizes()

        assertTrue(expected.containsAll(actual) && actual.containsAll(expected))
    }

    @Test fun `part 2`() {
        assertEquals(
            1134,
            sample.basinScore()
        )
    }
}