package Year2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class Day20Test {
    val sampleInput = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day20-sample.txt")
    @Test fun `test parse`() {
        val tiles = Day20.Tile.parseAll(sampleInput.readLines())

        assertNotNull(tiles)
        assertEquals(9, tiles.size)
    }

    @Test fun `test sample find corners`() {
        val solution = Day20.Solution(Day20.Tile.parseAll(sampleInput.readLines()))

        val expected = listOf(
            1951, 3079, 2971, 1171
        )
        val actual = solution.findCorners().map { it.id }

        assertEquals(expected.size, actual.size)
        assertTrue(
            actual.all { expected.contains(it) }
        )
    }

    @Test fun `test sample find solution`() {
        val solution = Day20.Solution(Day20.Tile.parseAll(sampleInput.readLines()))

        val actual = Day20.findSolution(solution)

        assertEquals(9, actual.placements.size)
        val expectedCornerProduct = 20899048083289
        val actualCornerProduct =
            actual.placements[0].tile.id.toLong() *
                    actual.placements[2].tile.id *
                    actual.placements[6].tile.id *
                    actual.placements[8].tile.id

        assertEquals(expectedCornerProduct, actualCornerProduct)

        val dragons = Day20.markDragonsInImage(actual)

        val expectedRoughness = 273
        val actualRoughness = Day20.calculateRoughness(dragons)

        assertEquals(expectedRoughness, actualRoughness)
    }

    @Test fun `test flip`() {
        val sample = Day20.Tile(0, listOf(
            listOf('.', '.', '.', '.', '.'),
            listOf('.', '#', '.', '.', '.'),
            listOf('.', '.', '.', '#', '.'),
            listOf('.', '#', '.', '.', '.'),
            listOf('.', '.', '.', '.', '.')
        ))

        val expected = listOf(
            listOf('.', '.', '#'),
            listOf('#', '.', '.'),
            listOf('.', '.', '#')
        )

        val actual = Day20.Placement(sample, true, 0).imagePixels

        assertEquals(expected, actual)
    }

    @Test fun `test rotation`() {
        val sample = Day20.Tile(0, listOf(
            listOf('.', '.', '.', '.', '.'),
            listOf('.', '#', '.', '.', '.'),
            listOf('.', '.', '.', '#', '.'),
            listOf('.', '#', '.', '.', '.'),
            listOf('.', '.', '.', '.', '.')
        ))

        val expected = listOf(
            listOf('#', '.', '#'),
            listOf('.', '.', '.'),
            listOf('.', '#', '.')
        )

        val actual = Day20.Placement(sample, false, 1).imagePixels

        assertEquals(expected, actual)
    }

    @Test fun `test rotate twice`() {
        val sample = Day20.Tile(0, listOf(
            listOf('.', '.', '.', '.', '.'),
            listOf('.', '#', '.', '.', '.'),
            listOf('.', '.', '.', '#', '.'),
            listOf('.', '#', '.', '.', '.'),
            listOf('.', '.', '.', '.', '.')
        ))

        val expected = listOf(
            listOf('.', '.', '#'),
            listOf('#', '.', '.'),
            listOf('.', '.', '#')
        )

        val actual = Day20.Placement(sample, false, 2).imagePixels

        assertEquals(expected, actual)
    }
}