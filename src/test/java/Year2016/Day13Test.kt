package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day13Test {
    val sample = 10

    @Test fun `match sample`() {
        val expected = """
            .#.####.##
            ..#..#...#
            #....##...
            ###.#.###.
            .##..#..#.
            ..##....#.
            #...##.###
        """.trimIndent()

        val uut = Day13.Maze(sample)
        val actual = (0..6).map { y ->
            (0..9).map { x ->
                uut.at(x,y).c
            }.joinToString("")
        }.joinToString("\n")

        assertEquals(expected, actual)
    }

    @Test fun `sample path`() {
        val uut = Day13.Maze(sample)
        val expected = 11
        val actual = uut.shortestPath(target = 7 to 4)

        assertEquals(expected, actual)
    }
}