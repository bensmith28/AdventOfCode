package Year2021

import Year2021.Day15.findBestRouteScore
import Year2021.Day15.parseMap
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day15Test {
    val grid = "/Year2021/Day15-sample.txt".asResourceFile().readLines().parseMap()

    @Test
    fun `part 1 sample`() {
        assertEquals(
            40,
            findBestRouteScore(Day15.ExpandableGrid(grid))
        )
    }

    @Test
    fun `part 2 sample`() {
        assertEquals(
            315,
            findBestRouteScore(Day15.ExpandableGrid(grid, 5))
        )
    }
}