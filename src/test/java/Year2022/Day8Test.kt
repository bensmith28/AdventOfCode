package Year2022

import Year2021.Day15.parseMap
import Year2022.Day8.scenicScore
import Year2022.Day8.takeUntil
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day8Test {
    val grid = "/Year2022/Day8-sample.txt".asResourceFile().readLines().parseMap()

    @Test
    fun `sample part 1`() {
        assertEquals(
            21,
            Day8.countAllVisibleTrees(grid)
        )
    }

    @Test
    fun `validate take until`() {
        val uut = listOf(1, 3, 5, 3)
        val expected = listOf(1, 3, 5)
        val actual = uut.takeUntil { it >= 4 }

        assertEquals(expected, actual)
    }

    @Test
    fun `scenic score 1`() {
        assertEquals(
            4,
            (1 to 2).scenicScore(grid)
        )
    }

    @Test
    fun `scenic score 2`() {
        assertEquals(
            8,
            (3 to 2).scenicScore(grid)
        )
    }
}