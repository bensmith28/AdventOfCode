package Year2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class Day22Test {
    val sampleInput = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day22-sample.txt")
    @Test fun `parse`() {
        val players = Day22.Player.parse(sampleInput.readLines())

        assertEquals(2, players.size)
    }

    @Test fun `play combat`() {
        val players = Day22.Player.parse(sampleInput.readLines())

        // Part 1
        //val expected = Day22.GameResult("Player 2", 306)

        // Part 2
        val expected = Day22.GameResult("Player 2", 291)
        val actual = Day22.playCombat(players)

        assertEquals(expected, actual)
    }
}