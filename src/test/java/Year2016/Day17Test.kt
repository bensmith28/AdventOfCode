package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day17Test {
    @Test fun `validate directions`() {
        val input = "hijkl"
        assertEquals(
            setOf(Day17.Direction.DOWN),
            Day17.allowedDirections(input)
        )
        assertEquals(
            setOf(Day17.Direction.UP, Day17.Direction.RIGHT),
            Day17.allowedDirections(input, "D")
        )
        assertEquals(
            emptySet<Day17.Direction>(),
            Day17.allowedDirections(input, "DR")
        )
        assertEquals(
            setOf(Day17.Direction.RIGHT),
            Day17.allowedDirections(input, "DU")
        )
        assertEquals(
            emptySet<Day17.Direction>(),
            Day17.allowedDirections(input, "DUR")
        )
    }

    @Test fun `path sample`() {
        assertEquals(
            "DDRRRD",
            Day17.shortestPath("ihgpwlah")
        )
    }

    @Test fun `path sample 2`() {
        assertEquals(
            "DDUDRLRRUDRD",
            Day17.shortestPath("kglvqrro")
        )
    }

    @Test fun `path sample 3`() {
        assertEquals(
            "DRURDRUDDLLDLUURRDULRLDUUDDDRR",
            Day17.shortestPath("ulqzkmiv")
        )
    }

    @Test fun `longest path sample`() {
        assertEquals(
            370,
            Day17.longestPath("ihgpwlah").length
        )
    }
}