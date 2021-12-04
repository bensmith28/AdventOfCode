package Year2021

import org.junit.jupiter.api.Test
import util.asResourceFile
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class Day4Test {
    private val input = "/Year2021/Day4-sample.txt".asResourceFile().readLines()

    @Test fun `parsing the game`() {
        val game = Day4.Game.parse(input)

        assertEquals(27, game.numbers.size)
        assertEquals(3, game.boards.size)
    }

    @Test fun `winning row`() {
        val board = Day4.Game.parse(input).boards.first()
        listOf(22, 13, 17, 11, 0).forEach { board.playNumber(it) }

        assertTrue(board.isWinner())
    }

    @Test fun `winning column`() {
        val board = Day4.Game.parse(input).boards.first()
        listOf(22, 8, 21, 6, 1).forEach { board.playNumber(it) }

        assertTrue(board.isWinner())
    }


    /*@Test*/ fun `winning diagonal top left`() {
        val board = Day4.Game.parse(input).boards.first()
        listOf(22, 2, 14, 18, 19).forEach { board.playNumber(it) }

        assertTrue(board.isWinner())
    }

    /*@Test*/ fun `winning diagonal top right`() {
        val board = Day4.Game.parse(input).boards.first()
        listOf(0, 4, 14, 10, 1).forEach { board.playNumber(it) }

        assertTrue(board.isWinner())
    }

    @Test fun `find first winner`() {
        val (winningNumber, winningBoard) = Day4.Game.parse(input).findFirstWinner()

        assertEquals(24, winningNumber)
        assertEquals(188, winningBoard.score())
    }

    @Test fun `find last winner`() {
        val (winningNumber, winningBoard) = Day4.Game.parse(input).findLastWinner()

        assertEquals(13, winningNumber)
        assertEquals(148, winningBoard.score())
    }
}