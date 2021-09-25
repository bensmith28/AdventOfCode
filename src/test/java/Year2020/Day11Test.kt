package Year2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class Day11Test {
    val initial = getSample("round0")
    private fun getSample(label: String): Day11.Layout {
        return File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day11-sample-$label.txt")
            .let { Day11.Layout.parse(it.readLines()) }
    }

    private fun getSightSample(label: String): Day11.Layout2 {
        return File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day11-sample-$label.txt")
            .let { Day11.Layout2.parse(it.readLines()) }
    }

    @Test fun `next sample layout`() {
        val expected = getSample("round1")
        val actual = initial.getNextLayout()

        assertEquals(expected, actual)
    }

    @Test fun `rounds of sample`() {
        val expected = listOf(
            initial,
            getSample("round1"),
            getSample("round2"),
            getSample("round3"),
            getSample("round4"),
            getSample("round5")
        )

        val actual = (1..5).fold(listOf(initial)) { layouts, _ ->
            layouts.plus(layouts.last().getNextLayout())
        }

        expected.forEachIndexed { index, expectedLayout ->
            val actualLayout = actual[index]
            assertEquals(expectedLayout, actualLayout)
        }
    }

    @Test fun `counting occupied`() {
        val expected = 37
        val actual = getSample("round5").countOccupied()

        assertEquals(expected, actual)
    }

    @Test fun `countInSight 8`() {
        val layout = getSightSample("see8")
        assertEquals(
            8,
            layout.countOccupiedNeighborsInSight(3,4)
        )
    }

    @Test fun `countInSight 1`() {
        val layout = getSightSample("see1")
        assertEquals(
            1,
            layout.countOccupiedNeighborsInSight(3,1)
        )
    }
}