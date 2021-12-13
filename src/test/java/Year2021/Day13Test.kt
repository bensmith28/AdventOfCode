package Year2021

import org.junit.jupiter.api.Test
import util.asResourceFile
import kotlin.test.assertEquals

class Day13Test {
    val sample = "/Year2021/Day13-sample.txt".asResourceFile().readLines()

    @Test fun parsing() {
        val (grid, folds) = Day13.parse(sample)

        assertEquals(18, grid.dots.size)
        assertEquals(2, folds.size)
    }

    @Test fun `single fold`() {
        val (grid, folds) = Day13.parse(sample)
        val folded = grid.fold(folds.first())

        assertEquals(17, folded.dots.size)
    }

    @Test fun `all sample folds`() {
        val (grid, folds) = Day13.parse(sample)
        val folded = folds.fold(grid) { g, next -> g.fold(next) }

        assertEquals(16, folded.dots.size)
    }

    @Test fun printing() {
        val (grid, folds) = Day13.parse(sample)
        val folded = folds.fold(grid) { g, next -> g.fold(next) }
        val expected = """
            #####
            #...#
            #...#
            #...#
            #####
        """.trimIndent()

        assertEquals(expected, folded.toString())
    }
}